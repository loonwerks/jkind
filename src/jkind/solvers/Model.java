package jkind.solvers;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkind.sexp.Symbol;

public class Model {
	// Is there a nicer (and more general) way to represent models?
	private Map<String, Value> valueAssignments;
	private Map<String, Map<BigInteger, Value>> functionAssignments;

	public Model() {
		this.valueAssignments = new HashMap<String, Value>();
		this.functionAssignments = new HashMap<String, Map<BigInteger, Value>>();
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
	
	public Value getValue(String id) {
		return valueAssignments.get(id);
	}
	
	public Value getValue(Symbol sym) {
		return getValue(sym.toString());
	}

	public Map<BigInteger, Value> getFunction(String fn) {
		return functionAssignments.get(fn);
	}
	
	public Value getFunctionValue(String fn, BigInteger index) {
		return getFunction(fn).get(index);
	}
	
	public Set<String> getFunctions() {
		return functionAssignments.keySet();
	}
}
