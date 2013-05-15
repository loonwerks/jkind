package jkind.solvers.smtlib2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Type;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Eval;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.solvers.Value;
import jkind.util.Util;

public class SmtLib2Model extends Model {
	private HashMap<String, Sexp> values;
	private HashMap<String, Lambda> functions;

	public SmtLib2Model() {
		this.values = new HashMap<String, Sexp>();
		this.functions = new HashMap<String, Lambda>();
	}

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
			lambda = new Lambda(Util.I, getDefaultValue(fn));
			functions.put(fn, lambda);
		}
		
		return new Eval(this).eval(lambda.instantiate(new Symbol(index.toString())));
	}

	private Symbol getDefaultValue(String fn) {
		if (declarations.get(fn).getType() == Type.BOOL) {
			return new Symbol("true");
		} else {
			return new Symbol("0");
		}
	}

	
	public Lambda getFunction(String fn) {
		return functions.get(fn);
	}
	
	@Override
	public Set<String> getFunctions() {
		return new HashSet<String>(functions.keySet());
	}
}
