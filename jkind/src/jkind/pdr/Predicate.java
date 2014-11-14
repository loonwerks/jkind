package jkind.pdr;

import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Predicate {
	private final Term term;

	public Predicate(Term term) {
		assert term != null;
		this.term = term;
	}

	public Term getTerm() {
		return term;
	}

	@Override
	public String toString() {
		return term.toString();
	}

	@Override
	public int hashCode() {
		return term.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Predicate) {
			Predicate other = (Predicate) obj;
			return term.equals(other.term);
		}
		return false;
	}
}
