package jkind.solvers;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

public abstract class Model {
	public abstract Value getValue(String name);
	public abstract Value getFunctionValue(String name, BigInteger index);
	public abstract Set<String> getFunctionNames();
	public abstract Model slice(Set<String> keep);

	protected Map<String, StreamDef> definitions;
	public void setDefinitions(Map<String, StreamDef> definitions) {
		this.definitions = definitions;
	}
	
	protected Map<String, StreamDecl> declarations;
	public void setDeclarations(Map<String, StreamDecl> declarations) {
		this.declarations = declarations;
	}
}
