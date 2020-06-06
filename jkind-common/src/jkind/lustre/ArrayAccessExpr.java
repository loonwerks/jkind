package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;

public class ArrayAccessExpr extends Expr {
	public final Expr array;
	public final Expr index;

	public ArrayAccessExpr(Location location, Expr array, Expr index) {
		super(location);
		Assert.isNotNull(array);
		Assert.isNotNull(index);
		this.array = array;
		this.index = index;
	}

	public ArrayAccessExpr(Expr array, Expr index) {
		this(Location.NULL, array, index);
	}

	public ArrayAccessExpr(Expr array, int index) {
		this(array, new IntExpr(index));
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}