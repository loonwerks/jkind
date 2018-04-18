JKind
=====

JKind is an SMT-based infinite-state model checker for safety
properties in Lustre. JKind uses parallel cooperating engines
including k-induction, property directed reachability, and
template-based invariant generation.

Downloads
---------

JKind is written in Java and requires at least [Java
8](https://java.com/download). The latest release of JKind is available on the
[releases page](https://github.com/agacek/jkind/releases). This includes the
JKind model checker as well as the JRealizability, JLustre2Excel, and
JLustre2Kind tools.

Design Goals
------------

JKind is designed to be cross-platform, reliable, and easy to
extend. Power and performance are secondary goals. Additionally,
JKind attempts to be mostly compatible with pkind and [Kind
2](http://kind2-mc.github.io/kind2/), though this varies over
time due to developments in both systems.


Alternative Solvers (optional)
------------------------------

By default, JKind is packaged with [SMTInterpol](http://ultimate.informatik.uni-freiburg.de/smtinterpol/) 
as its underlying SMT solver. Advanced users may wish to install alternative solvers such as 
[Z3](https://github.com/Z3Prover/z3),
[Yices (version 1)](http://yices.csl.sri.com/download-yices1.shtml), 
[Yices 2](http://yices.csl.sri.com/index.shtml),
[CVC4](http://cvc4.cs.nyu.edu/web/), or
[MathSAT](http://mathsat.fbk.eu/).

Minimal IVCs enumeration (optional)
-----------------------------------
This branch of JKind supports online enumeration of Minimal Inductive Validity Cores (MIVCs) as described in [1]. In order to use the MIVC enumeration, run the jkind with following settings:
-timeout 1800 -all_ivcs -all_ivcs_alg 2 -all_assigned -solver z3

In particular, -timeout limits the time for the enumeration, and -all_ivcs_alg serves for choosing the enumeration algorithm to be used. Currently, there are 4 available algorithms, see https://github.com/jar-ben/jkind/blob/newalgorithm-shrink-tracking/jkind/src/jkind/engines/ivcs/AllIvcsExtractorEngine.java for more details. The algorithm presented in [1] is run using -all_ivcs_alg 2.

[1] 	Online Enumeration of All Minimal Inductive Validity Cores, Jaroslav Bendik, Elaheh Ghassabani, Michael Whalen, and Ivana Cerna. To be appeared at SEFM 2018.
