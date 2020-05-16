package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;

public class RecordAccessExpr extends Expr {
	public final Expr record;
	public final String field;

	public RecordAccessExpr(Location location, Expr record, String field) {
		super(location);
		Assert.isNotNull(record);
		Assert.isNotNull(field);
		this.record = record;
		this.field = field;
	}

	public RecordAccessExpr(Expr record, String field) {
		this(Location.NULL, record, field);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}