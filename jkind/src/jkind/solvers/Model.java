package jkind.solvers;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.sexp.Sexp;
import jkind.util.BigFraction;
import jkind.util.StreamIndex;
import jkind.util.Util;

public abstract class Model {
    protected Map<String, Type> varTypes;
    private final Set<String> typeConstructors = new HashSet<>();

    public Model(Map<String, Type> varTypes) {
        this.varTypes = Collections.unmodifiableMap(new HashMap<>(varTypes));
    }

    public void addTypeConstructor(String name) {
        typeConstructors.add(name);
    }

    public boolean isTypeConstructor(String name) {
        return typeConstructors.contains(name);
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
