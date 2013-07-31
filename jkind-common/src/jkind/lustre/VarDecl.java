package jkind.lustre;


public class VarDecl extends Ast {
	final public String id;
	final public Type type;

	public VarDecl(Location location, String id, Type type) {
		super(location);
		this.id = id;
		this.type = type;
	}
	
	public VarDecl(String id, Type type) {
		this(Location.NULL, id, type);
	}
	
	@Override
	public <T> T accept(AstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}