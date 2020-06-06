package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;

public class IfThenElseExpr extends Expr {
	public final Expr cond;
	public final Expr thenExpr;
	public final Expr elseExpr;

	public IfThenElseExpr(Location location, Expr cond, Expr thenExpr, Expr elseExpr) {
		super(location);
		Assert.isNotNull(cond);
		Assert.isNotNull(thenExpr);
		Assert.isNotNull(elseExpr);
		this.cond = cond;
		this.thenExpr = thenExpr;
		this.elseExpr = elseExpr;
	}

	public IfThenElseExpr(Expr cond, Expr thenExpr, Expr elseExpr) {
		this(Location.NULL, cond, thenExpr, elseExpr);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
