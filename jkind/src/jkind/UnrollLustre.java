package jkind;

import static jkind.lustre.LustreUtil.TRUE;
import static jkind.lustre.LustreUtil.and;
import static jkind.lustre.LustreUtil.eq;
import static jkind.lustre.LustreUtil.pre;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class UnrollLustre extends AstMapVisitor {
	public static Node node(Node node, int depth) {
		return new UnrollLustre(node.properties, depth).visit(node);
	}

	private final List<String> properties;
	private final int depth;
	private int step = 0;

	public UnrollLustre(List<String> properties, int depth) {
		this.properties = properties;
		this.depth = depth;
	}

	private String id(String id) {
		return id + "~" + step;
	}

	@Override
	protected List<VarDecl> visitVarDecls(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			for (step = 0; step < depth; step++) {
				result.add(new VarDecl(id(vd.id), vd.type));
			}
			if (properties.contains(vd.id)) {
				result.add(new VarDecl(vd.id + "~all", vd.type));
			}
		}
		return result;
	}

	@Override
	protected List<Equation> visitEquations(List<Equation> equations) {
		List<Equation> result = new ArrayList<>();
		for (Equation eq : equations) {
			IdExpr id = eq.lhs.get(0);
			for (step = 0; step < depth; step++) {
				result.add(eq(visit(id), eq.expr.accept(this)));
			}
			if (properties.contains(id.id)) {
				Expr all = TRUE; 
				for (step = 0; step < depth; step++) {
					all = and(all, visit(id));
				}
				result.add(eq(new IdExpr(id.id + "~all"), all));
			}
		}
		return result;
	}

	@Override
	protected List<Expr> visitAssertions(List<Expr> assertions) {
		List<Expr> result = new ArrayList<>();
		for (Expr expr : assertions) {
			for (step = 0; step < depth; step++) {
				result.add(expr.accept(this));
			}
		}
		return result;
	}

	@Override
	public Expr visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW && step > 0) {
			return e.right.accept(this);
		} else {
			return super.visit(e);
		}
	}

	@Override
	public IdExpr visit(IdExpr e) {
		if (step >= 0) {
			return new IdExpr(e.id + "~" + step);
		} else {
			return new IdExpr(e.id + "~" + (depth - 1));
		}
	}

	@Override
	public Expr visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			step--;
			Expr expr = e.expr.accept(this);
			if (step == -1) {
				expr = pre(expr);
			}
			step++;
			return expr;
		} else {
			return super.visit(e);
		}
	}

	@Override
	protected String visitProperty(String e) {
		return e + "~all";
	}
}
