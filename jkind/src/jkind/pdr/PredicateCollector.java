package jkind.pdr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class PredicateCollector {
	private final List<Term> variables;
	private final Set<Predicate> predicates = new HashSet<>();

	public PredicateCollector(List<Term> variables) {
		this.variables = variables;
	}

	public static Set<Predicate> collect(Term term, List<Term> variables) {
		PredicateCollector collector = new PredicateCollector(variables);
		collector.collect(term);
		return collector.predicates;
	}

	private void collect(Term term) {
		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			collect(at);
		} else {
			throw new IllegalArgumentException("Unhandled: " + term.getClass().getSimpleName());
		}
	}

	private void collect(ApplicationTerm at) {
		if (at.getParameters().length == 0) {
			String name = at.getFunction().getName();
			if (name.equals("true") || name.equals("false")) {
				return;
			}
		} else if (booleanParamaters(at)) {
			for (Term sub : at.getParameters()) {
				collect(sub);
			}
			return;
		}

		predicates.add(new Predicate(at, variables));
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
