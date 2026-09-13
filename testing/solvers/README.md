# Solver testing

## `compare.py`

Diffs JKind's answers across two builds or two solvers. A solver upgrade should
change how fast answers arrive, never what the answers are, and this is how
that gets checked.

```bash
# Two builds, same solver: did upgrading the bundled SMTInterpol change anything?
./compare.py --jar-a old.jar --jar-b new.jar --models ../*.lus

# One build, two solvers.
./compare.py --jar-a jkind.jar --solver-b z3 --models ../*.lus

# Two versions of the same solver. JKind finds solvers through <SOLVER>_HOME,
# so the version is pinned per side through the environment.
./compare.py --jar-a jkind.jar --solver-a z3 --solver-b z3 \
    --env-a Z3_HOME=/opt/z3-4.16 --env-b Z3_HOME=/opt/z3-5.1 \
    --models ../*.lus
```

Output distinguishes two kinds of change:

- `DISAGREE` — both sides decided, and they decided differently. One of them is
  wrong. The tool exits non-zero.
- `differs` — one side decided and the other returned unknown or timed out.
  Expected when solvers differ in speed; not a correctness signal.

## Solver status

Versions this was last verified against, and what each needs.

| Solver | Verified | How JKind finds it | Notes |
|---|---|---|---|
| SMTInterpol | 2.5-1388 | bundled in `jkind.jar` | The default. No install needed. |
| Z3 | 5.1.0 | `Z3_HOME`, else `PATH` | Also verified at 4.16.0; no answer changed. |
| CVC5 | 1.3.4 | `CVC5_HOME`, else `PATH` | |
| Yices 2 | 2.7.0 | `YICES2_HOME`, else `PATH` | 2.7 changed its model format to standard SMT-LIB 2; JKind accepts both. |
| CVC4 | — | `CVC4_HOME`, else `PATH` | Deprecated upstream and superseded by CVC5. JKind warns when it is selected. |
| MathSAT | — | `MATHSAT_HOME`, else `PATH` | Research and evaluation license; must be installed by the user. |
| Yices 1 | — | `YICES_HOME`, else `PATH` | Long unmaintained upstream. |

`../BACKLOG.md` covers automatic solver discovery and portfolio solving, including
the licensing constraints on shipping any of these binaries.
