package jkind.solvers.smtinterpol;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class NameAppender {
	private final Script script;
	private final String suffix;

	public NameAppender(Script script, String suffix) {
		this.script = script;
		this.suffix = suffix;
	}

	public Term append(Term t) {
		if (t instanceof ApplicationTerm) {
			return append((ApplicationTerm) t);
		}

		throw new IllegalArgumentException("Unknown term: " + t.getClass().getSimpleName());
	}

	private Term append(ApplicationTerm at) {
		String name = at.getFunction().getName();
		if (at.getParameters().length == 0) {
			if (name.equals("true") || name.equals("false")) {
				return at;
			} else {
				return script.term(name + suffix);
			}
		} else {
			return script.term(name, append(at.getParameters()));
		}
	}

	private Term[] append(Term[] terms) {
		Term[] result = new Term[terms.length];
		for (int i = 0; i < terms.length; i++) {
			result[i] = append(terms[i]);
		}
		return result;
	}
}
