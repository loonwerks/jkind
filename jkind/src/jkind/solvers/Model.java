package jkind.solvers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.util.StreamIndex;

public abstract class Model {
	protected Map<String, Type> varTypes;
	
	public Model(Map<String, Type> varTypes) {
		this.varTypes = Collections.unmodifiableMap(new HashMap<>(varTypes));
	}
	
	public abstract Value getValue(String name);
	public abstract Set<String> getVariableNames();

	public Value getValue(StreamIndex si) {
		return getValue(si.getEncoded().str);
	}
}
