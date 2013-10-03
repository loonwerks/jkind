package jkind.results.layout;

import java.util.List;

/**
 * Layout information for signals in counterexamples
 * 
 * @see NodeLayout
 * @see SingletonLayout
 */
public interface Layout {
	/**
	 * Return the list of categories to use, in the order desired
	 * 
	 * For example, this might return ["Inputs", "Outputs", "Locals"]
	 */
	public List<String> getCategories();

	/**
	 * Get the category for a specific signal
	 * 
	 * @param signal
	 *            Name of the signal
	 * @return The category to which the signal belongs, or <code>null</code> if
	 *         the signal should be ignored
	 */
	public String getCategory(String signal);
}
