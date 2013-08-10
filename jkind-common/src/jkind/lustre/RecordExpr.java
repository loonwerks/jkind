package jkind.lustre;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class RecordExpr extends Expr {
	final public String id;
	final public SortedMap<String, Expr> fields;

	public RecordExpr(Location loc, String id, Map<String, Expr> fields) {
		super(loc);
		this.id = id;
		this.fields = Collections.unmodifiableSortedMap(new TreeMap<>(fields));
	}
	
	public RecordExpr(String id, Map<String, Expr> fields) {
		this(Location.NULL, id, fields);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
