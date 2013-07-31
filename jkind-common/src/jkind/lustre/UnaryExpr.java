package jkind.lustre;

public class UnaryExpr extends Expr {
	final public UnaryOp op;
	final public Expr expr;

	public UnaryExpr(Location location, UnaryOp op, Expr expr) {
		super(location);
		this.op = op;
		this.expr = expr;
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
