package jkind.engines.pdr;

import java.util.HashSet;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class PredicateCollector {
	private final Set<Term> predicates = new HashSet<>();

	public static Set<Term> collect(Term term) {
		PredicateCollector collector = new PredicateCollector();
		collector.walk(term);
		return collector.predicates;
	}

	private void walk(Term term) {
		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			walk(at);
		} else {
			throw new IllegalArgumentException("Unhandled: " + term.getClass().getSimpleName());
		}
	}

	private void walk(ApplicationTerm at) {
		if (at.getParameters().length == 0) {
			String name = at.getFunction().getName();
			if (name.equals("true") || name.equals("false")) {
				return;
			}
		} else if (booleanParamaters(at)) {
			for (Term sub : at.getParameters()) {
				walk(sub);
			}
			return;
		}

		predicates.add(at);
	}

	private boolean booleanParamaters(ApplicationTerm at) {
		for (Sort sort : at.getFunction().getParameterSorts()) {
			if (!sort.getName().equals("Bool")) {
				return false;
			}
		}
		return true;
	}
}
