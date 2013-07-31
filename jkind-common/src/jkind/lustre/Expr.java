package jkind.lustre;


public abstract class Expr extends Ast {
	public Expr(Location location) {
		super(location);
	}

	@Override
	public <T> T accept(AstVisitor<T> visitor) {
		return accept((ExprVisitor<T>) visitor);
	}
	
	public abstract <T> T accept(ExprVisitor<T> visitor);
}