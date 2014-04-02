package jkind.lustre;

import java.util.Collections;
import java.util.List;

import jkind.lustre.visitors.ExprVisitor;

public class TupleExpr extends Expr {
	final public List<Expr> elements;

	public TupleExpr(Location loc, List<? extends Expr> elements) {
		super(loc);
		this.elements = Collections.unmodifiableList(elements);
	}
	
	public TupleExpr(List<? extends Expr> elements) {
		this(Location.NULL, elements);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
