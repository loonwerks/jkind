package jkind.engines.pdr;

import jkind.lustre.Expr;
import jkind.lustre.LustreUtil;
import jkind.sexp.Sexp;
import jkind.util.SexpUtil;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class PLiteral {
	private final boolean positive;
	private final Predicate predicate;

	public PLiteral(boolean positive, Predicate predicate) {
		this.positive = positive;
		this.predicate = predicate;
	}

	public boolean isPositive() {
		return positive;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public Expr toExpr() {
		Expr expr = predicate.getExpr();
		return positive ? expr : LustreUtil.not(expr);
	}

	public Term toTerm(Script script) {
		Term pAtom = predicate.toTerm(script);
		return positive ? pAtom : Util.not(script, pAtom);
	}

	public Sexp toSexp(int index) {
		Sexp pAtom = predicate.toSexp(index);
		return positive ? pAtom : SexpUtil.not(pAtom);
	}

	public PLiteral negate() {
		return new PLiteral(!positive, predicate);
	}
	
	@Override
	public String toString() {
		return toExpr().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (positive ? 1231 : 1237);
		result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
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
		if (positive != other.positive) {
			return false;
		}
		if (predicate == null) {
			if (other.predicate != null) {
				return false;
			}
		} else if (!predicate.equals(other.predicate)) {
			return false;
		}
		return true;
	}
}
