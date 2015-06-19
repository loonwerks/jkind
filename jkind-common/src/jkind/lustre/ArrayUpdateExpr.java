package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;

public class ArrayUpdateExpr extends Expr {
	public final Expr array;
	public final Expr index;
	public final Expr value;
	
	public ArrayUpdateExpr(Location location, Expr array, Expr index, Expr value) {
		super(location);
		Assert.isNotNull(array);
		Assert.isNotNull(index);
		Assert.isNotNull(value);
		this.array = array;
		this.index = index;
		this.value = value;
	}
	
	public ArrayUpdateExpr(Expr array, Expr index, Expr value) {
		this(Location.NULL, array, index, value);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}