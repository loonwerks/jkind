package jkind.lustre;

import java.math.BigInteger;

public class IntExpr extends Expr {
	final public BigInteger value;

	public IntExpr(Location location, BigInteger value) {
		super(location);
		this.value = value;
	}
	
	public IntExpr(BigInteger value) {
		this(Location.NULL, value);
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
