package jkind.results;

public final class UnknownProperty extends Property {
	private final Counterexample cex;

	public UnknownProperty(String name, int k, Counterexample cex) {
		super(name, k);
		this.cex = cex;
	}

	public Counterexample getInductiveCounterexample() {
		return cex;
	}
	
	@Override
	public Property rename(Renaming renaming) {
		String newName = renaming.rename(name);
		if (newName == null) {
			return null;
		}
		
		return new UnknownProperty(newName, k, cex);
	}
}
