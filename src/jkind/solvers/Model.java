package jkind.solvers;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.sexp.Symbol;

public class Model {
	private Map<String, String> aliases;
	private Map<String, Value> valueAssignments;
	private Map<String, Map<BigInteger, Value>> functionAssignments;

	public Model() {
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
	
	public Value getValue(String id) {
		return valueAssignments.get(getAlias(id));
	}
	
	public Value getValue(Symbol sym) {
		return getValue(sym.toString());
	}

	public Map<BigInteger, Value> getFunction(String fn) {
		return functionAssignments.get(getAlias(fn));
	}
	
	public Value getFunctionValue(String fn, BigInteger index) {
		return getFunction(fn).get(index);
	}
	
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
