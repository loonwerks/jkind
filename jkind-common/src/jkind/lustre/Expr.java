package jkind.lustre;


public abstract class Expr extends Ast {
	public Expr(Location location) {
		super(location);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T, S extends T> S accept(AstVisitor<T, S> visitor) {
		return accept((ExprVisitor<S>) visitor);
	}
	
	public abstract <T> T accept(ExprVisitor<T> visitor);
}