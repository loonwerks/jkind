package jkind.lustre;

public abstract class Expr extends Ast {
	public Expr(Location location) {
		super(location);
	}

	public abstract <T> T accept(ExprVisitor<T> visitor);
}
