package jkind.pdr;

import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class AbstractedVariable extends AbstractedTerm {
	private final int index;

	public AbstractedVariable(int index) {
		this.index = index;
	}

	@Override
	public Term apply(Script script, List<Term> arguments) {
		return arguments.get(index);
	}

	@Override
	public String toString() {
		return "#" + index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		if (!(obj instanceof AbstractedVariable)) {
			return false;
		}
		AbstractedVariable other = (AbstractedVariable) obj;
		if (index != other.index) {
			return false;
		}
		return true;
	}
}
