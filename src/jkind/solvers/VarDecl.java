package jkind.solvers;

import jkind.lustre.Type;
import jkind.sexp.Symbol;

public class VarDecl {
	final public Symbol id;
	final public Type type;
	
	public VarDecl(Symbol id, Type type) {
		this.id = id;
		this.type = type;
	}
}
