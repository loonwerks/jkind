#!/usr/bin/env python3
"""Compare JKind's answers across two builds or two solvers.

Runs every model through both configurations and reports any property whose
answer changes. Use it when upgrading a bundled solver or adding support for a
new one: a solver upgrade should change how fast answers arrive, never what the
answers are.

Example:
    ./compare.py --jar-a old.jar --jar-b new.jar --models ../*.lus
    ./compare.py --jar-a jkind.jar --solver-a smtinterpol --solver-b z3 \
                 --models ../*.lus
"""

import argparse
import concurrent.futures
import glob
import os
import subprocess
import sys
import xml.etree.ElementTree as ET

# Answers that mean the solver reached a conclusion. Anything else (unknown, a
# timeout, an error) is reported separately, because differing resource limits
# legitimately change those.
DECIDED = {"valid", "falsifiable"}


def run(jar, solver, model, timeout, java, extra, env_overrides=None):
    """Runs one model, returning {property: answer} plus a status string."""
    command = [java, "-jar", jar, "-jkind", "-xml_to_stdout", "-timeout", str(timeout)]
    if solver:
        command += ["-solver", solver]
    command += extra + [model]
    # JKind locates each solver through <SOLVER>_HOME, so pinning a specific
    # build is a matter of the environment rather than the command line.
    env = dict(os.environ)
    for assignment in env_overrides or []:
        name, _, value = assignment.partition("=")
        env[name] = value

    try:
        done = subprocess.run(
            command, capture_output=True, timeout=timeout + 20, text=True, env=env
        )
    except subprocess.TimeoutExpired:
        return {}, "killed"

    out = done.stdout
    start = out.find("<?xml")
    if start < 0:
        return {}, "no-xml(exit=%d)" % done.returncode

    try:
        root = ET.fromstring(out[start:])
    except ET.ParseError as exc:
        return {}, "bad-xml(%s)" % exc

    answers = {}
    for prop in root.iter("Property"):
        answer = prop.find("Answer")
        answers[prop.get("name")] = (answer.text or "").strip() if answer is not None else "?"
    return answers, "ok"


def compare_one(model, args):
    a, status_a = run(args.jar_a, args.solver_a, model, args.timeout, args.java,
                      args.extra, args.env_a)
    b, status_b = run(args.jar_b or args.jar_a, args.solver_b, model, args.timeout,
                      args.java, args.extra, args.env_b)

    differences = []
    for name in sorted(set(a) | set(b)):
        answer_a = a.get(name, "<absent>")
        answer_b = b.get(name, "<absent>")
        if answer_a == answer_b:
            continue
        # Only a change between two decided answers is a real disagreement;
        # decided-vs-unknown usually just reflects timing.
        severity = "DISAGREE" if answer_a in DECIDED and answer_b in DECIDED else "differs"
        differences.append((severity, name, answer_a, answer_b))
    return model, status_a, status_b, differences


def main():
    parser = argparse.ArgumentParser(description=__doc__,
                                     formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--jar-a", required=True)
    parser.add_argument("--jar-b", help="defaults to --jar-a, for solver-only comparisons")
    parser.add_argument("--solver-a")
    parser.add_argument("--solver-b")
    parser.add_argument("--models", nargs="+", required=True)
    parser.add_argument("--timeout", type=int, default=30)
    parser.add_argument("--jobs", type=int, default=os.cpu_count())
    parser.add_argument("--java", default="java")
    parser.add_argument("--extra", nargs="*", default=[],
                        help="extra arguments passed to both invocations")
    parser.add_argument("--env-a", nargs="*", default=[], metavar="NAME=VALUE",
                        help="environment for side A, e.g. Z3_HOME=/opt/z3-4.16")
    parser.add_argument("--env-b", nargs="*", default=[], metavar="NAME=VALUE",
                        help="environment for side B")
    args = parser.parse_args()

    models = []
    for pattern in args.models:
        models.extend(sorted(glob.glob(pattern)) if any(c in pattern for c in "*?[") else [pattern])
    models = [m for m in models if os.path.isfile(m)]
    if not models:
        sys.exit("no models matched")

    label_a = "%s/%s %s" % (os.path.basename(args.jar_a), args.solver_a or "default",
                            " ".join(args.env_a))
    label_b = "%s/%s %s" % (os.path.basename(args.jar_b or args.jar_a),
                            args.solver_b or "default", " ".join(args.env_b))
    print("A = %s" % label_a)
    print("B = %s" % label_b)
    print("%d models, timeout %ds, %d jobs\n" % (len(models), args.timeout, args.jobs))

    disagreements = 0
    differences = 0
    failures = 0
    with concurrent.futures.ThreadPoolExecutor(max_workers=args.jobs) as pool:
        futures = [pool.submit(compare_one, m, args) for m in models]
        for future in concurrent.futures.as_completed(futures):
            model, status_a, status_b, diffs = future.result()
            if status_a != "ok" or status_b != "ok":
                failures += 1
                print("%-44s A:%s B:%s" % (os.path.basename(model), status_a, status_b))
            for severity, name, answer_a, answer_b in diffs:
                if severity == "DISAGREE":
                    disagreements += 1
                else:
                    differences += 1
                print("%-9s %-34s %-18s A=%-12s B=%s"
                      % (severity, os.path.basename(model), name, answer_a, answer_b))

    print("\n%d models | %d contradictions | %d undecided differences | %d run failures"
          % (len(models), disagreements, differences, failures))
    return 1 if disagreements else 0


if __name__ == "__main__":
    sys.exit(main())
