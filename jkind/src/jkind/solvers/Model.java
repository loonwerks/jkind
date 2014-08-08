package jkind.solvers;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.util.BigFraction;
import jkind.util.Util;

public abstract class Model {
	protected final Map<String, Type> streamTypes;

	public Model(Map<String, Type> streamTypes) {
		this.streamTypes = streamTypes;
	}

	protected Value getDefaultStreamValue(String name) {
		String typeName = Util.getName(streamTypes.get(name));
		switch (typeName) {
		case "bool":
			return BooleanValue.TRUE;
		case "int":
			return new IntegerValue(BigInteger.ZERO);
		case "real":
			return new RealValue(BigFraction.ZERO);
		default:
			throw new IllegalArgumentException("Unknown stream type: " + typeName);
		}
	}

	public abstract Value getValue(String name);
	public abstract Value getFunctionValue(String name, BigInteger index);
	public abstract Set<String> getFunctionNames();
	public abstract Model slice(Set<String> keep);
}
