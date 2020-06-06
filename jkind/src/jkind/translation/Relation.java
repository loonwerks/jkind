package jkind.translation;

import java.util.List;

import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;

public class Relation {
	public static final String T = "T"; // transition

	private final String name;
	private final List<VarDecl> inputs;
	private final Sexp body;

	public Relation(String name, List<VarDecl> inputs, Sexp body) {
		this.name = name;
		this.inputs = inputs;
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public List<VarDecl> getInputs() {
		return inputs;
	}

	public Sexp getBody() {
		return body;
	}
}
