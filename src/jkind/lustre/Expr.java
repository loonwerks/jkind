package jkind.lustre;

public abstract class Expr extends AST {
	public abstract <T> T accept(ExprVisitor<T> visitor);
}
