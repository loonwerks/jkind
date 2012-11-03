package jkind.lustre;

public class Type {
	final public String name;
	
	public Type(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	final public static Type REAL = new Type("real");
	final public static Type BOOL = new Type("bool");
	final public static Type INT = new Type("int");

	public boolean isBase() {
		return (this == REAL || this == BOOL || this == INT);
	}
	
	public boolean isBuiltin() {
		return isBase() || this instanceof SubrangeIntType;
	}
}
