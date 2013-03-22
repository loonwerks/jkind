package jkind.invariant;

import jkind.sexp.Sexp;

public class Invariant {
	final public Sexp sexp;
	final public String text;

	public Invariant(Sexp sexp, String text) {
		this.sexp = sexp;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
