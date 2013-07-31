package jkind.lustre;

import java.util.Collections;
import java.util.List;

public class Node extends Ast {
	final public String id;
	final public List<VarDecl> inputs;
	final public List<VarDecl> outputs;
	final public List<VarDecl> locals;
	final public List<Equation> equations;
	final public List<String> properties;
	final public List<Expr> assertions;

	public Node(Location location, String id, List<VarDecl> inputs, List<VarDecl> outputs,
			List<VarDecl> locals, List<Equation> equations, List<String> properties,
			List<Expr> assertions) {
		super(location);
		this.id = id;
		this.inputs = Collections.unmodifiableList(inputs);
		this.outputs = Collections.unmodifiableList(outputs);
		this.locals = Collections.unmodifiableList(locals);
		this.equations = Collections.unmodifiableList(equations);
		this.properties = Collections.unmodifiableList(properties);
		this.assertions = Collections.unmodifiableList(assertions);
	}
	
	@Override
	public <T> T accept(AstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
