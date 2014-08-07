package jkind.solvers.smtlib2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Type;
import jkind.lustre.values.Value;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Eval;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.util.SexpUtil;

public class SmtLib2Model extends Model {
	private final HashMap<String, Sexp> values = new HashMap<>();
	private final HashMap<String, Lambda> functions = new HashMap<>();

	public SmtLib2Model(Map<String, Type> streamTypes) {
		super(streamTypes);
	}

	public void addValue(String id, Sexp sexp) {
		values.put(id, sexp);
	}

	public void addFunction(String fn, Lambda lambda) {
		functions.put(fn, lambda);
	}

	@Override
	public Value getValue(String name) {
		return new Eval(this).eval(values.get(name));
	}

	@Override
	public Value getFunctionValue(String name, BigInteger index) {
		Symbol arg = new Symbol(index.toString());
		Lambda lambda = functions.get(name);
		if (lambda == null) {
			lambda = new Lambda(SexpUtil.I, new Symbol(getDefaultStreamValue(name).toString()));
			functions.put(name, lambda);
		}
		Sexp body = lambda.instantiate(arg);
		return new Eval(this).eval(body);
	}

	@Override
	public Set<String> getFunctionNames() {
		return new HashSet<>(functions.keySet());
	}
	
	@Override
	public SmtLib2Model slice(Set<String> keep) {
		SmtLib2Model sliced = new SmtLib2Model(streamTypes);
		for (String fn : getFunctionNames()) {
			if (fn.startsWith("$") && keep.contains(fn.substring(1))) {
				sliced.functions.put(fn, functions.get(fn));
			}
		}
		return sliced;
	}
}
