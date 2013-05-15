package jkind.solvers;

import jkind.lustre.Type;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class StreamDef {
	final private StreamDecl decl;
	final private Lambda lambda;
	
	public StreamDef(Symbol id, Type type, Lambda lambda) {
		this.decl = new StreamDecl(id, type);
		this.lambda = lambda;
	}
	
	public StreamDef(String string, Type type, Lambda lambda) {
		this(new Symbol(string), type, lambda);
	}

	public Symbol getId() {
		return decl.getId();
	}
	
	public Lambda getLambda() {
		return lambda;
	}
	
	public Sexp instantiate(Sexp arg) {
		return lambda.instantiate(arg);
	}

	public Sexp getArg() {
		return lambda.getArg();
	}

	public Type getType() {
		return decl.getType();
	}
	
	public StreamDecl getDecl() {
		return decl;
	}
	
	public Sexp getBody() {
		return lambda.getBody();
	}
}
