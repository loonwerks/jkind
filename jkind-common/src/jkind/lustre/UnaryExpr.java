package jkind.lustre;

import jkind.lustre.visitors.ExprVisitor;

public class UnaryExpr extends Expr {
	final public UnaryOp op;
	final public Expr expr;

	public UnaryExpr(Location location, UnaryOp op, Expr expr) {
		super(location);
		this.op = op;
		this.expr = expr;
	}
	
	public UnaryExpr(UnaryOp op, Expr expr) {
		this(Location.NULL, op, expr);
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
