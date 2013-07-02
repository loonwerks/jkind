JKind
=====

JKind is a Java implementation of the [KIND model
checker](http://clc.cs.uiowa.edu/Kind/). KIND is a parallel
multi-property k-induction based model checker for Lustre programs.

Downloads
---------
The latest release of JKind is available [here](https://github.com/agacek/jkind/releases).

Lustre2excel is an optional utility to convert Lustre programs to Excel spreadsheets. It is updated infrequently, so the latest version is somewhere down the releases page (here)[https://github.com/agacek/jkind/releases].

Design Goals
------------

JKind is designed to be cross-platform, reliable, and easy to extend.
Power and performance are secondary goals. Additionally, JKind
attempts to be compatible with pkind, the standard implementation of
KIND. Differences between JKind and pkind are described
[here](https://github.com/agacek/jkind/wiki/Differences-with-pkind).


Installation Notes
------------------

By default, JKind requires [yices (version 1)](http://yices.csl.sri.com/download.shtml) to be installed on your PATH. Alternatively, [CVC4](http://cvc4.cs.nyu.edu/web/) and [Z3](http://z3.codeplex.com/).
