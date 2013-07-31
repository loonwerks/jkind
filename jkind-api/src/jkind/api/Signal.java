package jkind.api;

import java.util.HashMap;
import java.util.Map;

import jkind.lustre.values.Value;

public class Signal<T extends Value> {
	private final String name;
	private final Map<Integer, T> values = new HashMap<>();

	public Signal(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void putValue(int step, T value) {
		values.put(step, value);
	}

	public T getValue(int step) {
		return values.get(step);
	}

	public Map<Integer, T> getValues() {
		return values;
	}
}
