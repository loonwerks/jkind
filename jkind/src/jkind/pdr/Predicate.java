package jkind.pdr;

import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Predicate {
	private final Term term;

	public Predicate(Term term) {
		this.term = term;
	}

	public Term getTerm() {
		return term;
	}

	@Override
	public String toString() {
		return term.toString();
	}

	// TODO: Equality on terms?

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		if (!(obj instanceof Predicate)) {
			return false;
		}
		Predicate other = (Predicate) obj;
		if (term == null) {
			if (other.term != null) {
				return false;
			}
		} else if (!term.equals(other.term)) {
			return false;
		}
		return true;
	}
}
