package jkind.lustre;

import java.util.ArrayList;
import java.util.List;

import jkind.util.Util;

public class AstMapVisitor extends ExprMapVisitor implements AstVisitor<Ast, Expr> {
	@Override
	public Ast visit(Constant e) {
		return new Constant(e.location, e.id, e.type, e.expr.accept(this));
	}

	@Override
	public Ast visit(Equation e) {
		List<IdExpr> lhs = Util.castList(visitAll(e.lhs), IdExpr.class);
		return new Equation(e.location, lhs, e.expr.accept(this));
	}

	@Override
	public Ast visit(Node e) {
		List<VarDecl> inputs = visitAllAst(e.inputs, VarDecl.class);
		List<VarDecl> outputs = visitAllAst(e.outputs, VarDecl.class);
		List<VarDecl> locals = visitAllAst(e.locals, VarDecl.class);
		List<Equation> equations = visitAllAst(e.equations, Equation.class);
		List<Expr> assertions = visitAll(e.assertions);
		return new Node(e.location, e.id, inputs, outputs, locals, equations, e.properties,
				assertions);
	}

	@Override
	public Ast visit(Program e) {
		List<TypeDef> types = visitAllAst(e.types, TypeDef.class);
		List<Constant> constants = visitAllAst(e.constants, Constant.class);
		List<Node> nodes = visitAllAst(e.nodes, Node.class);
		return new Program(e.location, types, constants, nodes, e.main);
	}

	@Override
	public Ast visit(TypeDef e) {
		return e;
	}

	@Override
	public Ast visit(VarDecl e) {
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
