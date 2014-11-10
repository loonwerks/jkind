package jkind.pdr;

import java.util.ArrayList;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class Clause {
	private final List<Term> positive = new ArrayList<>();
	private final List<Term> negative = new ArrayList<>();

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
		for (int i = 0; i < positive.size(); i++) {
			terms[i] = positive.get(i);
		}
		for (int i = 0; i < negative.size(); i++) {
			terms[i + positive.size()] = Util.not(solver, negative.get(i));
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
			c.addPositive(prime.prime(neg));
		}
		return c;
	}
	
	@Override
	public String toString() {
		return positive + " or ~" + negative;
	}
}
