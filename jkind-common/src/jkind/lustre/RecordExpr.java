package jkind.lustre;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class RecordExpr extends Expr {
	final public SortedMap<String, Expr> fields;

	public RecordExpr(Location loc, Map<String, Expr> fields) {
		super(loc);
		this.fields = Collections.unmodifiableSortedMap(new TreeMap<>(fields));
	}
	
	public RecordExpr(Map<String, Expr> fields) {
		this(Location.NULL, fields);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
