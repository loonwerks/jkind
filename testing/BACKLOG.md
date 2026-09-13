# JKind backlog

Ideas raised while fuzzing and updating solvers, recorded rather than acted on.
Solver items first, then engine and algorithm items.

## Solvers

### Load solvers automatically, without requiring `<SOLVER>_HOME`

Today `jkind.solvers.ProcessBasedSolver.getSolverPath` looks up
`<SOLVER>_HOME` in the environment and otherwise falls back to bare executable
name resolution through `PATH`. Every user therefore has to install each solver
themselves and wire up the environment before JKind can use anything but the
bundled SMTInterpol.

The ask is a Java package that locates and loads solvers automatically, so that
no environment variable is needed.

#### Sketch

A resolver consulted by `getSolverPath`, in order:

1. `<SOLVER>_HOME`, so existing setups keep working unchanged.
2. A JKind-managed solver directory (`~/.jkind/solvers/<name>/<version>/`).
3. `PATH`.
4. Solvers shipped inside the distribution, extracted to a cache directory on
   first use and marked executable.

Step 4 is the one that removes the manual setup, and it is the one with the
licensing constraints below. Steps 1-3 are uncontroversial and worth doing on
their own: they already remove most of the friction.

Worth adding alongside: a `-solvers` flag that prints each known solver, where
it was found, and its reported version. Version reporting also gives JKind a
place to warn about known-incompatible releases, which is how the Yices 2.7
model-format break would have surfaced as a diagnostic rather than a stack
trace.

#### Licensing constraints on shipping solver binaries

**Do not bundle a solver without checking its license first.** They differ
sharply, and two of the five cannot be redistributed the way the others can.

| Solver | Version | License | Can JKind redistribute it? |
|---|---|---|---|
| Z3 | 5.1.0 | MIT | Yes. Permissive; keep the copyright notice. |
| cvc5 | 1.3.4 | BSD-3-Clause | Yes, **but only the non-GPL build**. Upstream also publishes `-gpl` artifacts built against GPL libraries (CLN, GLPK); those are GPLv3 and must not be shipped as if they were BSD. |
| SMTInterpol | 2.5-1388 | LGPL-3.0 | Yes, with LGPL obligations — see below. Already shipped today. |
| Yices 2 | 2.7.0 | **GPLv3** | Only with care. Yices 2 relicensed to GPLv3 at 2.6.0. Running it as a separate process is not linking, so it does not infect JKind, but redistributing the binary carries GPLv3 obligations including a corresponding-source offer. Prefer downloading on demand over shipping it. |
| MathSAT 5 | 5.6.17 | Proprietary | **No.** "Available for research and evaluation purposes only. It can not be used in a commercial environment... without written permission." Must be user-installed; JKind may only detect it. |

The safest design that still removes the setup burden: ship nothing, and have
the resolver *fetch* a solver on demand into the managed directory, after
showing the user that solver's license and asking for confirmation. That keeps
redistribution out of JKind entirely while still giving one-command setup.

#### A note on the existing SMTInterpol bundling

`build.gradle` merges `smtinterpol.jar` into `jkind.jar`, so the shipped jar is
a combined work containing LGPL-3.0 code. Two things worth checking:

- LGPL-3.0 section 4 allows this, but expects the user to be able to replace
  the LGPL component with a modified version. A single merged jar makes that
  awkward; keeping SMTInterpol as a sibling jar on the classpath would satisfy
  it more clearly.
- The merge copies SMTInterpol's `LICENSE`, `COPYING` and `COPYING.LESSER` to
  the root of `jkind.jar`, while JKind's own BSD `LICENSE` is not included.
  Anyone opening the jar sees only the LGPL text and would reasonably conclude
  JKind is LGPL. JKind's own license should be included under a distinct name.

---

### Portfolio solving: race several solvers, take the first answer

Run the same proof obligation against several SMT backends concurrently and
accept whichever answers first. Solver performance on Lustre verification
conditions varies by orders of magnitude and not consistently in one
direction, so a portfolio buys robustness rather than peak speed: you stop
losing runs to the one backend that happens to be bad at this problem.

#### Does it make sense with k-induction?

Yes, but not as a per-query race, and that distinction drives the design.

K-induction is not a single query. It is an incremental loop: at each depth it
issues a base-case query and an inductive-step query against a solver that is
accumulating assertions through push/pop. Racing *individual queries* across
solvers would mean every backend maintains the full incremental state anyway,
which is the expensive part. At that point you have independent instances
wearing a costume.

So the design that actually works is **independent racing instances**: run a
complete k-induction instance per backend, each with its own solver process,
and take the first conclusive result. This is sound — whichever instance
concludes, concludes — and it composes with JKind's existing structure, since
the Director already supervises parallel engines with their own solvers.

Four things to get right:

1. **Process budget.** JKind already runs BMC, k-induction, PDR and invariant
   generation concurrently, each with a solver process. Multiplying that by the
   number of backends is how you end up with twenty solver processes fighting
   over cores and making everything slower. The portfolio needs a budget, and
   probably a curated default of two or three backends rather than all of them.

2. **Invariant sharing.** JKind's engines cooperate by sharing discovered
   invariants through the Director. Racing instances that do not share are
   leaving the main benefit of that architecture on the floor; instances that
   do share need the invariant channel to be solver-agnostic. This is the
   interesting design work, and it is what separates a real portfolio from
   running JKind three times.

