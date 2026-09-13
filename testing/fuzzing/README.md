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

See `CexFuzzer`. Runs complete JKind invocations and independently replays
reported counterexamples through a Lustre interpreter.

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
