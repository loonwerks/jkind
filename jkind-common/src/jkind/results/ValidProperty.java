package jkind.results;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import jkind.util.Util;

/**
 * A valid property
 */
public final class ValidProperty extends Property {
	private final String source;
	private final int k;
	private final List<String> invariants;
	private final Set<String> support;

	public ValidProperty(String name, String source, int k, double runtime,
			List<String> invariants, Collection<String> support) {
		super(name, runtime);
		this.source = source;
		this.k = k;
		this.invariants = Util.safeList(invariants);
		this.support = Util.safeStringSortedSet(support);
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
	 * JKindApi.setReduceSupport()
	 */
	public List<String> getInvariants() {
		return invariants;
	}

	/**
	 * Set of support used to prove property, only available if
	 * JKindApi.setReduceSupport()
	 */
	public Set<String> getSupport() {
		return support;
	}
}
