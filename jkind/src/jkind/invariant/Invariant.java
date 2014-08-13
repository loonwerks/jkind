package jkind.invariant;

import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Lambda;

public class Invariant {
	final private Lambda lambda;
	final private String text;

	public Invariant(Lambda lambda, String text) {
		this.lambda = lambda;
		this.text = text;
	}

	public Invariant(String prop) {
		this.lambda = new Lambda(prop, new Symbol(prop));
		this.text = prop;
	}

	@Override
	public String toString() {
		return text;
	}

	public Sexp instantiate(int k) {
		return lambda.instantiate(k);
	}
}
