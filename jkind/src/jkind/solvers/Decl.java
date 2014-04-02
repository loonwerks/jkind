package jkind.solvers;

import jkind.lustre.Type;
import jkind.sexp.Symbol;

public abstract class Decl {
	final protected Symbol id;
	final protected Type output;

	public Decl(Symbol id, Type output) {
		this.id = id;
		this.output = output;
	}
	
	public Decl(String id, Type output) {
		this.id = new Symbol(id);
		this.output = output;
	}

	public Symbol getId() {
		return id;
	}
	
	public Type getOutput() {
		return output;
	}
}
