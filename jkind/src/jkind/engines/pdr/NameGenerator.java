package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {
	private final String prefix;
	private int counter = 0;

	public NameGenerator(String prefix) {
		this.prefix = prefix;
	}

	public String getNextName() {
		return prefix + counter++;
	}

	public List<String> getAllNames() {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < counter; i++) {
			result.add(prefix + i);
		}
		return result;
	}
}
