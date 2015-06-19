package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;

public class RecordUpdateExpr extends Expr {
	public final Expr record;
	public final String field;
	public final Expr value;
	
	public RecordUpdateExpr(Location location, Expr record, String field, Expr value) {
		super(location);
		Assert.isNotNull(record);
		Assert.isNotNull(field);
		Assert.isNotNull(value);
		this.record = record;
		this.field = field;
		this.value = value;
	}
	
	public RecordUpdateExpr(Expr record, String field, Expr value) {
		this(Location.NULL, record, field, value);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}