package jkind.lustre;

public class BinaryExpr extends Expr {
	final public Expr left;
	final public BinaryOp op;
	final public Expr right;
	
	public BinaryExpr(Location location, Expr left, BinaryOp op, Expr right) {
		super(location);
		this.left = left;
		this.op = op;
		this.right = right;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}