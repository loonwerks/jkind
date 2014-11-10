package jkind.pdr;

import java.util.ArrayList;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class Frame {
	private final Term term;
	private final List<Clause> clauses = new ArrayList<>();

	public Frame(Term term) {
		this.term = term;
	}

	public Frame() {
		this.term = null;
	}

	public Term getTerm(Script solver) {
		if (term != null && !clauses.isEmpty()) {
			throw new IllegalArgumentException("Clauses in initial frame");
		}
		
		if (term != null) {
			return term;
		}
		
		Term[] terms = new Term[clauses.size()];
		for (int i = 0; i < clauses.size(); i++) {
			terms[i] = clauses.get(i).toTerm(solver);
		}
		return Util.and(solver, terms);
	}

	public void add(Clause c) {
		clauses.add(c);
	}

	public List<Clause> getClauses() {
		return clauses;
	}
}
