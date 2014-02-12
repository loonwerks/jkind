package jkind.lustre;

public class NamedType extends Type {
	final public String name;

	public NamedType(Location location, String name) {
		super(location);
		this.name = name;
	}

	public NamedType(String name) {
		this(Location.NULL, name);
	}
	
	@Override
	public String toString() {
		return name;
	}

	final public static NamedType REAL = new NamedType("real");
	final public static NamedType BOOL = new NamedType("bool");
	final public static NamedType INT = new NamedType("int");

	public boolean isBuiltin() {
		return (this == REAL || this == BOOL || this == INT);
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
}
