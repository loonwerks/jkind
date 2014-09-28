package jkind.solvers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jkind.lustre.values.Value;

public class SimpleFunction extends Function {
	private final Map<List<Value>, Value> values = new HashMap<>();

	public void addValue(List<Value> inputs, Value output) {
		values.put(inputs, output);
	}

	public Set<Entry<List<Value>, Value>> entrySet() {
		return values.entrySet();
	}

	@Override
	public Value getValue(List<Value> args) {
		return values.get(args);
	}
}
