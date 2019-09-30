package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

import java.util.List;

public class RepairNode extends Ast {
	public final String id;
	public final List<VarDecl> inputs;
	public final List<VarDecl> holeinputs;
	public final List<VarDecl> outputs;
	public final List<VarDecl> locals;
	public final List<Equation> equations;
	public final List<Expr> assertions;
	public final Contract contract; // Nullable

	public RepairNode(Location location, String id, List<VarDecl> inputs, List<VarDecl> holeinputs, List<VarDecl>
			outputs,
                      List<VarDecl> locals, List<Equation> equations,
                      List<Expr> assertions, Contract contract) {
		super(location);
		Assert.isNotNull(id);
		this.id = id;
		this.inputs = Util.safeList(inputs);
		this.holeinputs = Util.safeList(holeinputs);
		this.outputs = Util.safeList(outputs);
		this.locals = Util.safeList(locals);
		this.equations = Util.safeList(equations);
		this.assertions = Util.safeList(assertions);
		this.contract = contract;
	}

	public RepairNode(String id, List<VarDecl> inputs, List<VarDecl> holeinputs, List<VarDecl> outputs, List<VarDecl> locals,
                      List<Equation> equations, List<Expr> assertions, Contract contract) {
		this(Location.NULL, id, inputs, holeinputs, outputs, locals, equations, assertions, contract);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}