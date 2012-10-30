package jkind.lustre;

import java.util.Collections;
import java.util.List;

public class Node extends AST {
	final public List<Constant> constants;
	final public List<VarDecl> inputs;
	final public List<VarDecl> outputs;
	final public List<VarDecl> locals;
	final public List<Equation> equations;
	final public List<String> properties;

	public Node(Location location, List<Constant> constants, List<VarDecl> inputs,
			List<VarDecl> outputs, List<VarDecl> locals, List<Equation> equations,
			List<String> properties) {
		super(location);
		this.constants = Collections.unmodifiableList(constants);
		this.inputs = Collections.unmodifiableList(inputs);
		this.outputs = Collections.unmodifiableList(outputs);
		this.locals = Collections.unmodifiableList(locals);
		this.equations = Collections.unmodifiableList(equations);
		this.properties = Collections.unmodifiableList(properties);
	}
}
