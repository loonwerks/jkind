package jkind.solvers;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.lustre.Type;

public abstract class Model {
	protected final Map<String, Type> streamTypes;
	
	public Model(Map<String, Type> streamTypes) {
		this.streamTypes = streamTypes;
	}
	
	protected Value getDefaultStreamValue(String name) {
		if (streamTypes.get(name) == NamedType.BOOL) {
			return BoolValue.TRUE;
		} else {
			return new NumericValue("0");
		}
	}

	public abstract Value getValue(String name);
	public abstract Value getFunctionValue(String name, BigInteger index);
	public abstract Set<String> getFunctionNames();
	public abstract Model slice(Set<String> keep);
}
