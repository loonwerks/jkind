package jkind.results;

/**
 * An invalid property
 */
public final class InvalidProperty extends Property {
	private final String source;
	private final Counterexample cex;

	public InvalidProperty(String name, String source, Counterexample cex, double runtime) {
		super(name, runtime);
		this.source = source;
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
}
