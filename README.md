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
as its underlying SMT solver, so it works with no solver installation at all.
Advanced users may install an alternative solver and select it with `-solver`.

These are the versions JKind 4.7.0 is verified against:

| Solver | `-solver` | Verified version | Notes |
|---|---|---|---|
| [SMTInterpol](http://ultimate.informatik.uni-freiburg.de/smtinterpol/) | `smtinterpol` | 2.5-1388 | Bundled. The default; no install needed. |
| [Z3](https://github.com/Z3Prover/z3) | `z3` | 5.1.0 | Also verified at 4.16.0. Required for `-all_ivcs`. |
| [CVC5](https://github.com/cvc5/cvc5/releases/) | `cvc5` | 1.3.4 | |
| [Yices 2](http://yices.csl.sri.com/index.shtml) | `yices2` | 2.7.0 | |
| [MathSAT](http://mathsat.fbk.eu/) | `mathsat` | | Licensed for research and evaluation only. |
| [CVC4](http://cvc4.cs.nyu.edu/web/) | `cvc4` | | **Deprecated.** No longer developed upstream and superseded by CVC5. JKind warns when it is selected. |
| [Yices (version 1)](http://yices.csl.sri.com/download-yices1.shtml) | `yices` | | Long unmaintained upstream. |

Yices 2.7 changed its model output to the standard SMT-LIB 2 format. JKind
accepts both that and the older Yices-specific format, so new and old Yices 2
releases both work.

### Finding solvers

JKind locates each solver through `<SOLVER>_HOME` in the environment
(`Z3_HOME`, `CVC5_HOME`, `YICES2_HOME`, `MATHSAT_HOME`, `CVC4_HOME`,
`YICES_HOME`), using `$<SOLVER>_HOME/bin` when that directory exists and
`$<SOLVER>_HOME` otherwise. If the variable is not set, the executable is
resolved through `PATH`.

`testing/solvers/fetch.sh` downloads the verified versions of Z3, CVC5 and
Yices 2 and writes an `env.sh` that points those variables at them. The
solvers are fetched from their own upstream releases rather than redistributed
with JKind; each carries its own license, which the script reports.

### Java stack size

Parsing and the AST visitors recurse on expression structure, so deeply nested
models -- common in machine-generated Lustre -- need more than the default Java
stack. The launcher scripts pass `-Xss32m`, which can be overridden:

```
JKIND_JAVA_OPTS=-Xss64m jkind model.lus
```

A model too deeply nested for the current stack is now reported as an error
rather than crashing with a stack trace.

Minimal IVCs enumeration (optional)
-----------------------------------
JKind supports enumeration of All Minimal Inductive Validity Cores (All-MIVCs) to provide a full enumeration of all minimal set of model elements necessary for the inductive proofs of a safety property. 
Both the offline enumeration (as described in [1]) and the online enumeration (as described in [2]) have been implemented, and the offline enumeration is the algorithm made available for general use. 
To use the MIVC enumeration, run JKind with the following arguments: 
```
-all_ivcs -solver z3
```
In addition, use the -timeout argument to limit the time for the enumeration, e.g., 
```
-timeout 1800
```

[1] E. Ghassabani, M. W. Whalen, and A. Gacek. Efficient generation of all minimal inductive validity cores. 2017 Formal Methods in Computer Aided Design (FMCAD), pages 31–38, 2017.
[2] J. Bendik, E. Ghassabani, M. Whalen, and I. Cerna. Online enumeration of all minimal inductive validity cores. In International Conference on Software Engineering and Formal Methods, pages 189–204. Springer, 2018.


## Usage Examples

To run JKind over a test suite, navigate to the `jkindRegression` directory
(clone https://github.com/loonwerks/jkindRegression/tree/master first) and run
JKind from the command line as shown below:

### Example:

```bash
python3 ./jkindtest.py -dir /home/jkind/testing --recur -argfile ./test_arguments.xml -jkind /home/jkind/jkind/build/libs/jkind.jar
```

Output:

```text
JKind 4.7.0
Detected solvers: smtinterpol, z3, yices, yices2, cvc4, cvc5

test_result (jktest.testcase./home/jkind/testing/bridge_and_torch.lus)
...
```

