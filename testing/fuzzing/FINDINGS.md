# Fuzzing findings

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

## 5. Deep expression nesting overflows the stack (minor)

`findings/05-deep-nesting.lus.sh` generates the input.

A few thousand nested parentheses overflow the Java stack in the ANTLR-generated
parser or in a later recursive visitor, producing a `StackOverflowError` and
exit `-8`. Both parsing and the AST visitors are recursive with no depth limit,
so the depth that fails varies with stack size and with which pass gets there
first.

This is lower severity — the inputs are not realistic — but the current
behaviour is a stack trace rather than a diagnostic naming a limit.

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
