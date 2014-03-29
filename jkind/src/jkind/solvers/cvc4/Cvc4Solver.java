package jkind.solvers.cvc4;

import jkind.solvers.smtlib2.SmtLib2Solver;

public class Cvc4Solver extends SmtLib2Solver {
	public Cvc4Solver() {
		super(new ProcessBuilder("cvc4", "--lang", "smt"), "CVC4");
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
		send("(set-option :incremental true)");
		send("(set-option :rewrite-divk true)");
		send("(set-logic AUFLIRA)");
	}
}
