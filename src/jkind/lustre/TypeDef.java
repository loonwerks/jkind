package jkind.lustre;


public class TypeDef extends AST {
	final public String id;
	final public Type type;

	public TypeDef(Location location, String id, Type type) {
		super(location);
		this.id = id;
		this.type = type;
	}
}
