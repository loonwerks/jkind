package jkind.lustre;

public class VarDecl extends AST {
	final public String id;
	final public Type type;

	public VarDecl(String id, Type type) {
		this.id = id;
		this.type = type;
	}
}
