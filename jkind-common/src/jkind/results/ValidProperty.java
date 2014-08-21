package jkind.results;

import java.util.Collections;
import java.util.List;

import jkind.util.Util;

/**
 * A valid property
 */
public final class ValidProperty extends Property {
	private final int k;
	private final double runtime;
	private final List<String> invariants;

	public ValidProperty(String name, int k, double runtime, List<String> invariants) {
		super(name);
		this.k = k;
		this.runtime = runtime;
		this.invariants = Util.safeList(invariants);
	}
	
	/**
	 * k value (from k-induction) used to prove the property
	 */
	public int getK() {
		return k;
	}

	/**
	 * Invariants used to prove property, only available if JKindApi.setReduceInvariants()
	 */
	public List<String> getInvariants() {
		return Collections.unmodifiableList(invariants);
	}

	/**
	 * Runtime of verification measured in seconds
	 */
	public double getRuntime() {
		return runtime;
	}
}
