package jkind.pdr;

import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Predicate {
	private final Term body;
	private final List<Term> variables;

	public Predicate(Term body, List<Term> variables) {
		this.body = body;
		this.variables = variables;
	}

	public Term toTerm() {
		return body;
	}

	public Term apply(Script script, List<Term> arguments) {
		return Subst.apply(script, body, variables, arguments);
	}

	@Override
	public String toString() {
		return body.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((variables == null) ? 0 : variables.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Predicate)) {
			return false;
		}
		Predicate other = (Predicate) obj;
		if (body == null) {
			if (other.body != null) {
				return false;
			}
		} else if (!body.equals(other.body)) {
			return false;
		}
		if (variables == null) {
			if (other.variables != null) {
				return false;
			}
		} else if (!variables.equals(other.variables)) {
			return false;
		}
		return true;
	}
}
