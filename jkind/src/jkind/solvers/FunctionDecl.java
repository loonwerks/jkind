package jkind.solvers;

import java.util.List;

import jkind.lustre.Type;
import jkind.sexp.Symbol;

public class FunctionDecl extends Decl {
	final private List<Type> inputs;

	public FunctionDecl(Symbol id, List<Type> inputs, Type output) {
		super(id, output);
		this.inputs = inputs;
	}

	public FunctionDecl(String id, List<Type> inputs, Type output) {
		super(id, output);
		this.inputs = inputs;
	}
	
	public List<Type> getInputs() {
		return inputs;
	}
}
