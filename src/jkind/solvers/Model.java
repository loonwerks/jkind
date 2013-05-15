package jkind.solvers;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import jkind.sexp.Symbol;

public abstract class Model {
	public abstract Value getValue(Symbol sym);
	public abstract Value getFunctionValue(String fn, BigInteger index);
	public abstract Set<String> getFunctions();

	protected Map<String, StreamDef> definitions;
	public void setDefinitions(Map<String, StreamDef> definitions) {
		this.definitions = definitions;
	}
	
	protected Map<String, StreamDecl> declarations;
	public void setDeclarations(Map<String, StreamDecl> declarations) {
		this.declarations = declarations;
	}
}
