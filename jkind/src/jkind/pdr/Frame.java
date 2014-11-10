package jkind.pdr;

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
		if (term != null && !cubes.isEmpty()) {
			throw new IllegalArgumentException("Cannot block cube in initial frame");
		}
		
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
		cubes.add(c);
	}

	public Set<Cube> getCubes() {
		return cubes;
	}
}
