# Solver backlog

## Load solvers automatically, without requiring `<SOLVER>_HOME`

Today `jkind.solvers.ProcessBasedSolver.getSolverPath` looks up
`<SOLVER>_HOME` in the environment and otherwise falls back to bare executable
name resolution through `PATH`. Every user therefore has to install each solver
themselves and wire up the environment before JKind can use anything but the
bundled SMTInterpol.

The ask is a Java package that locates and loads solvers automatically, so that
no environment variable is needed.

### Sketch

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

### Licensing constraints on shipping solver binaries

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

### A note on the existing SMTInterpol bundling

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

## Portfolio solving: race several solvers, take the first answer

Run the same proof obligation against several SMT backends concurrently and
accept whichever answers first. Solver performance on Lustre verification
conditions varies by orders of magnitude and not consistently in one
direction, so a portfolio buys robustness rather than peak speed: you stop
losing runs to the one backend that happens to be bad at this problem.

### Does it make sense with k-induction?

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
