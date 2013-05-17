package jkind.solvers;

import jkind.lustre.Type;
import jkind.sexp.Symbol;

public class StreamDecl {
	final private Symbol id;
	final private Type type;

	public StreamDecl(Symbol id, Type type) {
		this.id = id;
		this.type = type;
	}
	
	public StreamDecl(String id, Type type) {
		this.id = new Symbol(id);
		this.type = type;
	}

	public Symbol getId() {
		return id;
	}

	public Type getType() {
		return type;
	}
}
