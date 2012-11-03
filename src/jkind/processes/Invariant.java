package jkind.processes;

import jkind.sexp.Sexp;

public class Invariant {
	final public String id;
	final public Sexp sexp;

	public Invariant(String id, Sexp sexp) {
		this.id = id;
		this.sexp = sexp;
	}
	
	@Override
	public String toString() {
		return id;
	}
}