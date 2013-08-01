package jkind.results;

public final class UnknownProperty extends Property {
	public UnknownProperty(String name) {
		super(name);
	}

	@Override
	public Property rename(Renaming renaming) {
		String newName = renaming.rename(name);
		if (newName == null) {
			return null;
		}
		
		return new UnknownProperty(newName);
	}
}
