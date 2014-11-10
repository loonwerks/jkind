package jkind.pdr;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Frame {
	private final Term term;

	public Frame(Term term) {
		this.term = term;
	}

	public Frame() {
		this.term = null;
	}

	public Term getTerm(Script solver) {
		if (term == null) {
			return solver.term("true");
		} else {
			return term;
		}
	}
}
