package jkind.solvers.cvc4;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.solvers.Value;

public class Cvc4Model extends Model {
	private HashMap<String, Sexp> values;
	private HashMap<String, Lambda> functions;

	public Cvc4Model() {
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
		return Eval.eval(values.get(sym.toString()));
	}

	@Override
	public Value getFunctionValue(String fn, BigInteger index) {
		return Eval.eval(functions.get(fn).instantiate(new Symbol(index.toString())));
	}

	
	public Lambda getFunction(String fn) {
		return functions.get(fn);
	}
	
	@Override
	public Set<String> getFunctions() {
		return new HashSet<String>(functions.keySet());
	}
}
