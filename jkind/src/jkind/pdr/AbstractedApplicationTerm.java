package jkind.pdr;

import java.util.Arrays;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class AbstractedApplicationTerm extends AbstractedTerm {
	private final String name;
	private final AbstractedTerm[] parameters;

	public AbstractedApplicationTerm(ApplicationTerm at, List<Term> variables) {
		this.name = at.getFunction().getName();
		this.parameters = new AbstractedTerm[at.getParameters().length];
		for (int i = 0; i < at.getParameters().length; i++) {
			parameters[i] = AbstractedTerm.make(at.getParameters()[i], variables);
		}
	}

	@Override
	public Term apply(Script script, List<Term> arguments) {
		Term[] terms = new Term[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			terms[i] = parameters[i].apply(script, arguments);
		}
		return script.term(name, terms);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("(");
		result.append(name);
		for (AbstractedTerm param : parameters) {
			result.append(" ");
			result.append(param);
		}
		result.append(")");
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(parameters);
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
		if (!(obj instanceof AbstractedApplicationTerm)) {
			return false;
		}
		AbstractedApplicationTerm other = (AbstractedApplicationTerm) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (!Arrays.equals(parameters, other.parameters)) {
			return false;
		}
		return true;
	}
}
