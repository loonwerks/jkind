package jkind.pdr;

import java.util.HashSet;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class Frame {
	private final Term term;
	private final Set<Clause> clauses = new HashSet<>();

	public Frame(Term term) {
		this.term = term;
	}

	public Frame() {
		this.term = null;
	}

	public Term toTerm(Script solver) {
		if (term != null && !clauses.isEmpty()) {
			throw new IllegalArgumentException("Clauses in initial frame");
		}
		
		if (term != null) {
			return term;
		}
		
		Term[] terms = new Term[clauses.size()];
		int i = 0;
		for (Clause c : clauses) {
			terms[i] = c.toTerm(solver);
			i++;
		}
		return Util.and(solver, terms);
	}

	public void add(Clause c) {
		clauses.add(c);
	}

	public Set<Clause> getClauses() {
		return clauses;
	}
}
