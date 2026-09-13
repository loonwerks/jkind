package jkind.solvers.smtinterpol;

import de.uni_freiburg.informatik.ultimate.logic.AnnotatedTerm;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.ConstantTerm;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.TermVariable;

public class Subst {
	public static Term apply(Script script, Term term, Term[] variables, Term[] arguments) {
		int i = indexOf(variables, term);
		if (i >= 0) {
			return arguments[i];
		}

		if (term instanceof ApplicationTerm) {
			return apply(script, (ApplicationTerm) term, variables, arguments);
		} else if (term instanceof ConstantTerm) {
			return term;
		} else if (term instanceof TermVariable) {
			// A variable bound inside the term, by a let or a quantifier. The
			// substitution targets are matched by identity above, so reaching
			// here means this variable is not one of them and substitution
			// leaves it alone.
			return term;
		} else if (term instanceof AnnotatedTerm) {
			AnnotatedTerm annotated = (AnnotatedTerm) term;
			return script.annotate(apply(script, annotated.getSubterm(), variables, arguments),
					annotated.getAnnotations());
		}

		throw new IllegalArgumentException("Unhandled: " + term.getClass().getSimpleName());
	}

	private static int indexOf(Term[] terms, Term term) {
		for (int i = 0; i < terms.length; i++) {
			if (terms[i] == term) {
				return i;
			}
		}
		return -1;
	}

	private static Term apply(Script script, ApplicationTerm at, Term[] variables, Term[] arguments) {
		Term[] params = at.getParameters();
		Term[] terms = new Term[params.length];
		for (int i = 0; i < params.length; i++) {
			terms[i] = apply(script, params[i], variables, arguments);
		}
		return script.term(at.getFunction().getName(), terms);
	}
}
