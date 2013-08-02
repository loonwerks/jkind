package jkind.results;

import java.util.Collections;
import java.util.List;

public final class ValidProperty extends Property {
	private final int k;
	private final double runtime;
	private final List<String> invariants;

	public ValidProperty(String name, int k, double runtime, List<String> invariants) {
		super(name);
		this.k = k;
		this.runtime = runtime;
		this.invariants = invariants;
	}
	
	public int getK() {
		return k;
	}

	public List<String> getInvariants() {
		return Collections.unmodifiableList(invariants);
	}

	public double getRuntime() {
		return runtime;
	}
	
	@Override
	public Property rename(Renaming renaming) {
		String newName = renaming.rename(name);
		if (newName == null) {
			return null;
		}
		
		return new ValidProperty(newName, k, runtime, invariants);
	}
}
