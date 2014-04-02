package jkind.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Lambda {
	final private List<Symbol> args;
	final private Sexp body;

	public Lambda(List<Symbol> args, Sexp body) {
		this.args = args;
		this.body = body;
	}

	/*
	 * For convenience we provide a single argument interface to lambda and do
	 * run-time error checking on it.
	 */
	public Lambda(Symbol arg, Sexp body) {
		this.args = Collections.singletonList(arg);
		this.body = body;
	}

	public List<Symbol> getArgs() {
		return args;
	}

	public Symbol getArg() {
		if (args.size() != 1) {
			throw new IllegalArgumentException();
		}
		return args.get(0);
	}

	public Sexp getBody() {
		return body;
	}

	public Sexp instantiate(List<? extends Sexp> actuals) {
		if (args.size() != actuals.size()) {
			throw new IllegalArgumentException();
		}
		return substitute(body, args, actuals);
	}

	private static Sexp substitute(Sexp sexp, List<Symbol> xs, List<? extends Sexp> ts) {
		if (sexp instanceof Cons) {
			Cons cons = (Cons) sexp;
			List<Sexp> args = new ArrayList<>();
			for (Sexp arg : cons.args) {
				args.add(substitute(arg, xs, ts));
			}
			return new Cons(substitute(cons.head, xs, ts), args);
		} else if (sexp instanceof Symbol) {
			int i = xs.indexOf(sexp);
			if (i >= 0) {
				return ts.get(i);
			} else {
				return sexp;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Sexp instantiate(Sexp actual) {
		if (args.size() != 1) {
			throw new IllegalArgumentException();
		}
		return substitute(body, args, Collections.singletonList(actual));
	}
}
