package jkind.results;

import java.util.Collections;
import java.util.List;

public final class ValidProperty extends Property {
	private final double runtime;
	private final List<String> invariants;

	public ValidProperty(String name, int k, List<String> invariants, double runtime) {
		super(name, k);
		this.invariants = invariants;
		this.runtime = runtime;
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
		
		return new ValidProperty(newName, k, invariants, runtime);
	}
}
