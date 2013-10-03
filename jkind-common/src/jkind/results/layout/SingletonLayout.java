package jkind.results.layout;

import java.util.Collections;
import java.util.List;

/**
 * A layout which assigns everything to one single category
 */
public class SingletonLayout implements Layout {
	private final String name;

	/**
	 * @param name
	 *            Name of the single category
	 */
	public SingletonLayout(String name) {
		this.name = name;
	}

	public SingletonLayout() {
		this("Signals");
	}

	@Override
	public List<String> getCategories() {
		return Collections.singletonList(name);
	}

	@Override
	public String getCategory(String signal) {
		return name;
	}
}
