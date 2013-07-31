package jkind.lustre;


public class IdExpr extends Expr {
	final public String id;
	
	public IdExpr(Location location, String id) {
		super(location);
		this.id = id;
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
