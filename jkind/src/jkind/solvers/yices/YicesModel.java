package jkind.solvers.yices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.solvers.Model;
import jkind.util.SexpUtil;

public class YicesModel extends Model {
	private final Map<String, String> aliases = new HashMap<>();
	private final Map<String, Value> values = new HashMap<>();
	private final Map<String, YicesFunction> functions = new HashMap<>();

	public YicesModel(Map<String, Type> varTypes) {
		super(varTypes);
	}

	public void addAlias(String from, String to) {
		aliases.put(from, to);
	}

	public void addValue(String name, Value value) {
		values.put(name, value);
	}

	public void addFunctionValue(String name, List<Value> inputs, Value output) {
		YicesFunction fn = getOrCreateFunction(name);
		fn.addValue(inputs, output);
	}

	private YicesFunction getOrCreateFunction(String name) {
		if (functions.containsKey(name)) {
			return functions.get(name);
		} else {
			YicesFunction fn = new YicesFunction();
			functions.put(name, fn);
			return fn;
		}
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
		Value value = values.get(getAlias(name));
		if (value == null) {
			return getDefaultValue(varTypes.get(name));
		} else {
			return value;
		}
	}

	@Override
	public Set<String> getVariableNames() {
		Set<String> result = new HashSet<>();
		for (String name : aliases.keySet()) {
			if (!SexpUtil.isEncodedFunction(name)) {
				result.add(name);
			}
		}
		result.addAll(values.keySet());
		return result;
	}

	public Set<String> getFunctionNames() {
		Set<String> result = new HashSet<>();
		for (String name : aliases.keySet()) {
			if (SexpUtil.isEncodedFunction(name)) {
				result.add(name);
			}
		}
		result.addAll(functions.keySet());
		return result;
	}

	public YicesFunction getFunction(String name) {
		return functions.get(aliases.get(name));
	}

	@Override
	public YicesModel slice(Set<String> keep) {
		YicesModel sliced = new YicesModel(varTypes);
		
		for (String var : getVariableNames()) {
			if (SexpUtil.isMangledStreamName(var) && keep.contains(SexpUtil.getBaseName(var))) {
				sliced.values.put(var, getValue(var));
			}
		}
		
		for (String fn : getFunctionNames()) {
			sliced.functions.put(fn, getFunction(fn));
		}
		
		return sliced;
	}
}
