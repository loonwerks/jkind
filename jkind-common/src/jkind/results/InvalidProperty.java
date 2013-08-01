package jkind.results;


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
	
	@Override
	public Property rename(Renaming renaming) {
		String newName = renaming.rename(name);
		if (newName == null) {
			return null;
		}
		
		return new InvalidProperty(newName, k, cex.rename(renaming));
	}
}
