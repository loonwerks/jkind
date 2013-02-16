package jkind.lustre;


public class Constant extends Ast {
	final public String id;
	final public Type type;
	final public Expr expr;

	public Constant(Location location, String id, Type type, Expr expr) {
		super(location);
		this.id = id;
		this.type = type;
		this.expr = expr;
	}
}
