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

	/**
	 * Rename property and signals (if present), possibly omitting some
	 * 
	 * @param renaming
	 *            The renaming to use
	 * @return Renamed version of the property, or <code>null</code> if there is
	 *         no renaming for the property
	 * @see Renaming
	 */
	public abstract Property rename(Renaming renaming);
}
