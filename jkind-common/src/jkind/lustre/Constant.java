package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.AstVisitor;

public class Constant extends Ast {
	public final String id;
	public final Type type;
	public final Expr expr;

	public Constant(Location location, String id, Type type, Expr expr) {
		super(location);
		Assert.isNotNull(id);
		// 'type' can be null
		Assert.isNotNull(expr);
		this.id = id;
		this.type = type;
		this.expr = expr;
	}

	public Constant(String id, Type type, Expr expr) {
		this(Location.NULL, id, type, expr);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}