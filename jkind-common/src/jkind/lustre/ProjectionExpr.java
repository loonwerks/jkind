package jkind.lustre;

public class ProjectionExpr extends Expr {
	final public Expr expr;
	final public String field;
	
	public ProjectionExpr(Location location, Expr expr, String field) {
		super(location);
		this.expr = expr;
		this.field = field;
	}
	
	public ProjectionExpr(Expr expr, String field) {
		this(Location.NULL, expr, field);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}