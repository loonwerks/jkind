package jkind.solvers.smtlib2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.NamedType;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Eval;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.solvers.Value;

public class SmtLib2Model extends Model {
	private HashMap<String, Sexp> values;
	private HashMap<String, Lambda> functions;

	public SmtLib2Model() {
		this.values = new HashMap<>();
		this.functions = new HashMap<>();
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
	public Value getFunctionValue(String fn, List<Value> inputs) {
		Lambda lambda;
		if (functions.containsKey(fn)) {
			lambda = functions.get(fn);
		} else if (definitions.containsKey(fn)) {
			lambda = definitions.get(fn).getLambda();
		} else {
			List<Symbol> args = new ArrayList<>();
			for (int i = 0; i < inputs.size(); i++) {
				args.add(new Symbol("i" + i));
			}
			lambda = new Lambda(args, getDefaultValue(fn));
			functions.put(fn, lambda);
		}
		
		return new Eval(this).eval(lambda.instantiate(valuesToSymbols(inputs)));
	}
	
	private List<Symbol> valuesToSymbols(List<Value> inputs) {
		List<Symbol> symbols = new ArrayList<>();
		for (Value input : inputs) {
			symbols.add(new Symbol(input.toString()));
		}
		return symbols;
	}

	private Symbol getDefaultValue(String fn) {
		if (declarations.get(fn).getOutput() == NamedType.BOOL) {
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
		return new HashSet<>(functions.keySet());
	}
}
