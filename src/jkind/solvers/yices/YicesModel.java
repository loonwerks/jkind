package jkind.solvers.yices;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Eval;
import jkind.solvers.Model;
import jkind.solvers.Value;

public class YicesModel extends Model {
	private Map<String, String> aliases;
	private Map<String, Value> valueAssignments;
	private Map<String, Map<BigInteger, Value>> functionAssignments;

	public YicesModel() {
		this.aliases = new HashMap<String, String>();
		this.valueAssignments = new HashMap<String, Value>();
		this.functionAssignments = new HashMap<String, Map<BigInteger, Value>>();
	}
	
	public void addAlias(String from, String to) {
		aliases.put(from, to);
	}
	
	public void addValue(String id, Value v) {
		valueAssignments.put(id, v);
	}
	
	public void addFunctionValue(String fn, BigInteger arg, Value v) {
		Map<BigInteger, Value> fnMap = functionAssignments.get(fn);
		if (fnMap == null) {
			fnMap = new HashMap<BigInteger, Value>();
			functionAssignments.put(fn, fnMap);
		}
		
		fnMap.put(arg, v);
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

	public Map<BigInteger, Value> getFunction(String fn) {
		return functionAssignments.get(getAlias(fn));
	}
	
	@Override
	public Value getFunctionValue(String fn, BigInteger index) {
		fn = getAlias(fn);
		if (functionAssignments.containsKey(fn)) {
			return functionAssignments.get(fn).get(index);
		} else if (definitions.containsKey(fn)) {
			Sexp s = definitions.get(fn).getLambda().instantiate(new Symbol(index.toString()));
			return new Eval(this).eval(s);
		} else {
			throw new IllegalArgumentException("Unknown function: " + fn);
		}
	}

	@Override
	public Set<String> getFunctions() {
		Set<String> fns = new HashSet<String>(functionAssignments.keySet());
		for (String alias : aliases.keySet()) {
			if (getFunction(alias) != null) {
				fns.add(alias);
			}
		}
		return fns;
	}
}
