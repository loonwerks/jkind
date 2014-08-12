package jkind.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.util.SexpUtil;

public class Lambda {
	final private Set<String> inputs;
	final private Sexp body;

	public Lambda(Set<String> inputs, Sexp body) {
		this.inputs = inputs;
		this.body = body;
	}

	public Lambda(String input, Sexp body) {
		this(Collections.singleton(input), body);
	}

	public Lambda(Sexp body) {
		this(Collections.<String> emptySet(), body);
	}

	public Sexp instantiate(int k) {
		return instantiate(body, k);
	}
	
	private Sexp instantiate(Sexp sexp, int k) {
		if (sexp instanceof Cons) {
			Cons cons = (Cons) sexp;
			List<Sexp> args = new ArrayList<>();
			for (Sexp arg : cons.args) {
				args.add(instantiate(arg, k));
			}
			return new Cons(instantiate(cons.head, k), args);
		} else if (sexp instanceof Symbol) {
			Symbol symbol = (Symbol) sexp;
			if (inputs.contains(symbol.str)) {
				return SexpUtil.offset(symbol.str, k);
			} else {
				return symbol;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static Lambda cons(String head, Lambda lambda1, Lambda lambda2) {
		Set<String> inputs = new HashSet<>();
		inputs.addAll(lambda1.inputs);
		inputs.addAll(lambda2.inputs);
		Sexp body = new Cons(head, lambda1.body, lambda2.body);
		return new Lambda(inputs, body);
	}
}
