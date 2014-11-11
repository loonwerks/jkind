package jkind.pdr;

import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Subst {
	public static Term apply(Script script, Term term, List<Term> variables, List<Term> arguments) {
		int i = variables.indexOf(term);
		if (i >= 0) {
			return arguments.get(i);
		}

		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			return apply(script, at, variables, arguments);
		}

		throw new IllegalArgumentException("Unhandled: " + term.getClass().getSimpleName());
	}

	public static Term apply(Script script, ApplicationTerm term, List<Term> variables,
			List<Term> arguments) {
		Term[] parameters = term.getParameters();
		Term[] terms = new Term[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			terms[i] = apply(script, parameters[i], variables, arguments);
		}
		return script.term(term.getFunction().getName(), term);
	}
}
