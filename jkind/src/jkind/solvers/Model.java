package jkind.solvers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.sexp.Symbol;

public abstract class Model {
	public abstract Value getValue(Symbol sym);
	public abstract Value getFunctionValue(String fn, List<Value> inputs);
	public abstract Set<String> getFunctions();

	protected Map<String, StreamDef> definitions;
	public void setDefinitions(Map<String, StreamDef> definitions) {
		this.definitions = definitions;
	}
	
	protected Map<String, Decl> declarations;
	public void setDeclarations(Map<String, Decl> declarations) {
		this.declarations = declarations;
	}
	
	public Value getStreamValue(String fn, BigInteger index) {
		List<Value> inputs = new ArrayList<>();
		inputs.add(new NumericValue(index.toString()));
		return getFunctionValue(fn, inputs);
	}
}
