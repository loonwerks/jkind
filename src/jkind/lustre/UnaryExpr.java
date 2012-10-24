package jkind.lustre;

public class UnaryExpr extends Expr {
	final public UnaryOp op;
	final public Expr expr;

	public UnaryExpr(UnaryOp op, Expr expr) {
		this.op = op;
		this.expr = expr;
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
