package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.AstVisitor;

public class TypeDef extends Ast {
	public final String id;
	public final Type type;

	public TypeDef(Location location, String id, Type type) {
		super(location);
		Assert.isNotNull(id);
		Assert.isNotNull(type);
		this.id = id;
		this.type = type;
	}

	public TypeDef(String id, Type type) {
		this(Location.NULL, id, type);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}