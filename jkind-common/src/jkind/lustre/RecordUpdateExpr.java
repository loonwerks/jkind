package jkind.lustre;

import jkind.lustre.visitors.ExprVisitor;

// TODO : Ask Andrew
public class RecordUpdateExpr extends Expr {
	final public Expr record;
	final public String field;
	final public Expr value;
	
	public RecordUpdateExpr(Location location, Expr record, String field, Expr value) {
		super(location);
		this.record = record;
		this.field = field;
		this.value = value;
	}
	
	public RecordUpdateExpr(Expr expr, String field, Expr value) {
		this(Location.NULL, expr, field, value);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}