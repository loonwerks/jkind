# JKind

JKind is a Java implementation of the [KIND model
checker](http://clc.cs.uiowa.edu/Kind/). KIND is a parallel
multi-property k-induction based model checker for Lustre programs.

## Design Goals

JKind is designed to be cross-platform, reliable, and easy to extend.
Power and performance are secondary goals. Additionally, JKind
attempts to be compatible with pkind, the standard implementation of
KIND.

## Current Limitations

- Weak invariant generation
- No abstraction
- No path compression
- No termination checking
- Single node Lustre only
- No inlining
- No inductive counter-examples
- Yices is the only supported back-end solver

## Installation Notes

JKind requires yices to be installed on your PATH.

## Other Notes

JKind uses the convention that k=1 is the same as regular induction.
