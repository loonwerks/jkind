package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;

public class IdExpr extends Expr {
	public final String id;

	public IdExpr(Location location, String id) {
		super(location);
		Assert.isNotNull(id);
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
