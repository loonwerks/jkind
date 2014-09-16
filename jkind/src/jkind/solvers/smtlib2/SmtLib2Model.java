package jkind.solvers.smtlib2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.sexp.Sexp;
import jkind.solvers.Eval;
import jkind.solvers.Model;
import jkind.solvers.ModelFunction;

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
		if (sexp == null) {
			return getDefaultValue(varTypes.get(name));
		}
		return new Eval(this).eval(sexp);
	}

	@Override
	public Set<String> getVariableNames() {
		return new HashSet<>(values.keySet());
	}

	@Override
	public ModelFunction getFunction(String name) {
		throw new IllegalArgumentException();
	}

	@Override
	public Set<String> getFunctionNames() {
		throw new IllegalArgumentException();
	}
}
