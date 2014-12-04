package jkind.results;

import java.util.Collections;
import java.util.List;

import jkind.util.Util;

/**
 * A valid property
 */
public final class ValidProperty extends Property {
	private final String source;
	private final int k;
	private final List<String> invariants;

	public ValidProperty(String name, String source, int k, double runtime, List<String> invariants) {
		super(name, runtime);
		this.source = source;
		this.k = k;
		this.invariants = Util.safeList(invariants);
	}

	/**
	 * Name of the engine used to prove the property (k-induction, pdr, ...)
	 */
	public String getSource() {
		return source;
	}

	/**
	 * k value (from k-induction) used to prove the property
	 */
	public int getK() {
		return k;
	}

	/**
	 * Invariants used to prove property, only available if
	 * JKindApi.setReduceInvariants()
	 */
	public List<String> getInvariants() {
		return Collections.unmodifiableList(invariants);
	}
}
