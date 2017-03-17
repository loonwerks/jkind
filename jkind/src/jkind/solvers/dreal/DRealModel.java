package jkind.solvers.dreal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.solvers.Model;
import jkind.util.Util;

public class DRealModel extends Model {
	private final Map<String, Value> values = new HashMap<>();

	public DRealModel(Map<String, Type> varTypes) {
		super(varTypes);
	}

	public void addValue(String name, Value value) {
		values.put(name, value);
	}


	@Override
	public Value getValue(String name) {
		Value value = values.get(name);
		if (value == null) {
			return Util.getDefaultValue(varTypes.get(name));
		} else {
			return value;
		}
	}

	@Override
	public Set<String> getVariableNames() {
		Set<String> result = new HashSet<>();
		result.addAll(values.keySet());
		return result;
	}
}
