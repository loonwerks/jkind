package jkind.lustre;

import jkind.lustre.visitors.ExprVisitor;

public class RecordAccessExpr extends Expr {
	final public Expr record;
	final public String field;
	
	public RecordAccessExpr(Location location, Expr record, String field) {
		super(location);
		this.record = record;
		this.field = field;
	}
	
	public RecordAccessExpr(Expr expr, String field) {
		this(Location.NULL, expr, field);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}