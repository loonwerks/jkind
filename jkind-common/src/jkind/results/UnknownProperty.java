package jkind.results;


/**
 * An unknown property
 */
public final class UnknownProperty extends Property {
	private final int trueFor;
	private Counterexample cex;

	public UnknownProperty(String name, int trueFor, Counterexample cex, double runtime) {
		super(name, runtime);
		this.trueFor = trueFor;
		this.cex = cex;
	}
	
	/**
	 * How many steps the property was true for in the base step
	 */
	public int getTrueFor() {
		return trueFor;
	}
	
	/**
	 * Inductive counterexample for the property, only available if
	 * JKindApi.setInductiveCounterexamples()
	 */
	public Counterexample getInductiveCounterexample() {
		return cex;
	}

	@Override
	public void discardDetails() {
		cex = null;
	}
}
