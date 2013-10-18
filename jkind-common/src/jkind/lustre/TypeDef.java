package jkind.lustre;

public class TypeDef extends Ast {
	final public String id;
	final public Type type;

	public TypeDef(Location location, String id, Type type) {
		super(location);
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