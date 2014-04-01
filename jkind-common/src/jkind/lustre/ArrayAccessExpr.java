package jkind.lustre;

import jkind.lustre.visitors.ExprVisitor;

public class ArrayAccessExpr extends Expr {
	final public Expr array;
	final public Expr index;
	
	public ArrayAccessExpr(Location location, Expr array, Expr index) {
		super(location);
		this.array = array;
		this.index = index;
	}
	
	public ArrayAccessExpr(Expr expr, Expr index) {
		this(Location.NULL, expr, index);
	}
	
	public ArrayAccessExpr(Expr expr, int index) {
		this(expr, new IntExpr(index));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}