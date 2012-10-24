package jkind.lustre;

public class BoolExpr extends Expr {
	final public boolean value;

	public BoolExpr(boolean value) {
		this.value = value;
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
