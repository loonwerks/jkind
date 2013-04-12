package jkind.invariant;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Lambda;
import jkind.util.Util;

public class Invariant {
	final private Lambda lambda;
	final private String text;

	public Invariant(Lambda lambda, String text) {
		this.lambda = lambda;
		this.text = text;
	}
	
	public Invariant(String prop) {
		this.lambda = new Lambda(Util.I, new Cons(new Symbol("$" + prop), Util.I));	
		this.text = prop;
	}
	
	@Override
	public String toString() {
		return text;
	}

	public Sexp instantiate(Sexp arg) {
		return lambda.instantiate(arg);
	}
}
