package jkind.results;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import jkind.util.Util;

/**
 * A valid property
 */
public final class ValidProperty extends Property {
	private final String source;
	private final int k;
	private List<String> invariants;
	private Set<String> ivc;
	private final Set<List<String>> ivcSets;
	private final Set<List<String>> invarantSets;

	public ValidProperty(String name, String source, int k, double runtime,
			List<String> invariants, Collection<String> ivc, Set<List<String>> invariantSets,
			Set<List<String>> ivcSets) {
		super(name, runtime);
		this.source = source;
		this.k = k;
		this.invariants = Util.safeList(invariants);
		this.ivc = Util.safeStringSortedSet(ivc);
		this.invarantSets = Util.safeStringSortedSets(invariantSets);
		this.ivcSets = Util.safeStringSortedSets(ivcSets);
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
	 * JKindApi.setIvcReduction()
	 */
	public List<String> getInvariants() {
		return invariants;
	}

	/**
	 * Inductive validity core, only available if JKindApi.setIvcReduction()
	 */
	public Set<String> getIvc() {
		return ivc;
	}

	/**
	 * Invariants used to prove property, only available if
	 * JKindApi.setIvcReduction()
	 */
	public Set<List<String>> getInvariantSets() {
		return invarantSets;
	}

	/**
	 * Inductive validity core, only available if JKindApi.setIvcReduction()
	 */

	public Set<List<String>> getIvcSets() {
		return ivcSets;
	}

	@Override
	public void discardDetails() {
		invariants = Collections.emptyList();
		ivc = Collections.emptySet();
	}
}
