package jkind.lustre;


public class Equation extends AST {
	final public String id;
	final public Expr expr;

	public Equation(String id, Expr expr) {
		this.id = id;
		this.expr = expr;
	}
}
