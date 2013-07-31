package jkind.lustre;


public class IfThenElseExpr extends Expr {
	final public Expr cond;
	final public Expr thenExpr;
	final public Expr elseExpr;
	
	public IfThenElseExpr(Location location, Expr cond, Expr thenExpr, Expr elseExpr) {
		super(location);
		this.cond = cond;
		this.thenExpr = thenExpr;
		this.elseExpr = elseExpr;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
