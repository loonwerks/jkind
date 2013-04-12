package jkind.solvers;

import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Lambda {
	final private Symbol arg;
	final private Sexp body;
	
	public Lambda(Symbol arg, Sexp body) {
		this.arg = arg;
		this.body = body;
	}

	public Symbol getArg() {
		return arg;
	}

	public Sexp getBody() {
		return body;
	}

	public Sexp instantiate(Sexp actual) {
		return substitute(body, arg, actual);
	}

	private static Sexp substitute(Sexp sexp, Sexp x, Sexp t) {
		if (sexp instanceof Cons) {
			Cons cons = (Cons) sexp;
			List<Sexp> args = new ArrayList<Sexp>();
			for (Sexp arg : cons.args) {
				args.add(substitute(arg, x, t));
			}
			return new Cons(substitute(cons.head, x, t), args);
		} else if (sexp instanceof Symbol) {
			Symbol symbol = (Symbol) sexp;
			if (sexp.equals(x)) {
				return t;
			} else {
				return symbol;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
}
