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
import jkind.util.Util;

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
		List<VarDecl> inputs = visitAllAst(e.inputs, VarDecl.class);
		List<VarDecl> outputs = visitAllAst(e.outputs, VarDecl.class);
		List<VarDecl> locals = visitAllAst(e.locals, VarDecl.class);
		List<Equation> equations = visitAllAst(e.equations, Equation.class);
		List<Expr> assertions = visitAll(e.assertions);
		return new Node(e.location, e.id, inputs, outputs, locals, equations, e.properties,
				assertions);
	}

	@Override
	public Program visit(Program e) {
		List<TypeDef> types = visitAllAst(e.types, TypeDef.class);
		List<Constant> constants = visitAllAst(e.constants, Constant.class);
		List<Node> nodes = visitAllAst(e.nodes, Node.class);
		return new Program(e.location, types, constants, nodes, e.main);
	}

	@Override
	public TypeDef visit(TypeDef e) {
		return e;
	}

	@Override
	public VarDecl visit(VarDecl e) {
		return e;
	}

	protected <T> List<T> visitAllAst(List<? extends Ast> list, Class<T> klass) {
		List<Ast> result = new ArrayList<>();
		for (Ast e : list) {
			result.add(e.accept(this));
		}
		return Util.castList(result, klass);
	}
}
