# JKind fuzzing harness

Randomized testing infrastructure for JKind. It is organized in tiers, because
the cheap tier and the expensive tier find different classes of bug.

## Tier 1 — front-end fuzzing (`FrontEndFuzzer`)

Pushes large volumes of generated input through every pass JKind runs before
its solving engines start: parsing, static analysis, linearity checking,
translation, specification construction and equation inlining. None of these
need an SMT solver, so inputs run in process at thousands per second and
hundreds of thousands of inputs complete in minutes.

**The oracle.** JKind's contract for bad input is a diagnostic followed by
`System.exit` with a documented code (`-5` parse error, `-6` static analysis
error, `-7` unsupported feature). Anything that instead throws out of a pass
reaches the `catch (Throwable)` in `JKind.main`, which prints a Java stack trace
and exits `-8`. Those escaping throwables are the findings. The harness installs
a security manager so that a pass calling `System.exit` is recorded as a clean
rejection rather than killing the run.

A secondary oracle checks that pretty-printing a parsed program, reparsing it
and printing it again is idempotent. A mismatch means the printer and parser
disagree, which silently corrupts any tool that round-trips models.

Hangs are detected by a watchdog that captures the stack of any worker that
makes no progress, so infinite loops are reported rather than stalling the run.

### Input generators

| Generator | Share | Purpose |
|---|---|---|
| `well-typed` | 32% | Type-correct programs that reach the deepest passes |
| `nonlinear` | 8% | Non-linear arithmetic, accepted only by some solvers |
| `chaos` | 25% | Deliberate semantic faults that must be rejected cleanly |
| `mutation` | 25% | Mutations of the models in `testing/`, near-valid input |
| `token-soup` | 7% | Random Lustre tokens, for parser error recovery |
| `random-text` | 3% | Random printable bytes, for the lexer |

`LustreGen` is a typed generator: it tracks the type of every expression it
builds, so roughly 96% of `well-typed` programs pass static analysis and
exercise translation and slicing rather than dying at the type checker.

Every input is a pure function of its seed, so any finding replays exactly:

```bash
java -cp build/fuzz:build/jkind.jar jkind.fuzz.Repro --kind well-typed --seed 9068
```

`Repro --tally N` reports why a batch of generated programs is rejected, which
is how the generator is kept aimed at the deeper passes.

## Tier 2 — end-to-end and counterexample validation

`CexFuzzer` runs complete, tightly bounded JKind invocations and checks the
answers rather than just the absence of crashes. A Lustre interpreter
(`Interp`, specified in `SEMANTICS.md`) replays every counterexample JKind
reports: the values must match what the equations compute from the same
inputs, the assertions must hold, and the property must actually be false.
Properties reported valid are attacked with random concrete traces.

```bash
java -Djava.security.manager=allow -cp build/fuzz:build/jkind.jar \
  jkind.fuzz.CexFuzzer --iterations 3000 --threads 12 \
  --jar build/jkind.jar --compare-solver z3 --out cex-out
```

**The oracles are only meaningful if they can fire.** With
`-Djkind.fuzz.sabotage=true` the interpreter's arrow operator is deliberately
wrong, and the same campaign immediately reports counterexample mismatches. A
clean run proves something only alongside a failing sabotage run.

### Mode matrix (`--matrix`)

Everything above runs JKind in one configuration, which leaves its option
surface untested. `--matrix` runs each model under twelve configurations —
`default`, `no-slicing`, `ivc`, `all-ivcs`, `induct-cex`, `smooth`,
`bmc-only`, `kind-only`, and each of the four solvers — and adds three
oracles:

- **No crash in any mode.** stderr and the exit code are inspected; a Java
  stack trace or exit `-8` is a finding.
- **No option changes an answer.** Disabling an engine may leave a property
  undecided, but no option may turn a valid property into a falsifiable one.
  Every pair of configurations is compared.
- **IVC elements name real variables.**

```bash
source ../solvers/toolchain/env.sh      # so cvc5 and yices2 are found
java -Djava.security.manager=allow -cp build/fuzz:build/jkind.jar \
  jkind.fuzz.CexFuzzer --iterations 400 --matrix --jar build/jkind.jar \
  --out matrix-out
```

Findings 8 and 9 in `FINDINGS.md` came from this, within thirty models.

## Running

```bash
./build.sh                       # compiles JKind and the harness
java -Djava.security.manager=allow -Xss16m -Xmx8g \
  -cp build/fuzz:build/jkind.jar jkind.fuzz.FrontEndFuzzer \
  --iterations 500000 --threads 16 --corpus .. --out fuzz-out
```

`-Djava.security.manager=allow` is required: the harness relies on a security
manager to intercept `System.exit`. `-Xss16m` keeps deeply nested generated
expressions from reporting stack overflows that only reflect the harness's own
stack budget.

Findings land in `fuzz-out/report.md`, with a reproducer per distinct signature
in `fuzz-out/findings/`.
