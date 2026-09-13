# Fuzzing findings

**All seven findings below are fixed.** Each entry keeps the original report
and records the fix underneath it. Verification after the fixes:

| Campaign | Result |
|---|---|
| Tier 1, 500,000 inputs, seed 7 (the seed that originally found 22,226 crashes) | **0 findings** |
| Tier 1, 500,000 inputs, fresh seed 4242 | **0 findings** |
| Regression corpus, before vs after the fixes | **0 contradictions** |

Findings from the harness in this directory. Each one makes JKind print a Java
stack trace and exit `-8` (`UNCAUGHT_EXCEPTION`) instead of either analyzing the
model or rejecting it with a diagnostic. Reproducers are in `findings/`.

Campaign: 500,000 front-end inputs, 31.6s wall (15,825 inputs/s, 16 threads,
base seed 7). 41.2% of inputs reached the end of the front end; 271,810 were
cleanly rejected (158,720 parse errors, 113,090 static analysis errors);
22,226 crashed across 6 distinct signatures. No hangs.

To reproduce a whole campaign, see `README.md`. To replay one input:

```bash
java -cp build/fuzz:build/jkind.jar jkind.fuzz.Repro --replay <seed>
```

---

## 1. An empty subrange crashes before it can be reported

`findings/01-empty-subrange.lus` — 21,395 occurrences, by far the most common.

```lustre
node main(x : subrange [5, 2] of int) returns (ok : bool);
```

```
jkind.JKindException: Assertion failed
	at jkind.Assert.isTrue(Assert.java:12)
	at jkind.lustre.SubrangeIntType.<init>(SubrangeIntType.java:16)
	at jkind.lustre.parsing.LustreToAstVisitor.visitSubrangeType(LustreToAstVisitor.java:305)
```

JKind already has the right diagnostic for this input.
`jkind.analysis.SubrangesNonempty` exists only to report `subrange is empty`.
But `SubrangeIntType`'s constructor asserts `low <= high`, and the AST is built
during parsing, long before static analysis runs. The assertion always fires
first, so **`SubrangesNonempty` is unreachable dead code** for user input.

The fix has to decide which layer owns the invariant. Either the constructor
accepts empty subranges and lets `SubrangesNonempty` report them, or the
parser's visitor checks bounds and raises `LustreParseException` (which
`Main.parseLustre` already turns into a clean `exit -5`).

**Fixed.** The `low <= high` assertion is gone from `SubrangeIntType`'s
constructor, so an empty range is representable and reaches the analysis that
exists to report it. `SubrangesNonempty` is no longer dead code: the model now
produces `Error at line 5:14 subrange is empty` and exits `-6`. The type
checker already tolerated empty ranges — it only ever joins, negates and
compares them — so nothing downstream needed changing, and the analysis fails
the program before translation can see one.

## 2. A nested `pre` inside a condact throws ConcurrentModificationException

`findings/02-condact-nested-pre.lus` — 691 occurrences.

```lustre
node inner(x : int) returns (y : int);
let
  y = 0 -> pre (0 -> pre x);
tel;
-- used as: condact(c, inner(x), 0)
```

```
java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.computeIfAbsent(HashMap.java:1229)
	at jkind.translation.RemoveCondacts$4.visit(RemoveCondacts.java:175)
```

In `RemoveCondacts.clockPres`, the mapping function passed to
`cache.computeIfAbsent` recursively calls `e.expr.accept(this)`, which re-enters
`computeIfAbsent` on the same `HashMap` for the inner `pre`. `HashMap` detects
the reentrant modification and throws. This is a plain misuse of
`computeIfAbsent`, whose contract forbids the mapping function from modifying
the map.

Fixing it means not holding the map open across the recursion: compute the
replacement first, then `put` it, keeping the recursion outside the
`computeIfAbsent` call.

**Fixed.** `RemoveCondacts.clockPres` no longer rewrites the operand from
inside `computeIfAbsent`. It now looks the key up, and on a miss registers the
replacement in the map *before* recursing, so a nested `pre` finds the entry
already present instead of modifying the map from within its own mapping
function.

Worth noting that `createCondactNode` in the same file carries a comment
explaining that it was moved off `computeIfAbsent` for exactly this reason.
The fix had been applied in one place and missed in the other.

## 3. A condact inside another condact's call arguments throws ClassCastException

