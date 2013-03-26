package jkind.solvers.yices;

import jkind.solvers.Label;

public class IntLabel extends Label {
	final public int i;
	
	public IntLabel(int i) {
		this.i = i;
	}

	@Override
	public int hashCode() {
		return i;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntLabel other = (IntLabel) obj;
		if (i != other.i)
			return false;
		return true;
	}

}
