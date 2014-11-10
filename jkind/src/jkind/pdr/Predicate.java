package jkind.pdr;

import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Predicate {
	private final Script script;
	private final List<Term> variables;
	private final Term body;

	public Predicate(Script script, List<Term> variables, Term body) {
		this.script = script;
		this.variables = variables;
		this.body = body;
	}

	public Term apply(List<Term> arguments) {
		return apply(body, arguments);
	}

	private Term apply(Term term, List<Term> arguments) {
		int index = variables.indexOf(term);
		if (index >= 0) {
			return arguments.get(index);
		}

		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			Term[] params = new Term[at.getParameters().length];
			for (int i = 0; i < params.length; i++) {
				params[i] = apply(at.getParameters()[i], arguments);
			}
			return script.term(at.getFunction().getName(), params);
		}
		
		return term;
	}
}
