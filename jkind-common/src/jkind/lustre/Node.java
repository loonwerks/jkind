package jkind.lustre;

import java.util.List;
import java.util.Optional;

import jkind.Assert;
import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class Node extends Ast {
	public final String id;
	public final List<VarDecl> inputs;
	public final List<VarDecl> outputs;
	public final List<VarDecl> locals;
	public final List<Equation> equations;
	public final List<String> properties;
	public final List<Expr> assertions;
	public final Optional<List<String>> realizabilityInputs;
	public final Optional<List<Contract>> contracts;

	public Node(Location location, String id, List<VarDecl> inputs, List<VarDecl> outputs,
			List<VarDecl> locals, List<Equation> equations, List<String> properties,
			List<Expr> assertions, Optional<List<String>> realizabilityInputs, Optional<List<Contract>> contracts) {
		super(location);
		Assert.isNotNull(id);
		this.id = id;
		this.inputs = Util.safeList(inputs);
		this.outputs = Util.safeList(outputs);
		this.locals = Util.safeList(locals);
		this.equations = Util.safeList(equations);
		this.properties = Util.safeList(properties);
		this.assertions = Util.safeList(assertions);
		this.realizabilityInputs = Util.safeOptionalList(realizabilityInputs);
		this.contracts = Util.safeOptionalList(contracts);
	}

	public Node(String id, List<VarDecl> inputs, List<VarDecl> outputs, List<VarDecl> locals,
			List<Equation> equations, List<String> properties, List<Expr> assertions,
			Optional<List<String>> realizabilityInputs, Optional<List<Contract>> contracts) {
		this(Location.NULL, id, inputs, outputs, locals, equations, properties, assertions,
				realizabilityInputs, contracts);
	}
	
	public Node(String id, List<VarDecl> inputs, List<VarDecl> outputs, List<VarDecl> locals,
			List<Equation> equations, List<String> properties, List<Expr> assertions,
			Optional<List<String>> realizabilityInputs) {
		this(Location.NULL, id, inputs, outputs, locals, equations, properties, assertions,
				realizabilityInputs, null);
	}

	public Node(String id, List<VarDecl> inputs, List<VarDecl> outputs, List<VarDecl> locals,
			List<Equation> equations, List<String> properties, List<Expr> assertions) {
		this(Location.NULL, id, inputs, outputs, locals, equations, properties, assertions, null, null);
	}

	public Node(String id, List<VarDecl> inputs, List<VarDecl> outputs, List<VarDecl> locals,
			List<Equation> equations, List<String> properties) {
		this(Location.NULL, id, inputs, outputs, locals, equations, properties, null, null, null);
	}

	public Node(String id, List<VarDecl> inputs, List<VarDecl> outputs, List<VarDecl> locals,
			List<Equation> equations) {
		this(Location.NULL, id, inputs, outputs, locals, equations, null, null, null, null);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}