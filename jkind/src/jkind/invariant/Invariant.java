package jkind.invariant;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.sexp.Sexp;
import jkind.translation.Lustre2Sexp;

public class Invariant {
	private final Expr expr;

	public Invariant(Expr expr) {
		this.expr = expr;
	}

	public Invariant(String prop) {
		this(new IdExpr(prop));
	}

	@Override
	public String toString() {
		return expr.toString();
	}

	public Sexp instantiate(int k) {
		return expr.accept(new Lustre2Sexp(k));
	}
}
