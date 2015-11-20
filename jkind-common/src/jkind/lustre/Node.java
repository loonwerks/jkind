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
	public final Optional<Contract> contract;

	public Node(Location location, String id, List<VarDecl> inputs, List<VarDecl> outputs,
			List<VarDecl> locals, List<Equation> equations, List<String> properties,
			List<Expr> assertions, Optional<List<String>> realizabilityInputs, Optional<Contract> contract) {
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
		this.contract = Util.safeOptional(contract);
	}

	public Node(String id, List<VarDecl> inputs, List<VarDecl> outputs, List<VarDecl> locals,
			List<Equation> equations, List<String> properties, List<Expr> assertions,
			Optional<List<String>> realizabilityInputs, Optional<Contract> contract) {
		this(Location.NULL, id, inputs, outputs, locals, equations, properties, assertions,
				realizabilityInputs, contract);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}