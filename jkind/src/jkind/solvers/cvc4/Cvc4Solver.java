package jkind.solvers.cvc4;

import java.io.File;

import jkind.solvers.smtlib2.SmtLib2Solver;

public class Cvc4Solver extends SmtLib2Solver {
	public Cvc4Solver() {
		super(new ProcessBuilder(getCVC4(), "--lang", "smt"), "CVC4");
	}

	private static String getCVC4() {
		String home = System.getenv("CVC4_HOME");
		if (home != null) {
			return new File(new File(home, "bin"), "cvc4").toString();
		}
		return "cvc4";
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
		send("(set-option :incremental true)");
		send("(set-option :rewrite-divk true)");
		send("(set-logic AUFLIRA)");
	}
}
