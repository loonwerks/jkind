package jkind.lustre;

import java.math.BigDecimal;

public class RealExpr extends Expr {
	final public BigDecimal value;

	public RealExpr(BigDecimal value) {
		this.value = value;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
