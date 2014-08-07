package jkind.solvers.yices;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import jkind.solvers.Value;


public class YicesFunction {
	private final Map<BigInteger, Value> values = new HashMap<>();
	private Value defaultValue;
	
	public void addValue(BigInteger index, Value value) {
		values.put(index, value);
	}
	
	public void setDefaultValue(Value defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Value getValue(BigInteger index) {
		if (values.containsKey(index)) {
			return values.get(index);
		} else {
			return defaultValue;
		}
	}
}
