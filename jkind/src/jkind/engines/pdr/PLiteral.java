package jkind.engines.pdr;

import jkind.lustre.Expr;
import jkind.lustre.LustreUtil;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class PLiteral {
	private final boolean positive;
	private final Expr expr;

	public PLiteral(boolean positive, Expr expr) {
		this.positive = positive;
		this.expr = expr;
	}

	public boolean isPositive() {
		return positive;
	}

	public Expr getExpr() {
		return expr;
	}

	public Expr toExpr() {
		return positive ? expr : LustreUtil.not(expr);
	}

	public Term toTerm(Script script) {
		Term pAtom = expr.accept(new Lustre2Term(script));
		return positive ? pAtom : Util.not(script, pAtom);
	}

	public PLiteral negate() {
		return new PLiteral(!positive, expr);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
		result = prime * result + (positive ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PLiteral)) {
			return false;
		}
		PLiteral other = (PLiteral) obj;
		if (expr == null) {
			if (other.expr != null) {
				return false;
			}
		} else if (!expr.equals(other.expr)) {
			return false;
		}
		if (positive != other.positive) {
			return false;
		}
		return true;
	}
}
