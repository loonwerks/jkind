package jkind.pdr;

import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public abstract class AbstractedTerm {
	public abstract Term apply(Script script, List<Term> arguments);

	public static AbstractedTerm make(Term term, List<Term> variables) {
		int i = variables.indexOf(term);
		if (i >= 0) {
			return new AbstractedVariable(i);
		} else if (term instanceof ApplicationTerm) {
			return new AbstractedApplicationTerm((ApplicationTerm) term, variables);
		}
		
		throw new IllegalArgumentException("Unhandled: " + term.getClass().getSimpleName());
	}
}
