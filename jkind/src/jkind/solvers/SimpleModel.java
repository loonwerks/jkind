package jkind.solvers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Function;
import jkind.lustre.values.Value;
import jkind.util.StreamIndex;

public class SimpleModel extends Model {
	private final Map<String, Value> values = new HashMap<>();

	public SimpleModel() {
		super(Collections.emptyMap(), Collections.emptyList());
	}

	public SimpleModel(List<Function> functions) {
		super(Collections.emptyMap(), functions);
	}

	public void putValue(StreamIndex si, Value value) {
		values.put(si.getEncoded().str, value);
	}

	public void putValue(String encoded, Value value) {
		values.put(encoded, value);
	}

	@Override
	public Value getValue(String name) {
		return values.get(name);
	}

	@Override
	public Set<String> getVariableNames() {
		return values.keySet();
	}

	@Override
	public boolean hasVariable(String name) {
		// This model carries values rather than declared types, so its own map
		// is what decides whether a variable is available.
		return values.containsKey(name);
	}
}
