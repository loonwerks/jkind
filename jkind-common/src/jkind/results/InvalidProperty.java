package jkind.results;

import java.util.Collections;
import java.util.List;

import jkind.util.Util;

/**
 * An invalid property
 */
public final class InvalidProperty extends Property {
	private final String source;
	private Counterexample cex;
	private List<String> conflicts;

	public InvalidProperty(String name, String source, Counterexample cex, List<String> conflicts,
			double runtime) {
		super(name, runtime);
		this.source = source;
		this.conflicts = Util.safeList(conflicts);
		this.cex = cex;
	}

	/**
	 * Name of the engine used to find the counterexample (bmc, pdr, ...)
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Counterexample for the property
	 */
	public Counterexample getCounterexample() {
		return cex;
	}
	
	/**
	 * Conflicts (used in realizability analysis)
	 */
	public List<String> getConflicts() {
		return conflicts;
	}

	@Override
	public void discardDetails() {
		cex = null;
		conflicts = Collections.emptyList();
	}
}
