package jkind.solvers.cvc4;

import jkind.solvers.smtlib2.SmtLib2Solver;

public class Cvc4Solver extends SmtLib2Solver {
	public Cvc4Solver() {
		super(new ProcessBuilder(getCVC4(), "--lang", "smt"), "CVC4");
	}

	private static String getCVC4() {
		String cvc4Bin = System.getenv("CVC4_BIN");
		return cvc4Bin != null ? cvc4Bin : "cvc4";
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
		send("(set-option :incremental true)");
		send("(set-option :rewrite-divk true)");
		send("(set-logic AUFLIRA)");
	}
}
