package jkind.lustre;


public class Equation extends AST {
	final public String id;
	final public Expr expr;

	public Equation(Location location, String id, Expr expr) {
		super(location);
		this.id = id;
		this.expr = expr;
	}
}
