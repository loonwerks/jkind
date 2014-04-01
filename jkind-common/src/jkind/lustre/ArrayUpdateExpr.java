package jkind.lustre;

import jkind.lustre.visitors.ExprVisitor;

public class ArrayUpdateExpr extends Expr {
	final public Expr array;
	final public Expr index;
	final public Expr value;
	
	public ArrayUpdateExpr(Location location, Expr array, Expr index, Expr value) {
		super(location);
		this.array = array;
		this.index = index;
		this.value = value;
	}
	
	public ArrayUpdateExpr(Expr expr, Expr index, Expr value) {
		this(Location.NULL, expr, index, value);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}