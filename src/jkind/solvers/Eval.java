package jkind.solvers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Eval {
	private Model model;

	public Eval(Model model) {
		this.model = model;
	}

	public Value eval(Sexp sexp) {
		if (sexp instanceof Symbol) {
			return evalSymbol(((Symbol) sexp).sym);
		} else if (sexp instanceof Cons) {
			return evalCons((Cons) sexp);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Value evalSymbol(String sym) {
		if (sym.equals("true")) {
			return BoolValue.TRUE;
		} else if (sym.equals("false")) {
			return BoolValue.FALSE;
		} else {
			return new NumericValue(sym);
		}
	}

	private Value evalCons(Cons sexp) {
		String fn = ((Symbol) sexp.head).sym;
		if (fn.equals("ite")) {
			return isTrue(eval(sexp.args.get(0))) ? eval(sexp.args.get(1)) : eval(sexp.args.get(2));
		} else if (fn.equals("and")) {
			for (Sexp arg : sexp.args) {
				if (!isTrue(eval(arg))) {
					return BoolValue.FALSE;
				}
				return BoolValue.TRUE;
			}
		}
		
		List<Value> args = new ArrayList<Value>();
		for (Sexp arg : sexp.args) {
			args.add(eval(arg));
		}
		return evalFunction(fn, args);
	}

	private boolean isTrue(Value v) {
		return v == BoolValue.TRUE;
	}

	private Value evalFunction(String fn, List<Value> args) {
		if (fn.equals("=")) {
			return BoolValue.fromBool(args.get(0).equals(args.get(1)));
		} else if (fn.equals("-")) {
			NumericValue x = (NumericValue) args.get(0);
			return new NumericValue("-" + x);
		} else if (fn.equals("/")) {
			NumericValue p = (NumericValue) args.get(0);
			NumericValue q = (NumericValue) args.get(1);
			return new NumericValue(p + "/" + q);
		} else if (fn.equals("<=")) {
			BigInteger p = new BigInteger(args.get(0).toString());
			BigInteger q = new BigInteger(args.get(1).toString());
			return BoolValue.fromBool(p.compareTo(q) <= 0);
		} else if (fn.equals("not")) {
			return BoolValue.fromBool(!isTrue(args.get(0)));
		} else {
			BigInteger index = new BigInteger(args.get(0).toString());
			return model.getFunctionValue(fn, index);
		}
	}
}