JKind
=====

JKind is a Java implementation of the [Kind 2 model
checker](http://kind2-mc.github.io/kind2/). Kind 2 is a multi-engine
SMT-based automatic model checker for safety properties of Lustre
programs.

Downloads
---------

The latest release of JKind is available on the [releases
page](https://github.com/agacek/jkind/releases). This includes the
JKind model checker as well as the Lustre2excel and Lustre2kind
utilities.

Design Goals
------------

JKind is designed to be cross-platform, reliable, and easy to extend.
Power and performance are secondary goals. Additionally, JKind
attempts to be mostly compatible with pkind and Kind 2, though this
varies over time due to developments in both systems.


Alternative Solvers (optional)
------------------------------

By default, JKind is packaged with [SMTInterpol](http://ultimate.informatik.uni-freiburg.de/smtinterpol/) 
as its underlying SMT solver. Advanced users may wish to install alternative solvers such as 
[Z3](https://github.com/Z3Prover/z3),
[Yices (version 1)](http://yices.csl.sri.com/download-yices1.shtml) 
[Yices 2](http://yices.csl.sri.com/index.shtml),
[CVC4](http://cvc4.cs.nyu.edu/web/), or
[MathSAT](http://mathsat.fbk.eu/).
