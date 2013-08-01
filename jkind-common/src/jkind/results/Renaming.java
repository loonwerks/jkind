package jkind.results;

/**
 * An interface for renaming and removing variables
 */
public interface Renaming {
	/**
	 * Returns the new name for a given name, or null if the original name
	 * should be hidden. This method should always return the same result when
	 * given the same input.
	 * 
	 * @param original
	 *            the original variable name
	 * @return the new variable name or null if variable should be hidden
	 */
	public String rename(String original);
}
