package jkind.solvers;

import jkind.lustre.Type;
import jkind.sexp.Symbol;

public class StreamDecl extends Decl {
	public StreamDecl(Symbol id, Type output) {
		super(id, output);
	}

	public StreamDecl(String id, Type output) {
		super(id, output);
	}
}
