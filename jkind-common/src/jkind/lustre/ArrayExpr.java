package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class ArrayExpr extends Expr {
	public final List<Expr> elements;

	public ArrayExpr(Location loc, List<Expr> elements) {
		super(loc);
		this.elements = Util.safeList(elements);
	}
	
	public ArrayExpr(List<Expr> elements) {
		this(Location.NULL, elements);
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
