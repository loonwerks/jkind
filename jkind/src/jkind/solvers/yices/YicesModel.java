package jkind.solvers.yices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Function;
import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.solvers.Model;
import jkind.util.Util;

public class YicesModel extends Model {
	protected final Map<String, String> aliases = new HashMap<>();
	protected final Map<String, Value> values = new HashMap<>();

	public YicesModel(Map<String, Type> varTypes, List<Function> functions) {
		super(varTypes, functions);
	}

	public void addAlias(String from, String to) {
		aliases.put(from, to);
	}

	public void addValue(String name, Value value) {
		values.put(name, value);
	}

	protected String getAlias(String name) {
		String result = name;
		while (aliases.containsKey(result)) {
			result = aliases.get(result);
		}
		return result;
	}

	@Override
	public Value getValue(String name) {
		Type type = varTypes.get(name);
		if (type == null) {
			throw new IllegalArgumentException("Model queried unknown variable: " + name);
		}
		Value value = values.get(getAlias(name));
		if (value == null) {
			return Util.getDefaultValue(type);
		} else {
			return Util.promoteIfNeeded(value, type);
		}
	}

	@Override
	public Set<String> getVariableNames() {
		Set<String> result = new HashSet<>();
		result.addAll(aliases.keySet());
		result.addAll(values.keySet());
		return result;
	}
}
