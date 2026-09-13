# Semantics of the interpreted fragment

This is the specification `Interp.java` implements. It is written to be
transliterable: a Lean (or Coq, or ACL2) formalization of these rules can be
checked against JKind on the same generated corpus, and any disagreement is a
bug in JKind, in the interpreter, or in the formalization.

Keeping the rules in one document rather than only in Java matters because the
interpreter is an *oracle*. An oracle whose behaviour is defined only by its
implementation cannot tell you which side of a disagreement is wrong.

## The fragment

A program in the fragment is a single node with no type, constant or function
declarations, and no node calls or condacts:

```
node N(i₁ : τ₁; …; i_m : τ_m) returns (o₁ : σ₁; …);
var l₁ : ρ₁; …;
let
  x = e_x;        -- exactly one equation per output and local
  …
  assert a;       -- zero or more
  --%PROPERTY p;  -- p is a bool output or local
tel;
```

Types τ are `bool`, `int`, `real`, and `subrange [a, b] of int`.

Three restrictions make evaluation total, and the generator enforces all three:

1. **Guarded `pre`.** Every `pre` occurs as the right operand of an `->`, so no
   evaluation ever reaches a step before 0. Unguarded `pre` is nondeterministic
   in Lustre and has no single value to check against.
2. **Acyclic definitions.** Each variable's defining expression refers only to
   inputs, to variables defined earlier, or — underneath a `pre` — to anything.
   This forbids algebraic loops, which have no step-by-step meaning.
3. **Total arithmetic.** Division and modulus are by non-zero constants, and
   modulus is by a positive constant.

## Values

A value is a boolean, an arbitrary-precision integer, or an exact rational.
Integers do not wrap and rationals do not round; both match the unbounded SMT
semantics JKind encodes against, so a disagreement is never an artifact of
machine arithmetic. A `subrange [a, b] of int` value is an integer; the bounds
constrain which values are well-typed, not how arithmetic behaves.

## Traces

A trace of length *n* assigns a value to each input at each step
*k* ∈ {0, …, n−1}. Given a trace, every variable has exactly one value at
every step, written ⟦x⟧ₖ, and every expression has a value ⟦e⟧ₖ.

For a variable *x* with equation `x = e`:

    ⟦x⟧ₖ = ⟦e⟧ₖ

which is well defined because definitions are acyclic.

## Expressions

Let *k* be the current step.

| Form | Value at step *k* |
|---|---|
| literal `c` | `c` |
| variable `x` | ⟦x⟧ₖ |
| `not e` | ¬⟦e⟧ₖ |
| `-e` | −⟦e⟧ₖ |
| `e₁ op e₂`, op ∈ {and, or, xor, =>} | the corresponding boolean function of ⟦e₁⟧ₖ and ⟦e₂⟧ₖ |
| `e₁ op e₂`, op ∈ {+, −, *} | the corresponding arithmetic operation |
| `e₁ = e₂`, `e₁ <> e₂` | equality of values |
| `e₁ op e₂`, op ∈ {<, <=, >, >=} | the corresponding order comparison |
| `if c then e₁ else e₂` | ⟦e₁⟧ₖ if ⟦c⟧ₖ, else ⟦e₂⟧ₖ |
| `e₁ -> e₂` | ⟦e₁⟧₀ if *k* = 0, else ⟦e₂⟧ₖ |
| `pre e` | ⟦e⟧ₖ₋₁, defined only for *k* > 0 |
| `real(e)` | ⟦e⟧ₖ as a rational |
| `floor(e)` | ⌊⟦e⟧ₖ⌋, the greatest integer ≤ ⟦e⟧ₖ |

Boolean operators are **not** short-circuiting. Every expression in the
fragment is total, so evaluation order is unobservable and this only matters
for a formalization that models partiality.

### Integer division and modulus

These follow SMT-LIB, not Java, and the difference is observable:

    e₁ div e₂  =  (⟦e₁⟧ₖ − (⟦e₁⟧ₖ mod |⟦e₂⟧ₖ|)) / ⟦e₂⟧ₖ
    e₁ mod e₂  =  ⟦e₁⟧ₖ mod ⟦e₂⟧ₖ,   always in [0, ⟦e₂⟧ₖ)

So `-5 div 3` is `-2` and `-5 mod 3` is `1`. Java would give `-1` and `-2`.
JKind's own `jkind.util.Util.smtDivide` carries the same comment.

## Assertions and properties

A trace is a **behaviour** of the model when every assertion holds at every
step. Assertions constrain which traces exist; they are not obligations.

- A property *p* is **valid** when ⟦p⟧ₖ is true at every step of every
  behaviour, for every trace length.
- A **counterexample** of length *n* is a behaviour whose first *n* steps
  satisfy every assertion and in which ⟦p⟧ₙ₋₁ is false.

Note the consequence that finding 7 in `FINDINGS.md` turns on: if the
assertions are unsatisfiable, the model has no behaviours, so every property is
vacuously valid and **no counterexample exists**. Any reported counterexample
must satisfy the assertions, or it is not a behaviour of the model.

## What this fragment does not cover

Arrays, records, enumerations, tuples, node calls, condacts, uninterpreted
functions, multiple nodes, unguarded `pre`, and algebraic loops. Tier-1 fuzzing
covers those for crash-resistance; checking JKind's *answers* on them would
need the interpreter extended first.

## Relationship to JKind

The interpreter parses with JKind's own parser and evaluates the resulting AST.
It is therefore independent of JKind's translation, encoding and solving — the
parts that decide answers — but shares its front end. A parser bug that
misreads a program the same way for both would not be caught here; tier-1's
print-and-reparse check is aimed at that gap instead.
