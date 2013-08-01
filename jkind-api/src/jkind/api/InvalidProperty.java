package jkind.api;

public final class InvalidProperty extends Property {
	private final int k;
	private final Counterexample cex;

	public InvalidProperty(String name, int k, Counterexample cex) {
		super(name);
		this.k = k;
		this.cex = cex;
	}

	public int getK() {
		return k;
	}

	public Counterexample getCounterexample() {
		return cex;
	}
}
