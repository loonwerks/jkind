package jkind.solvers.yices;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.sexp.Symbol;
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
		return getFunction(fn).get(index);
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
