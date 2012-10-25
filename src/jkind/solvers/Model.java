package jkind.solvers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model {
	// Is there a nicer (and more general) way to represent models?
	private Map<String, Value> valueAssignments;
	private Map<String, Map<Integer, Value>> functionAssignments;

	public Model() {
		this.valueAssignments = new HashMap<String, Value>();
		this.functionAssignments = new HashMap<String, Map<Integer, Value>>();
	}
	
	public void addValue(String id, Value v) {
		valueAssignments.put(id, v);
	}
	
	public void addFunctionValue(String fn, int arg, Value v) {
		Map<Integer, Value> fnMap = functionAssignments.get(fn);
		if (fnMap == null) {
			fnMap = new HashMap<Integer, Value>();
			functionAssignments.put(fn, fnMap);
		}
		
		fnMap.put(arg, v);
	}
	
	public Value getValue(String id) {
		return valueAssignments.get(id);
	}
	
	public Map<Integer, Value> getFunction(String fn) {
		return functionAssignments.get(fn);
	}
	
	public Value getFunctionValue(String fn, int arg) {
		return getFunction(fn).get(arg);
	}
	
	public Set<String> getFunctions() {
		return functionAssignments.keySet();
	}
}
