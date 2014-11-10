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

	public Clause negate() {
		Clause negated = new Clause();
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
		return Util.and(solver, terms);
	}

	public Cube prime(Script solver) {
		Prime prime = new Prime(solver);
		Cube c = new Cube();
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
		return positive + " and ~" + negative;
	}
}
