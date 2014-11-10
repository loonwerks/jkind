package jkind.pdr;

import java.util.HashSet;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class Clause {
	private final Set<Term> positive = new HashSet<>();
	private final Set<Term> negative = new HashSet<>();

	public void addPositive(Term t) {
		positive.add(t);
	}

	public void addNegative(Term t) {
		negative.add(t);
	}

	public Cube negate() {
		Cube negated = new Cube();
		for (Term pos : positive) {
			negated.addNegative(pos);
		}
		for (Term neg : negative) {
			negated.addPositive(neg);
		}
		return negated;
	}

	public Term toTerm(Script solver) {
		Term[] terms = new Term[positive.size() + negative.size()];
		int i = 0;
		for (Term pos : positive) {
			terms[i++] = pos;
		}
		for (Term neg : negative) {
			terms[i++] = Util.not(solver, neg);
		}
		return Util.or(solver, terms);
	}
	
	public Clause prime(Script solver) {
		Prime prime = new Prime(solver);
		Clause c = new Clause();
		for (Term pos : positive) {
			c.addPositive(prime.prime(pos));
		}
		for (Term neg : negative) {
			c.addNegative(prime.prime(neg));
		}
		return c;
	}
	
	@Override
	public String toString() {
		return positive + " or ~" + negative;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((negative == null) ? 0 : negative.hashCode());
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
		if (!(obj instanceof Clause)) {
			return false;
		}
		Clause other = (Clause) obj;
		if (negative == null) {
			if (other.negative != null) {
				return false;
			}
		} else if (!negative.equals(other.negative)) {
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
