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
