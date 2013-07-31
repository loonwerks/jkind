package jkind.api;

import java.util.HashMap;
import java.util.Map;

import jkind.lustre.values.Value;

public class Signal {
	private final String name;
	private final Map<Integer, Value> values = new HashMap<>();

	public Signal(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void put(int key, Value value) {
		values.put(key, value);
	}

	public Value get(int step) {
		return values.get(step);
	}

	public Map<Integer, Value> getValues() {
		return values;
	}
}