3. **It discards a bug oracle.** Disagreement between solvers is a strong
   signal that one of them is wrong — the tier-2 fuzzing in
   `testing/fuzzing` uses exactly that as an oracle. "First answer wins"
   silently throws that signal away and makes any single solver's unsoundness
   into JKind's unsoundness. Worth pairing with an opt-in mode that waits for
   all backends and reports contradictions instead of racing them.

4. **Reproducibility.** Which backend wins varies run to run, so counterexamples
   stop being stable across runs. Anything that diffs JKind output — including
   the regression corpus — needs a way to pin a single solver.

K-induction aside, the place a portfolio pays off most is the inductive step at
larger k, which is where the time concentrates and where backends differ most.

---

## Engines and algorithms

### Run PDR in both directions when threads allow

`PdrEngine` spawns one `PdrSubengine` thread per property, capped by
`-pdr_max`. Each subengine runs forward PDR: it refines frames from the initial
states toward the property.

PDR can also be run in reverse, treating the negated property as the starting
point and the initial states as the target. The two directions have genuinely
different performance profiles — reverse PDR tends to win where the bad states
are few and highly constrained but the reachable state space is broad, and
loses where the reverse image is expensive to compute. Which one wins is not
predictable from the model, which is exactly the case for running both.

The proposal: when the thread budget exceeds the number of properties, spend
the spare capacity on a reverse-direction instance of the same property rather
than leaving it idle. First direction to converge answers.

Points to settle:

- **Budget policy.** With *p* properties and *n* threads, the current policy is
  `min(p, n)` forward instances. A both-directions policy wants something like
  forward-first, then backward for the properties still open, so that a model
  with many properties does not starve.
- **Reverse image.** Forward PDR needs a post-image; reverse needs a pre-image.
  For the Lustre transition relation both are expressible, but the existing
  `PdrSmt` queries are written in one direction and would need a counterpart.
- **Invariant sharing.** Lemmas learned in one direction are not directly
  transferable to the other, but invariants proved in either direction are
  still invariants, and JKind's Director already has a channel for those.
- **Counterexample extraction.** A reverse instance produces a trace from the
  bad state back to the initial state; it must be reversed before reporting, or
  the counterexample comes out backwards.

### Techniques worth considering

Roughly in order of expected value for an SMT-based infinite-state checker.

**IC3 with implicit predicate abstraction (IC3-IA).** The standard modern
approach for infinite-state IC3 (Cimatti and Griggio). Rather than working over
concrete states, it maintains an implicit predicate abstraction refined by the
counterexamples IC3 itself produces. This is the single largest algorithmic
upgrade available to JKind's PDR, and it subsumes much of what a hand-rolled
CEGAR loop would provide.

**PD-KIND.** Property-directed k-induction (Jovanović and Dutertre, as
implemented in Sally) combines k-induction's handling of temporal depth with
IC3's property direction. It targets precisely JKind's setting — infinite-state
transition systems over linear arithmetic — and JKind already has both
ingredients separately.

**Interpolation-based model checking.** McMillan's method derives an
over-approximation of reachable states from the proof of a failed BMC query.
JKind bundles SMTInterpol, which is an *interpolating* solver, and does not
currently ask it for interpolants. That is an unusual amount of capability left
on the table.

**Horn-clause solving via Spacer.** Encode the transition system as constrained
Horn clauses and hand them to Z3's Spacer engine, which is a mature IC3-IA
implementation. This is the cheapest way to get a strong engine, at the cost of
depending on Z3 for it. Worth prototyping before implementing IC3-IA by hand,
if only to calibrate what is achievable.

**Data-driven invariant inference (ICE learning).** JKind's invariant
generation is template-based: it proposes candidate relations from fixed
templates and filters them. ICE-style learning instead infers candidates from
positive examples, negative examples and implication pairs harvested from
failed proofs. It complements templates rather than replacing them.

**Abstract interpretation for auxiliary invariants.** A cheap interval or
octagon pass over the Lustre program produces numeric bounds that strengthen
k-induction at almost no cost. Often the difference between a property needing
k = 20 and needing k = 2.

### CEGAR and CEGIS specifically

**CEGAR** fits JKind well, and there is already a concrete motivation in tree.
Slicing is an abstraction: it removes variables outside the property's cone of
influence. It is applied once and never refined, and finding 7 in
`fuzzing/FINDINGS.md` is exactly the failure mode that produces — a spurious
counterexample from an abstraction that dropped an unsatisfiable assertion.

A CEGAR loop closes that hole structurally rather than by special-casing
assertions: every counterexample found in the abstract model is replayed
against the concrete one, and a counterexample that does not concretize
triggers refinement instead of being reported. Note that
`testing/fuzzing/Interp.java` already performs exactly this concretization
check — it was written as a test oracle, but it is the same operation a CEGAR
loop needs, on the same fragment.

The natural first step is smaller than full CEGAR and worth doing regardless:
validate counterexamples before reporting them. That converts finding 7 from a
wrong answer into a refinement trigger.

**CEGIS** is a weaker fit for the model-checking path, because JKind is
verifying given properties rather than synthesizing artifacts. It is a strong
fit in two other places:

- *Invariant generation*, where the thing being synthesized is a strengthening
  invariant and the counterexamples are states that break the current
  candidate. This is close to ICE learning above.
- *JRealizability*, where the synthesized artifact is a controller or a witness
  strategy. This is the textbook CEGIS setting, and the existing realizability
  engine is the place it would land.
