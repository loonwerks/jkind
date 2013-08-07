package jkind.results;


/**
 * Abstract class of property result from JKind
 */
public abstract class Property {
	protected final String name;

	public Property(String name) {
		this.name = name;
	}

	/**
	 * Get the name of the property
	 */
	public String getName() {
		return name;
	}
}
