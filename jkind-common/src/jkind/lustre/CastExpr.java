package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;

public class CastExpr extends Expr {
	public final Type type;
	public final Expr expr;

	public CastExpr(Location location, Type type, Expr expr) {
		super(location);
		Assert.isNotNull(type);
		Assert.isNotNull(expr);
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
