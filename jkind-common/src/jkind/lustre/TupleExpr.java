package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class TupleExpr extends Expr {
	public final List<Expr> elements;

	public TupleExpr(Location loc, List<? extends Expr> elements) {
		super(loc);
		if (elements != null && elements.size() == 1) {
			throw new IllegalArgumentException("Cannot construct singleton tuple");
		}
		this.elements = Util.safeList(elements);
	}
	
	public TupleExpr(List<? extends Expr> elements) {
		this(Location.NULL, elements);
	}

	public static Expr compress(List<? extends Expr> exprs) {
		if (exprs.size() == 1) {
			return exprs.get(0);
		}
		return new TupleExpr(exprs);
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
