JKind
=====

JKind is a Java implementation of the [KIND model
checker](http://clc.cs.uiowa.edu/Kind/). KIND is a parallel
multi-property k-induction based model checker for Lustre programs.

Downloads
---------
[JKind 1.3.1](https://github.com/agacek/jkind/blob/uploads/jkind-1.3.1.zip?raw=true)

[Lustre2excel 1.2](https://github.com/agacek/jkind/blob/uploads/lustre2excel-1.2.zip?raw=true) - an optional utility to convert Lustre programs to Excel spreadsheets

Design Goals
------------

JKind is designed to be cross-platform, reliable, and easy to extend.
Power and performance are secondary goals. Additionally, JKind
attempts to be compatible with pkind, the standard implementation of
KIND. Differences between JKind and pkind are described
[here](https://github.com/agacek/jkind/wiki/Differences-with-pkind).


Installation Notes
------------------

JKind requires [yices (version 1)](http://yices.csl.sri.com/download.shtml) to be installed on your PATH.
