package jkind.results;


/**
 * An invalid property
 */
public final class InvalidProperty extends Property {
	private final Counterexample cex;

	public InvalidProperty(String name, Counterexample cex, double runtime) {
		super(name, runtime);
		this.cex = cex;
	}

	/**
	 * Counterexample for the property
	 */
	public Counterexample getCounterexample() {
		return cex;
	}
}
