package jkind.lustre.visitors;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Ast;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
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
	public Node visit(Node e) {
		List<VarDecl> inputs = visitVarDecls(e.inputs);
		List<VarDecl> outputs = visitVarDecls(e.outputs);
		List<VarDecl> locals = visitVarDecls(e.locals);
		List<Equation> equations = visitEquations(e.equations);
		List<Expr> assertions = visitAssertions(e.assertions);
		return new Node(e.location, e.id, inputs, outputs, locals, equations, e.properties,
				assertions);
	}

	protected List<VarDecl> visitVarDecls(List<VarDecl> es) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl e : es) {
			result.add(visit(e));
		}
		return result;
	}

	protected List<Equation> visitEquations(List<Equation> es) {
		List<Equation> result = new ArrayList<>();
		for (Equation e : es) {
			result.add(visit(e));
		}
		return result;
	}
	
	protected List<Expr> visitAssertions(List<Expr> es) {
		return visitExprs(es);
	}

	@Override
	public Program visit(Program e) {
		List<TypeDef> types = visitTypeDefs(e.types);
		List<Constant> constants = visitConstants(e.constants);
		List<Node> nodes = visitNodes(e.nodes);
		return new Program(e.location, types, constants, nodes, e.main);
	}
	
	protected List<TypeDef> visitTypeDefs(List<TypeDef> es) {
		List<TypeDef> result = new ArrayList<>();
		for (TypeDef e : es) {
			result.add(visit(e));
		}
		return result;
	}

	protected List<Constant> visitConstants(List<Constant> es) {
		List<Constant> result = new ArrayList<>();
		for (Constant e : es) {
			result.add(visit(e));
		}
		return result;
	}

	protected List<Node> visitNodes(List<Node> es) {
		List<Node> result = new ArrayList<>();
		for (Node e : es) {
			result.add(visit(e));
		}
		return result;
	}
	
	@Override
	public TypeDef visit(TypeDef e) {
		return e;
	}

	@Override
	public VarDecl visit(VarDecl e) {
		return e;
	}
}
