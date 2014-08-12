package jkind.invariant;

import jkind.lustre.values.BooleanValue;
import jkind.sexp.Symbol;
import jkind.solvers.Eval;
import jkind.solvers.Lambda;
import jkind.solvers.Model;

public class Candidate {
	private final Lambda lambda;
	private final String text;

	public Candidate(Lambda lambda, String text) {
		this.lambda = lambda;
		this.text = text;
	}

	public boolean isTrue(Model model, int k) {
		return new Eval(model).eval(lambda.instantiate(k)) == BooleanValue.TRUE;
	}

	public Lambda getLambda() {
		return lambda;
	}

	@Override
	public String toString() {
		return text;
	}

	public static final Candidate TRUE = new Candidate(new Lambda(new Symbol("true")), "true");
	public static final Candidate FALSE = new Candidate(new Lambda(new Symbol("false")), "false");
}
