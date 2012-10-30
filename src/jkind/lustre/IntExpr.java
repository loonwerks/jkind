package jkind.lustre;

public class IntExpr extends Expr {
	final public int value;

	public IntExpr(Location location, int value) {
		super(location);
		this.value = value;
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
