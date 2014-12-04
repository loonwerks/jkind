JKind
=====

JKind is a Java implementation of the [KIND model
checker](http://clc.cs.uiowa.edu/Kind/). KIND is a parallel
multi-property k-induction model checker for Lustre programs.

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
attempts to be mostly compatible with pkind, the standard
implementation of KIND. Differences between JKind and pkind are
described on the
[wiki](https://github.com/agacek/jkind/wiki/Differences-with-pkind).
JKind also attempts to be compatible with Kind 2, though this varies
over time due to developments in both systems.


Installation Notes
------------------

By default, JKind requires [Yices (version
1)](http://yices.csl.sri.com/download-yices1.shtml) to be installed on
your PATH. Alternatively, one can use
[CVC4](http://cvc4.cs.nyu.edu/web/), [Z3](http://z3.codeplex.com/), or
[Yices 2](http://yices.csl.sri.com/index.shtml).
