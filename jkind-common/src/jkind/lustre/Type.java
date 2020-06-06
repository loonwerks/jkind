package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.TypeVisitor;

public abstract class Type {
	public final Location location;

	protected Type(Location location) {
		Assert.isNotNull(location);
		this.location = location;
	}

	public abstract <T> T accept(TypeVisitor<T> visitor);
}