`findings/03-nested-condact.lus` — 31 occurrences.

```lustre
ok = condact(c, inner(condact(c, inner(x), 0)), 0) >= 0;
```

```
java.lang.ClassCastException: class jkind.lustre.IdExpr cannot be cast to
    class jkind.lustre.NodeCallExpr
	at jkind.lustre.visitors.ExprMapVisitor.visit(ExprMapVisitor.java:69)
	at jkind.translation.InlineNodeCalls.visitEquationsQueue(InlineNodeCalls.java:62)
```

`ExprMapVisitor.visit(CondactExpr)` unconditionally casts the result of
`e.call.accept(this)` to `NodeCallExpr`. That holds only while a transformation
maps node calls to node calls. `InlineNodeCalls` replaces a call with an
`IdExpr` naming the inlined output, so any condact that survives to that pass
crashes.

The inner condact survives because `RemoveCondacts` does not reach condacts
nested inside the argument list of another condact's call. Both halves are worth
addressing: the cast is unsound as written, and the nested condact should have
been removed.

**Fixed**, and the cause was narrower than it looked. `RemoveCondacts`
rebuilt the rewritten call with `args.addAll(e.call.args)` — the *original*
arguments — instead of the visited ones, silently discarding the rewrite it had
just performed. A condact nested in those arguments therefore survived
untouched into `InlineNodeCalls`, which replaced its call with an `IdExpr` and
tripped the cast. Using `call.args` fixes it.

The cast in `ExprMapVisitor.visit(CondactExpr)` is left alone: the parser
guarantees a condact's call is a `NodeCallExpr`, and with the rewrite applied
correctly no condact reaches a pass that would change that.

## 4. A record literal with an untypable field crashes the type checker

`findings/04-record-null-field-type.lus` — 102 occurrences (two signatures).

```lustre
p = if b then pt { x = no_such_var; y = b } else 0;
```

```
Error at line 7:25 unknown variable no_such_var
java.lang.NullPointerException: Cannot invoke "jkind.lustre.Type.accept(...)"
    because the return value of "java.util.Map$Entry.getValue()" is null
	at jkind.lustre.visitors.TypeMapVisitor.visit(TypeMapVisitor.java:37)
	at jkind.analysis.RemoveSubranges.remove(RemoveSubranges.java:10)
	at jkind.analysis.TypeChecker.simple(TypeChecker.java:654)
```

`TypeChecker.visit(RecordExpr)` (line 529) builds `new RecordType(location, id,
fields)` from the *inferred* types of the field expressions. A field whose
expression has no type at all — an unknown variable — contributes a `null`
value. The record type is then returned as if it were well-formed, and the next
diagnostic that formats it via `simple()` walks into the null and dies.

Note that JKind reports the real error correctly, then crashes while formatting
a *second* error. Users lose the diagnostics they were about to receive.

The fix is for `visit(RecordExpr)` to return `null` when any field type is null,
which is how the rest of the type checker signals "already reported".

**Fixed.** `TypeChecker.visit(RecordExpr)` now returns `null` when any field
expression has no type, which is the convention the rest of the type checker
already uses for "an error has been reported here". Previously it returned a
`RecordType` with a null field type embedded, and the next diagnostic to format
that type walked into the null. The model now reports `unknown variable
no_such_var` and exits `-6`, with the user's diagnostics intact.

## 5. Deep expression nesting overflows the stack (minor)

`findings/05-deep-nesting.lus.sh` generates the input.

A few thousand nested parentheses overflow the Java stack in the ANTLR-generated
parser or in a later recursive visitor, producing a `StackOverflowError` and
exit `-8`. Both parsing and the AST visitors are recursive with no depth limit,
so the depth that fails varies with stack size and with which pass gets there
first.

This is lower severity — the inputs are not realistic — but the current
behaviour is a stack trace rather than a diagnostic naming a limit.

**Fixed**, in both directions, and it is less minor than first assessed:
JKind's own launcher used the default JVM stack, while its main use case is
machine-generated Lustre with very deep expressions.

The entry points (`JKind`, `JRealizability`, `JLustre2Kind`) now catch
`StackOverflowError` ahead of the catch-all and report it as a diagnostic
naming the remedy, exiting `-7` instead of printing a stack trace. Separately
the launcher scripts now default to `-Xss32m`, overridable through
`JKIND_JAVA_OPTS`, so realistic models do not reach that path at all. A model
with 4,000 levels of nesting fails cleanly on the default stack and analyzes
successfully under the new launcher default.

