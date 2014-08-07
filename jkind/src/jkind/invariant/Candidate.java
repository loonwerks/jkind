package jkind.invariant;

import java.math.BigInteger;

import jkind.lustre.values.BooleanValue;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Eval;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.util.SexpUtil;

public class Candidate {
	final public Lambda lambda;
	final public String text;

	public Candidate(Lambda lambda, String text) {
		this.lambda = lambda;
		this.text = text;
	}

	public boolean isTrue(Model model, BigInteger k) {
		return new Eval(model).eval(lambda.instantiate(new Symbol(k.toString()))) == BooleanValue.TRUE;
	}

	public Sexp index(Sexp index) {
		return lambda.instantiate(index);
	}

	@Override
	public String toString() {
		return text;
	}

	final public static Candidate TRUE = new Candidate(new Lambda(SexpUtil.I, new Symbol("true")),
			"true");
	final public static Candidate FALSE = new Candidate(
			new Lambda(SexpUtil.I, new Symbol("false")), "false");
}
