package jkind.excel.layout;

import java.util.Collections;
import java.util.List;

public class SingletonLayout implements Layout {
	private final String name;
	
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
	public String getCategory(String varName) {
		return name;
	}
}
