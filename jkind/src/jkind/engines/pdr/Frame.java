package jkind.engines.pdr;

import java.util.HashSet;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;

public class Frame {
	private final Term term;
	private final Set<Cube> cubes = new HashSet<>();

	public Frame(Term term) {
		this.term = term;
	}

	public Frame() {
		this.term = null;
	}

	public Term toTerm(Script script) {
		if (term != null) {
			return term;
		}

		Term[] terms = new Term[cubes.size()];
		int i = 0;
		for (Cube c : cubes) {
			terms[i] = Util.not(script, c.toTerm(script));
			i++;
		}
		return Util.and(script, terms);
	}

	public void add(Cube c) {
		assert term == null;
		cubes.add(c);
	}

	public Set<Cube> getCubes() {
		return cubes;
	}

	public boolean isEmpty() {
		return term == null && cubes.isEmpty();
	}

	@Override
	public String toString() {
		if (term != null) {
			return term.toString();
		} else {
			return cubes.toString();
		}
	}
}
