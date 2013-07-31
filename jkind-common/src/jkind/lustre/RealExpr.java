package jkind.lustre;

import java.math.BigDecimal;

public class RealExpr extends Expr {
	final public BigDecimal value;

	public RealExpr(Location location, BigDecimal value) {
		super(location);
		this.value = value;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
