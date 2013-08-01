package jkind.results;

public final class InvalidProperty extends Property {
	private final Counterexample cex;
	private final double runtime;

	public InvalidProperty(String name, int k, Counterexample cex, double runtime) {
		super(name, k);
		this.runtime = runtime;
		this.cex = cex;
	}

	public int getK() {
		return k;
	}

	public Counterexample getCounterexample() {
		return cex;
	}
	
	@Override
	public Property rename(Renaming renaming) {
		String newName = renaming.rename(name);
		if (newName == null) {
			return null;
		}
		
		return new InvalidProperty(newName, k, cex.rename(renaming), runtime);
	}

	public double getRuntime() {
		return runtime;
	}
}
