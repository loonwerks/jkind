package jkind.pdr;

import java.util.ArrayList;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class Cube {
	private final List<Term> positive = new ArrayList<>();
	private final List<Term> negative = new ArrayList<>();
	private Cube next;

	public void addPositive(Term t) {
		positive.add(t);
	}

	public void addNegative(Term t) {
		negative.add(t);
	}
	
	public void setNext(Cube next) {
		this.next = next;
	}
	
	public Cube getNext() {
		return next;
	}

	public Term toTerm(Script solver) {
		Term[] terms = new Term[positive.size() + negative.size()];
		for (int i = 0; i < positive.size(); i++) {
			terms[i] = positive.get(i);
		}
		for (int i = 0; i < negative.size(); i++) {
			terms[i + positive.size()] = Util.not(solver, negative.get(i));
		}
		return Util.and(solver, terms);
	}

	@Override
	public String toString() {
		return positive + " and ~" + negative;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((negative == null) ? 0 : negative.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((positive == null) ? 0 : positive.hashCode());
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
		if (!(obj instanceof Cube)) {
			return false;
		}
		Cube other = (Cube) obj;
		if (negative == null) {
			if (other.negative != null) {
				return false;
			}
		} else if (!negative.equals(other.negative)) {
			return false;
		}
		if (next == null) {
			if (other.next != null) {
				return false;
			}
		} else if (!next.equals(other.next)) {
			return false;
		}
		if (positive == null) {
			if (other.positive != null) {
				return false;
			}
		} else if (!positive.equals(other.positive)) {
			return false;
		}
		return true;
	}
}
