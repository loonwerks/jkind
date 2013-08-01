package jkind.api;

public final class ValidProperty extends Property {
	private final int k;

	public ValidProperty(String name, int k) {
		super(name);
		this.k = k;
	}

	public int getK() {
		return k;
	}

	@Override
	public Property rename(Renaming renaming) {
		String newName = renaming.rename(name);
		if (newName == null) {
			return null;
		}
		
		return new ValidProperty(newName, k);
	}
}
