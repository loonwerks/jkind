package jkind.lustre;

import java.util.Collections;
import java.util.List;

import jkind.lustre.visitors.AstVisitor;

public class Function extends Ast {
	final public String id;
	final public List<VarDecl> inputs;
	final public List<VarDecl> outputs;
	
	public Function(Location location, String id, List<VarDecl> inputs, List<VarDecl> outputs) {
		super(location);
		this.id = id;
		this.inputs = Collections.unmodifiableList(inputs);
		this.outputs = Collections.unmodifiableList(outputs);
	}
	
	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}
