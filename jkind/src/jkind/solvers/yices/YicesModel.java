package jkind.solvers.yices;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.solvers.Model;
import jkind.solvers.Value;

public class YicesModel extends Model {
	private final Map<String, String> aliases = new HashMap<>();
	private final Map<String, Value> values = new HashMap<>();
	private final Map<String, YicesFunction> functions = new HashMap<>();

	public YicesModel(Map<String, Type> streamTypes) {
		super(streamTypes);
	}

	public void addAlias(String from, String to) {
		aliases.put(from, to);
	}

	public void addValue(String name, Value value) {
		values.put(name, value);
	}

	public void addFunctionValue(String name, BigInteger index, Value value) {
		getOrCreateFunction(name).addValue(index, value);
	}

	private YicesFunction getOrCreateFunction(String name) {
		YicesFunction fn = functions.get(name);
		if (fn == null) {
			fn = createFunction(name);
		}
		return fn;
	}

	private YicesFunction createFunction(String name) {
		YicesFunction fn = new YicesFunction();
		functions.put(name, fn);
		return fn;
	}

	public void addFunctionDefault(String name, Value defaultValue) {
		getOrCreateFunction(name).setDefaultValue(defaultValue);
	}

	private String getAlias(String name) {
		String result = name;
		while (aliases.containsKey(result)) {
			result = aliases.get(result);
		}
		return result;
	}

	@Override
	public Value getValue(String name) {
		return values.get(getAlias(name));
	}

	public YicesFunction getFunction(String name) {
		return functions.get(getAlias(name));
	}

	@Override
	public Value getFunctionValue(String name, BigInteger index) {
		YicesFunction fn = getFunction(name);
		if (fn == null) {
			fn = createFunction(name);
			fn.setDefaultValue(getDefaultStreamValue(name));
		}
		return fn.getValue(index);
	}

	@Override
	public Set<String> getFunctionNames() {
		Set<String> fns = new HashSet<>(functions.keySet());
		for (String alias : aliases.keySet()) {
			if (getFunction(alias) != null) {
				fns.add(alias);
			}
		}
		return fns;
	}

	@Override
	public YicesModel slice(Set<String> keep) {
		YicesModel sliced = new YicesModel(streamTypes);
		for (String fn : getFunctionNames()) {
			if (fn.startsWith("$") && keep.contains(fn.substring(1))) {
				sliced.functions.put(fn, getFunction(fn));
			}
		}
		return sliced;
	}
}
