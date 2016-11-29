package jkind.solvers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkind.lustre.values.Value;
import jkind.util.StreamIndex;

public class SimpleModel extends Model {
	private final Map<String, Value> values = new HashMap<>();

	public SimpleModel() {
		super(Collections.emptyMap());
	}

	public SimpleModel(Model other) {
		super(Collections.emptyMap());
		for (String var : other.getVariableNames()) {
			StreamIndex si = StreamIndex.decode(var);
			if (si != null) {
				putValue(si, other.getValue(var));
			}
		}
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
}
