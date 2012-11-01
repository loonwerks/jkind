package jkind.lustre;

import java.util.ArrayList;
import java.util.List;


public class MapVisitor implements ExprVisitor<Expr> {
	@Override
	public Expr visit(BinaryExpr e) {
		return new BinaryExpr(e.location, e.left.accept(this), e.op, e.right.accept(this));
	}

	@Override
	public Expr visit(BoolExpr e) {
		return e;
	}

	@Override
	public Expr visit(IdExpr e) {
		return e;
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		return new IfThenElseExpr(e.location, e.cond.accept(this), e.thenExpr.accept(this),
				e.elseExpr.accept(this));
	}

	@Override
	public Expr visit(IntExpr e) {
		return e;
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		List<Expr> newArgs = new ArrayList<Expr>();
		for (Expr arg : e.args) {
			newArgs.add(arg.accept(this));
		}
		return new NodeCallExpr(e.location, e.node, newArgs);
	}
	
	@Override
	public Expr visit(RealExpr e) {
		return e;
	}

	@Override
	public Expr visit(UnaryExpr e) {
		return new UnaryExpr(e.location, e.op, e.expr.accept(this));
	}
}
