package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.TypeVisitor;

public class NamedType extends Type {
	public final String name;

	public NamedType(Location location, String name) {
		super(location);
		Assert.isNotNull(name);
		Assert.isFalse(name.equals(BOOL.toString()));
		Assert.isFalse(name.equals(INT.toString()));
		Assert.isFalse(name.equals(REAL.toString()));
		this.name = name;
	}

	public NamedType(String name) {
		this(Location.NULL, name);
	}
	
	/*
	 * Private constructor for built-in types
	 */
	private NamedType(String name, @SuppressWarnings("unused") Object unused) {
		super(Location.NULL);
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static final NamedType BOOL = new NamedType("bool", null);
	public static final NamedType INT = new NamedType("int", null);
	public static final NamedType REAL = new NamedType("real", null);

	public boolean isBuiltin() {
		return this == REAL || this == BOOL || this == INT;
	}
	
	public static NamedType get(String name) {
		switch (name) {
		case "int":
			return NamedType.INT;
		case "real":
			return NamedType.REAL;
		case "bool":
			return NamedType.BOOL;
		default:
			return new NamedType(name);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NamedType)) {
			return false;
		}
		NamedType other = (NamedType) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
