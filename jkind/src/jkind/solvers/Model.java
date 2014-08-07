package jkind.solvers;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.util.BigFraction;

public abstract class Model {
	protected final Map<String, Type> streamTypes;

	public Model(Map<String, Type> streamTypes) {
		this.streamTypes = streamTypes;
	}

	protected Value getDefaultStreamValue(String name) {
		Type type = streamTypes.get(name);
		if (type == NamedType.BOOL) {
			return BooleanValue.TRUE;
		} else if (type == NamedType.INT) {
			return new IntegerValue(BigInteger.ZERO);
		} else if (type == NamedType.REAL) {
			return new RealValue(BigFraction.ZERO);
		} else {
			throw new IllegalArgumentException("Unknown stream type: " + type);
		}
	}

	public abstract Value getValue(String name);
	public abstract Value getFunctionValue(String name, BigInteger index);
	public abstract Set<String> getFunctionNames();
	public abstract Model slice(Set<String> keep);
}
