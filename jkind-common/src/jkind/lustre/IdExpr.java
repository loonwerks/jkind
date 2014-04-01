package jkind.lustre;

import jkind.lustre.visitors.ExprVisitor;


public class IdExpr extends Expr {
	final public String id;
	
	public IdExpr(Location location, String id) {
		super(location);
		this.id = id;
	}
	
	public IdExpr(String id) {
		this(Location.NULL, id);
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
