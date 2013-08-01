package jkind.api;

public abstract class Property {
	protected final String name;

	public Property(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract Property rename(Renaming renaming);
}
