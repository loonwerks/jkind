package jkind.lustre;

import java.util.Collections;
import java.util.List;

import jkind.lustre.visitors.ExprVisitor;

public class ArrayExpr extends Expr {
	final public List<Expr> elements;

	public ArrayExpr(Location loc, List<Expr> elements) {
		super(loc);
		this.elements = Collections.unmodifiableList(elements);
	}
	
	public ArrayExpr(List<Expr> elements) {
		this(Location.NULL, elements);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
