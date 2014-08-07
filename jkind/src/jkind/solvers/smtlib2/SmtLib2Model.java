package jkind.solvers.smtlib2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Eval;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.solvers.Value;
import jkind.util.SexpUtil;

public class SmtLib2Model extends Model {
	private HashMap<String, Sexp> values = new HashMap<>();
	private HashMap<String, Lambda> functions = new HashMap<>();

	public void addValue(String id, Sexp sexp) {
		values.put(id, sexp);
	}

	public void addFunction(String fn, Lambda lambda) {
		functions.put(fn, lambda);
	}

	@Override
	public Value getValue(Symbol sym) {
		return new Eval(this).eval(values.get(sym.toString()));
	}

	@Override
	public Value getFunctionValue(String fn, BigInteger index) {
		Lambda lambda;
		if (functions.containsKey(fn)) {
			lambda = functions.get(fn);
		} else if (definitions.containsKey(fn)) {
			lambda = definitions.get(fn).getLambda();
		} else {
			lambda = new Lambda(SexpUtil.I, getDefaultValue(fn));
			functions.put(fn, lambda);
		}

		return new Eval(this).eval(lambda.instantiate(new Symbol(index.toString())));
	}

	private Symbol getDefaultValue(String fn) {
		if (declarations.get(fn).getType() == NamedType.BOOL) {
			return new Symbol("true");
		} else {
			return new Symbol("0");
		}
	}

	@Override
	public Set<String> getFunctionNames() {
		return new HashSet<>(functions.keySet());
	}
	
	@Override
	public SmtLib2Model slice(Set<String> keep) {
		SmtLib2Model sliced = new SmtLib2Model();
		for (String fn : getFunctionNames()) {
			if (fn.startsWith("$") && keep.contains(fn.substring(1))) {
				sliced.addFunction(fn, functions.get(fn));
			}
		}
		return sliced;
	}
}