## 6. A property cannot be declared on a bool type alias (minor, not a crash)

Found while tuning the generator rather than by a crash oracle.

```lustre
type flag = bool;
node main() returns ();
var ok : flag;
let
  ok = true;
  --%PROPERTY ok;   -- rejected: "does not have type bool"
tel;
```

`StaticAnalyzer.getBooleans` tests `varDecl.type == NamedType.BOOL` by
reference, so a user-defined alias for `bool` fails even though it resolves to
`bool`. The check runs before `InlineUserTypes`, so the alias has not yet been
resolved. Resolving the type through the type table first would accept it.

---

# Tier 2: end-to-end answer checking

Campaign: 3,000 generated models, 215s wall, each run under both SMTInterpol
and Z3 with `-n 8 -timeout 2 -pdr_max 0` and hard-killed at 10s.

| Outcome | Count |
|---|---|
| counterexamples replayed and validated | 2,787 |
| valid properties that survived random-trace refutation | 1,507 |
| cross-solver agreements (SMTInterpol vs Z3) | 4,309 |
| **cross-solver contradictions** | **0** |
| **unsound VALID results found** | **0** |
| findings | 11 (all one class, below) |

Two negative results are worth stating plainly. Across 1,507 properties proved
valid, no concrete trace ever refuted one, and SMTInterpol and Z3 never
contradicted each other on 4,309 comparisons. On this fragment the proof
engines look sound.

The oracles are not inert: with `-Djkind.fuzz.sabotage=true` the interpreter's
arrow operator is deliberately wrong, and the same campaign immediately reports
counterexample mismatches. A clean run means something only because the
sabotaged run fails.

**Fixed.** `StaticAnalyzer.getBooleans` resolves each variable's declared
type through the program's type table before comparing against
`NamedType.BOOL`, rather than comparing the unresolved type by reference. The
check runs before `InlineUserTypes`, so the alias is still present at that
point and had to be resolved explicitly.

## 7. Slicing drops inconsistent assertions, producing impossible counterexamples

`findings/07-sliced-assertion.lus` — all 11 tier-2 findings are this.

```lustre
node main() returns (o : int);
var
  v : int;
  ok : bool;
let
  o = -3;
  v = 2;
  ok = false;
  assert (v = o);
  --%PROPERTY ok;
tel;
```

```
jkind -jkind             =>  INVALID PROPERTIES: [ok]
jkind -jkind -no_slicing =>  VALID PROPERTIES: [ok]
```

The assertion is unsatisfiable, so the model has no behaviours and every
property holds vacuously. `-no_slicing` gets this right. The default does not:
slicing removes `v`, `o` and the assertion because they lie outside the
property's cone of influence, and BMC then reports a counterexample in which
`ok` is false — a trace the assertion forbids.

Cone-of-influence slicing is only semantics-preserving while the assertions are
satisfiable. When they are not, the sliced model has behaviours the original
does not, and any counterexample found in it is spurious.

This was found by replaying JKind's own counterexamples through the interpreter
and checking the assertions, which is why it shows up as "counterexample
violates the model's own assertions" rather than as a crash. Two answers that
contradict each other under a flag that is documented only as an optimization
is the clearest evidence that one of them is wrong.

JKind ships a consistency checker (`testing/consistency-checker/`), which
suggests inconsistent assertions are a known hazard; the gap is that ordinary
analysis does not notice when slicing has removed the inconsistency.

**Fixed.** Assertions are no longer sliced away. `DependencyMap` now treats
everything an assertion mentions as a root, `LustreSlicer` keeps the closure of
those roots alongside the properties' closure, and `visitAssertions` retains
every assertion.

The reasoning: cone-of-influence slicing removes variables nothing depends on,
which is sound. Removing an assertion is different in kind — assertions
constrain which behaviours exist at all, so dropping one *adds* behaviours, and
added behaviours can only turn a valid property into a spurious counterexample.
That is exactly the direction of the bug. The extreme case is an unsatisfiable
assertion, where the original model has no behaviours and the sliced one has
many.

The model now reports `VALID`, agreeing with `-no_slicing`. Across the
regression corpus the change produces no contradictions, and slicing still
removes everything outside the combined property and assertion cones.
