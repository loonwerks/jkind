package jkind.solvers.z3;

import jkind.solvers.smtlib2.SmtLib2Solver;

public class Z3Solver extends SmtLib2Solver {
	public Z3Solver() {
		super(new ProcessBuilder("z3", "/smt2", "/in"), "Z3");
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
	}
}
