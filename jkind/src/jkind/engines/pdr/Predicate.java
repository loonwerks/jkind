package jkind.engines.pdr;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.sexp.Sexp;
import jkind.solvers.smtinterpol.Term2Expr;
import jkind.translation.Lustre2Sexp;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Predicate {
	private final Expr expr;
	private final String string;

	public Predicate(Expr expr) {
		this.expr = expr;
		this.string = expr.toString();
	}

	public Predicate(Term term) {
		this(Term2Expr.expr(term));
	}

	public Predicate(String id) {
		this(new IdExpr(id));
	}

	public Expr getExpr() {
		return expr;
	}

	public Term toTerm(Script script) {
		return expr.accept(new Lustre2Term(script));
	}

	public Sexp toSexp(int index) {
		return expr.accept(new Lustre2Sexp(index));
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public int hashCode() {
		return string.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Predicate)) {
			return false;
		}
		Predicate other = (Predicate) obj;
		return string.equals(other.string);
	}
}
