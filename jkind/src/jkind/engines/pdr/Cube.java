package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;
import jkind.sexp.Sexp;
import jkind.util.SexpUtil;

public class Cube {
	private final List<PLiteral> pLiterals = new ArrayList<>();
	private Cube next;

	public void addPLiteral(PLiteral plit) {
		pLiterals.add(plit);
	}

	public void removePLiteral(PLiteral plit) {
		pLiterals.remove(plit);
	}

	public List<PLiteral> getPLiterals() {
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

	public Sexp toSexp(int index) {
		List<Sexp> terms = new ArrayList<>();
		for (PLiteral plit : pLiterals) {
			terms.add(plit.toSexp(index));
		}
		return SexpUtil.conjoin(terms);
	}

	public Term toTerm(Script script) {
		Term[] terms = new Term[pLiterals.size()];
		for (int i = 0; i < pLiterals.size(); i++) {
			PLiteral p = pLiterals.get(i);
			terms[i] = p.toTerm(script);
		}
		return Util.and(script, terms);
	}

	@Override
	public String toString() {
		return pLiterals.toString();
	}
}
