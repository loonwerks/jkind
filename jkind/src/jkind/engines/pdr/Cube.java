package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class Cube {
	private final List<Term> pLiterals = new ArrayList<>();
	private Cube next;

	public void addPLiteral(Term term) {
		pLiterals.add(term);
	}

	public void removePLiteral(Term term) {
		pLiterals.remove(term);
	}

	public List<Term> getPLiterals() {
		return pLiterals;
	}

	public boolean subsumes(Cube other) {
		return other.pLiterals.containsAll(pLiterals);
	}

	public void setNext(Cube next) {
		this.next = next;
	}

	public Cube getNext() {
		return next;
	}

	public Term toTerm(Script script) {
		return Util.and(script, pLiterals.toArray(new Term[pLiterals.size()]));
	}

	@Override
	public String toString() {
		return pLiterals.toString();
	}
}
