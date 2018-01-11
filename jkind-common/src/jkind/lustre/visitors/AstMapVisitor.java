package jkind.lustre.visitors;

import java.util.List;

import jkind.lustre.Ast;
import jkind.lustre.Constant;
import jkind.lustre.Contract;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;

public class AstMapVisitor extends ExprMapVisitor implements AstVisitor<Ast, Expr> {
	@Override
	public Constant visit(Constant e) {
		return new Constant(e.location, e.id, e.type, e.expr.accept(this));
	}

	@Override
	public Equation visit(Equation e) {
		// Do not traverse e.lhs since they do not really act like Exprs
		return new Equation(e.location, e.lhs, e.expr.accept(this));
	}

	@Override
	public Function visit(Function e) {
		List<VarDecl> inputs = visitVarDecls(e.inputs);
		List<VarDecl> outputs = visitVarDecls(e.outputs);
		return new Function(e.location, e.id, inputs, outputs);
	}

	@Override
	public Node visit(Node e) {
		List<VarDecl> inputs = visitVarDecls(e.inputs);
		List<VarDecl> outputs = visitVarDecls(e.outputs);
		List<VarDecl> locals = visitVarDecls(e.locals);
		List<Equation> equations = visitEquations(e.equations);
		List<Expr> assertions = visitAssertions(e.assertions);
		List<String> properties = visitProperties(e.properties);
		List<String> ivc = visitIvc(e.ivc);
		List<String> realizabilityInputs = visitRealizabilityInputs(e.realizabilityInputs);
		Contract contract = visit(e.contract);
		return new Node(e.location, e.id, inputs, outputs, locals, equations, properties, assertions,
				realizabilityInputs, contract, ivc);
	}

	protected List<VarDecl> visitVarDecls(List<VarDecl> es) {
		return map(this::visit, es);
	}

	protected List<Equation> visitEquations(List<Equation> es) {
		return map(this::visit, es);
	}

	protected List<Expr> visitAssertions(List<Expr> es) {
		return visitExprs(es);
	}

	protected List<String> visitProperties(List<String> es) {
		return map(this::visitProperty, es);
	}

	protected String visitProperty(String e) {
		return e;
	}

	protected List<String> visitIvc(List<String> es) {
		return map(this::visitIvc, es);
	}

	protected String visitIvc(String e) {
		return e;
	}

	protected List<String> visitRealizabilityInputs(List<String> es) {
		if (es == null) {
			return null;
		}
		return map(this::visitRealizabilityInput, es);
	}

	protected String visitRealizabilityInput(String e) {
		return e;
	}

	@Override
	public Program visit(Program e) {
		List<TypeDef> types = visitTypeDefs(e.types);
		List<Constant> constants = visitConstants(e.constants);
		List<Function> functions = visitFunctions(e.functions);
		List<Node> nodes = visitNodes(e.nodes);
		return new Program(e.location, types, constants, functions, nodes, e.main);
	}

	protected List<TypeDef> visitTypeDefs(List<TypeDef> es) {
		return map(this::visit, es);
	}

	protected List<Constant> visitConstants(List<Constant> es) {
		return map(this::visit, es);
	}

	protected List<Node> visitNodes(List<Node> es) {
		return map(this::visit, es);
	}

	protected List<Function> visitFunctions(List<Function> es) {
		return map(this::visit, es);
	}

	@Override
	public TypeDef visit(TypeDef e) {
		return e;
	}

	@Override
	public VarDecl visit(VarDecl e) {
		return e;
	}

	@Override
	public Contract visit(Contract contract) {
		if (contract == null) {
			return null;
		}
		return new Contract(visitExprs(contract.requires), visitExprs(contract.ensures));
	}
}
