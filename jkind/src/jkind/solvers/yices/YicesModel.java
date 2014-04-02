package jkind.solvers.yices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.BoolValue;
import jkind.solvers.Eval;
import jkind.solvers.Model;
import jkind.solvers.NumericValue;
import jkind.solvers.Value;

public class YicesModel extends Model {
	private Map<String, String> aliases;
	private Map<String, Value> valueAssignments;
	private Map<String, Map<List<Value>, Value>> functionAssignments;

	public YicesModel() {
		this.aliases = new HashMap<>();
		this.valueAssignments = new HashMap<>();
		this.functionAssignments = new HashMap<>();
	}

	public void addAlias(String from, String to) {
		aliases.put(from, to);
	}

	public void addValue(String id, Value v) {
		valueAssignments.put(id, v);
	}

	public void addFunctionValue(String fn, List<Value> inputs, Value output) {
		Map<List<Value>, Value> fnMap = functionAssignments.get(fn);
		if (fnMap == null) {
			fnMap = new HashMap<>();
			functionAssignments.put(fn, fnMap);
		}

		fnMap.put(inputs, output);
	}

	private String getAlias(String id) {
		String result = id;
		while (aliases.containsKey(result)) {
			result = aliases.get(result);
		}
		return result;
	}

	@Override
	public Value getValue(Symbol sym) {
		return valueAssignments.get(getAlias(sym.toString()));
	}

	public Map<List<Value>, Value> getFunction(String fn) {
		return functionAssignments.get(getAlias(fn));
	}

	@Override
	public Value getFunctionValue(String fn, List<Value> inputs) {
		fn = getAlias(fn);
		if (functionAssignments.containsKey(fn) && functionAssignments.get(fn).containsKey(inputs)) {
			return functionAssignments.get(fn).get(inputs);
		} else if (definitions.containsKey(fn)) {
			Symbol argument = new Symbol(inputs.get(0).toString());
			Sexp s = definitions.get(fn).getLambda().instantiate(argument);
			return new Eval(this).eval(s);
		} else {
			Value value = getDefaultValue(fn);
			addFunctionValue(fn, inputs, value);
			return value;
		}
	}

	private Value getDefaultValue(String fn) {
		if (declarations.get(fn).getOutput() == NamedType.BOOL) {
			return BoolValue.TRUE;
		} else {
			return new NumericValue("0");
		}
	}

	@Override
	public Set<String> getFunctions() {
		Set<String> fns = new HashSet<>(functionAssignments.keySet());
		for (String alias : aliases.keySet()) {
			if (getFunction(alias) != null) {
				fns.add(alias);
			}
		}
		return fns;
	}
}
