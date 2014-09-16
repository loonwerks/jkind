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
import jkind.util.StreamIndex;
import jkind.util.Util;

public abstract class Model {
	protected Map<String, Type> varTypes;
	
	public Model(Map<String, Type> varTypes) {
		this.varTypes = varTypes;
	}
	
	public abstract Value getValue(String name);
	public abstract Set<String> getVariableNames();

	public Value getValue(StreamIndex si) {
		return getValue(si.getEncoded().str);
	}
	
	protected Value getDefaultValue(Type type) {
		switch (Util.getName(type)) {
		case "bool":
			return BooleanValue.FALSE;
		case "int":
			return new IntegerValue(BigInteger.ZERO);
		case "real":
			return new RealValue(BigFraction.ZERO);
		default:
			throw new IllegalArgumentException("Unable to construct default value for type: "
					+ type);
		}
	}
}
