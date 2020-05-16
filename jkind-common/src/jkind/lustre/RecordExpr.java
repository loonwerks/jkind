package jkind.lustre;

import java.util.Map;
import java.util.SortedMap;

import jkind.Assert;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class RecordExpr extends Expr {
	public final String id;
	public final SortedMap<String, Expr> fields;

	public RecordExpr(Location loc, String id, Map<String, Expr> fields) {
		super(loc);
		Assert.isNotNull(id);
		Assert.isNotNull(fields);
		Assert.isTrue(fields.size() > 0);
		this.id = id;
		this.fields = Util.safeStringSortedMap(fields);
	}

	public RecordExpr(String id, Map<String, Expr> fields) {
		this(Location.NULL, id, fields);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
