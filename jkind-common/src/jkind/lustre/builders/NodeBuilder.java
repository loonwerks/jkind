package jkind.lustre.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jkind.lustre.Contract;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class NodeBuilder {
	private String id;
	private List<VarDecl> inputs = new ArrayList<>();
	private List<VarDecl> outputs = new ArrayList<>();
	private List<VarDecl> locals = new ArrayList<>();
	private List<Equation> equations = new ArrayList<>();
	private List<String> properties = new ArrayList<>();
	private List<Expr> assertions = new ArrayList<>();
	private List<String> ivc = new ArrayList<>();
	private List<String> realizabilityInputs = null;
	private Contract contract = null;

	public NodeBuilder(String id) {
		this.id = id;
	}

	public NodeBuilder(Node node) {
		this.id = node.id;
		this.inputs = new ArrayList<>(node.inputs);
		this.outputs = new ArrayList<>(node.outputs);
		this.locals = new ArrayList<>(node.locals);
		this.equations = new ArrayList<>(node.equations);
		this.properties = new ArrayList<>(node.properties);
		this.assertions = new ArrayList<>(node.assertions);
		this.ivc = new ArrayList<>(node.ivc);
		this.realizabilityInputs = Util.copyNullable(node.realizabilityInputs);
		this.contract = node.contract;
	}

	public NodeBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public NodeBuilder addInput(VarDecl input) {
		this.inputs.add(input);
		return this;
	}

	public NodeBuilder addInputs(Collection<VarDecl> inputs) {
		this.inputs.addAll(inputs);
		return this;
	}

	public IdExpr createInput(String name, Type type) {
		this.inputs.add(new VarDecl(name, type));
		return new IdExpr(name);
	}

	public NodeBuilder clearInputs() {
		this.inputs.clear();
		return this;
	}

	public NodeBuilder addOutput(VarDecl output) {
		this.outputs.add(output);
		return this;
	}

	public NodeBuilder addOutputs(Collection<VarDecl> outputs) {
		this.outputs.addAll(outputs);
		return this;
	}

	public IdExpr createOutput(String name, Type type) {
		this.outputs.add(new VarDecl(name, type));
		return new IdExpr(name);
	}

	public NodeBuilder clearOutputs() {
		this.outputs.clear();
		return this;
	}

	public NodeBuilder addLocal(VarDecl local) {
		this.locals.add(local);
		return this;
	}

	public NodeBuilder addLocals(Collection<VarDecl> locals) {
		this.locals.addAll(locals);
		return this;
	}

	public IdExpr createLocal(String name, Type type) {
		this.locals.add(new VarDecl(name, type));
		return new IdExpr(name);
	}

	public NodeBuilder clearLocals() {
		this.locals.clear();
		return this;
	}

	public NodeBuilder addEquation(Equation equation) {
		this.equations.add(equation);
		return this;
	}

	public NodeBuilder addEquation(IdExpr var, Expr expr) {
		this.equations.add(new Equation(var, expr));
		return this;
	}

	public NodeBuilder addEquations(Collection<Equation> equations) {
		this.equations.addAll(equations);
		return this;
	}

	public NodeBuilder clearEquations() {
		this.equations.clear();
		return this;
	}

	public NodeBuilder addProperty(String property) {
		this.properties.add(property);
		return this;
	}

	public NodeBuilder addProperty(IdExpr property) {
		this.properties.add(property.id);
		return this;
	}

	public NodeBuilder addProperties(Collection<String> properties) {
		this.properties.addAll(properties);
		return this;
	}

	public NodeBuilder clearProperties() {
		this.properties.clear();
		return this;
	}

	public NodeBuilder addAssertion(Expr assertion) {
		this.assertions.add(assertion);
		return this;
	}

	public NodeBuilder addAssertions(Collection<Expr> assertions) {
		this.assertions.addAll(assertions);
		return this;
	}

	public NodeBuilder clearAssertions() {
		this.assertions.clear();
		return this;
	}

	public NodeBuilder addIvc(String e) {
		this.ivc.add(e);
		return this;
	}

	public NodeBuilder addIvcs(List<String> ivc) {
		this.ivc.addAll(ivc);
		return this;
	}

	public NodeBuilder clearIvc() {
		this.ivc.clear();
		return this;
	}

	public NodeBuilder setRealizabilityInputs(List<String> realizabilityInputs) {
		this.realizabilityInputs = Util.copyNullable(realizabilityInputs);
		return this;
	}

	public NodeBuilder setContract(Contract contract) {
		this.contract = contract;
		return this;
	}

	public Node build() {
		return new Node(Location.NULL, id, inputs, outputs, locals, equations, properties,
				assertions, realizabilityInputs, contract, ivc);
	}
}