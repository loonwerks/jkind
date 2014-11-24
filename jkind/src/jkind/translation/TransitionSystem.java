package jkind.translation;

import java.util.List;

import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;

public class TransitionSystem {
	public static final String INIT = "%init";
	public static final String T = "T";

	private final List<VarDecl> base;
	private final List<VarDecl> prime;
	private final Sexp body;

	public TransitionSystem(List<VarDecl> base, List<VarDecl> prime, Sexp body) {
		this.base = base;
		this.prime = prime;
		this.body = body;
	}

	public List<VarDecl> getBase() {
		return base;
	}
	
	public List<VarDecl> getPrime() {
		return prime;
	}

	public Sexp getBody() {
		return body;
	}
}
