package jkind.lustre;

import jkind.lustre.visitors.TypeVisitor;

public abstract class Type {
	final public Location location;
	
	protected Type(Location location) {
		this.location = location;
	}

	public abstract <T> T accept(TypeVisitor<T> visitor);
}
