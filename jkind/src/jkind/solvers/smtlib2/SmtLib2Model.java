package jkind.solvers.smtlib2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.util.BigFraction;
import jkind.util.Util;

public class SmtLib2Model extends Model {
	private final Map<String, Sexp> values = new HashMap<>();

	public SmtLib2Model(Map<String, Type> varTypes) {
		super(varTypes);
	}

	public void addValue(String id, Sexp sexp) {
		values.put(id, sexp);
	}

	@Override
	public Value getValue(String name) {
		Sexp sexp = values.get(name);
		Type type = varTypes.get(name);
		if (sexp == null) {
			return Util.getDefaultValue(type);
		}
		Value value = new SexpEvaluator(this).eval(sexp);
		return promoteIfNeeded(value, type);
	}

	private Value promoteIfNeeded(Value value, Type type) {
		if (value instanceof IntegerValue && type == NamedType.REAL) {
			IntegerValue iv = (IntegerValue) value;
			return new RealValue(new BigFraction(iv.value));
		}
		return value;
	}

	@Override
	public Set<String> getVariableNames() {
		return values.keySet();
	}
}
