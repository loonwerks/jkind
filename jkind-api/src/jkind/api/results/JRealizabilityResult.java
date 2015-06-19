package jkind.api.results;

import java.util.Collections;

import jkind.util.Util;

/**
 * This class holds the results of a run of JRealizability.

 * Note on renaming: This object can be configured with a {@link Renaming} which
 * changes the names of properties and signals as they arrive. In this case, all
 * properties are added and retrieved using their original names.
 * 
 * @see PropertyResult
 */
public class JRealizabilityResult extends JKindResult {
	/**
	 * Construct a JRealizabilityResult to hold the results of a run of JRealizability
	 * 
	 * @param name
	 *            Name of the results
	 */
	public JRealizabilityResult(String name) {
		super(name, Collections.singletonList(Util.REALIZABLE));
	}
	
	/**
	 * Construct a JRealizabilityResult to hold the results of a run of JRealizability
	 * 
	 * @param name
	 *            Name of the results	 
	 * @param renaming
	 *            Renaming to apply to apply properties
	 */
	public JRealizabilityResult(String name, Renaming renaming) {
		super(name, Collections.singletonList(Util.REALIZABLE), renaming);
	}
	
	
	/**
	 * Get the PropertyResult for realizability
	 */
	public PropertyResult getPropertyResult() {
		return getPropertyResult(Util.REALIZABLE);
	}
}
