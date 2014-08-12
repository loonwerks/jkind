package jkind.translation;

import java.util.List;

import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class TransitionRelation {
	public static final Symbol T = new Symbol("T");

	private final List<VarDecl> inputs;
	private final Sexp body;

	public TransitionRelation(List<VarDecl> inputs, Sexp body) {
		this.inputs = inputs;
		this.body = body;
	}

	public List<VarDecl> getInputs() {
		return inputs;
	}

	public Sexp getBody() {
		return body;
	}
}
