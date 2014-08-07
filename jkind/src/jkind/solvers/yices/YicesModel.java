package jkind.solvers.yices;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
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
	private Map<String, String> aliases = new HashMap<>();
	private Map<String, Value> values = new HashMap<>();
	private Map<String, YicesFunction> functions = new HashMap<>();

	public void addAlias(String from, String to) {
		aliases.put(from, to);
	}

	public void addValue(String name, Value value) {
		values.put(name, value);
	}

	public void addFunctionValue(String name, BigInteger index, Value value) {
		getOrCreateFunction(name).addValue(index, value);
	}

	public YicesFunction getOrCreateFunction(String name) {
		YicesFunction fn = functions.get(name);
		if (fn == null) {
			fn = new YicesFunction();
			functions.put(name, fn);
		}
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
	public Value getValue(Symbol sym) {
		return values.get(getAlias(sym.toString()));
	}

	public YicesFunction getFunction(String name) {
		return functions.get(getAlias(name));
	}

	@Override
	public Value getFunctionValue(String name, BigInteger index) {
		YicesFunction fn = getFunction(name);
		if (fn != null) {
			return fn.getValue(index);
		} else if (definitions.containsKey(name)) {
			Sexp s = definitions.get(name).getLambda().instantiate(new Symbol(index.toString()));
			return new Eval(this).eval(s);
		} else {
			fn = getOrCreateFunction(name);
			fn.setDefaultValue(getDefaultValue(name));
			return fn.getValue(index);
		}
	}

	private Value getDefaultValue(String name) {
		if (declarations.get(name).getType() == NamedType.BOOL) {
			return BoolValue.TRUE;
		} else {
			return new NumericValue("0");
		}
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

	public void addFunction(String name, YicesFunction fn) {
		functions.put(name, fn);
	}
}
