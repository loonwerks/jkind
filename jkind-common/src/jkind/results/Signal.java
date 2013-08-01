package jkind.results;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.values.Value;

public final class Signal<T extends Value> {
	private final String name;
	private final Map<Integer, T> values;

	public Signal(String name) {
		this.name = name;
		this.values = new HashMap<>();
	}

	private Signal(String name, Map<Integer, T> values) {
		this.name = name;
		this.values = values;
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
		return Collections.unmodifiableMap(values);
	}

	public <S extends T> Signal<S> cast(Class<S> klass) {
		Signal<S> castSignal = new Signal<S>(name);
		for (Integer step : values.keySet()) {
			Value value = values.get(step);
			if (klass.isInstance(value)) {
				castSignal.putValue(step, klass.cast(value));
			} else {
				throw new JKindException("Cannot cast " + value.getClass().getSimpleName()
						+ " to " + klass.getSimpleName());
			}
		}
		return castSignal;
	}

	public Signal<T> rename(Renaming renaming) {
		String newName = renaming.rename(name);
		if (newName == null) {
			return null;
		}
		
		HashMap<Integer, T> newValues = new HashMap<>(values);
		return new Signal<T>(newName, newValues);
	}
}
