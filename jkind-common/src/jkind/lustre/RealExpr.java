package jkind.lustre;

import java.math.BigDecimal;

import jkind.lustre.visitors.ExprVisitor;

public class RealExpr extends Expr {
	final public BigDecimal value;

	public RealExpr(Location location, BigDecimal value) {
		super(location);
		this.value = value;
	}
	
	public RealExpr(BigDecimal value) {
		this(Location.NULL, value);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
