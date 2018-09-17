package jkind.results;

/**
 * Abstract class of property result from JKind
 */
public abstract class Property {
	private final String name;
	private final double runtime;

	public Property(String name, double runtime) {
		this.name = name;
		this.runtime = runtime;
	}

	/**
	 * Get the name of the property
	 */
	public String getName() {
		return name;
	}

	/**
	 * Runtime of the property measured in seconds
	 */
	public double getRuntime() {
		return runtime;
	}

	/**
	 * Discard details such as counterexamples and IVCs to save space
	 */
	public abstract void discardDetails();
}
