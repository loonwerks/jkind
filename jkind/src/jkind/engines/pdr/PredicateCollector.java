package jkind.engines.pdr;

import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.Set;

import jkind.solvers.smtinterpol.Term2Expr;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class PredicateCollector {
	private final Set<Term> predicates = new HashSet<>();

	public static Set<Predicate> collect(Term term) {
		PredicateCollector collector = new PredicateCollector();
		collector.walk(term);
		int todo;
		return collector.predicates.stream().map(t -> new Predicate(Term2Expr.expr(t)))
				.collect(toSet());
	}

	private void walk(Term term) {
		if (term instanceof ApplicationTerm) {
			walk((ApplicationTerm) term);
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
		} else if (!hasVariables(at)) {
			return;
		}

		if (!at.getFunction().getName().startsWith("%")) {
			predicates.add(at);
		}
	}

	private boolean hasVariables(Term term) {
		if (term instanceof ApplicationTerm) {
			return hasVariables((ApplicationTerm) term);
		}
		return false;
	}

	private boolean hasVariables(ApplicationTerm at) {
		if (isVariable(at)) {
			return true;
		}

		for (Term sub : at.getParameters()) {
			if (hasVariables(sub)) {
				return true;
			}
		}

		return false;
	}

	private boolean isVariable(ApplicationTerm at) {
		String name = at.getFunction().getName();
		return at.getParameters().length == 0 && !name.equals("true") && !name.equals("false");
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
