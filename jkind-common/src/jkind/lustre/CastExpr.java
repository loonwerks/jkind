package jkind.lustre;

import jkind.lustre.visitors.ExprVisitor;

public class CastExpr extends Expr {
	final public Type type;
	final public Expr expr;

	public CastExpr(Location location, Type type, Expr expr) {
		super(location);
		this.type = type;
		this.expr = expr;
	}

	public CastExpr(Type type, Expr expr) {
		this(Location.NULL, type, expr);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
