package jkind.lustre;

public class IterVisitor implements ExprVisitor<Void> {
	@Override
	public Void visit(BinaryExpr e) {
		e.left.accept(this);
		e.right.accept(this);
		return null;
	}

	@Override
	public Void visit(BoolExpr e) {
		return null;
	}

	@Override
	public Void visit(IdExpr e) {
		return null;
	}

	@Override
	public Void visit(IfThenElseExpr e) {
		e.cond.accept(this);
		e.thenExpr.accept(this);
		e.elseExpr.accept(this);
		return null;
	}

	@Override
	public Void visit(IntExpr e) {
		return null;
	}

	@Override
	public Void visit(NodeCallExpr e) {
		for (Expr arg : e.args) {
			arg.accept(this);
		}
		return null;
	}
	
	@Override
	public Void visit(RealExpr e) {
		return null;
	}

	@Override
	public Void visit(UnaryExpr e) {
		e.expr.accept(this);
		return null;
	}
}
