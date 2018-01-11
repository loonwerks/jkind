package jkind.solvers.yices2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Function;
import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.results.FunctionTable;
import jkind.solvers.yices.YicesModel;
import jkind.util.Util;

public class Yices2Model extends YicesModel {
	private final Map<String, Value> functionDefaultValue = new HashMap<>();

	public Yices2Model(Map<String, Type> varTypes, List<Function> functions) {
		super(varTypes, functions);
	}
	
	public void setFunctionDefaultValue(String name, Value value) {
		functionDefaultValue.put(name, value);
	}

	@Override
	public Value evaluateFunction(String name, List<Value> inputs) {
		name = getAlias(name);
		
		FunctionTable table = functionTables.get(name);
		Value value = table.lookup(inputs);
		if (value == null) {
			value = functionDefaultValue.get(name);
		}
		
		return Util.promoteIfNeeded(value, table.getOutput().type);
	}
}
