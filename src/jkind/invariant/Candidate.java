package jkind.invariant;

import java.math.BigInteger;

import jkind.util.Util;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;

public class Candidate {
	final public String id;
	final public Sexp sexp;
	final public String text;

	public Candidate(String id, Sexp sexp, String text) {
		this.id = id;
		this.sexp = sexp;
		this.text = text;
	}

	public boolean isTrue(Model model, BigInteger k) {
		BoolValue bv = (BoolValue) model.getFunctionValue(id, k);
		return bv.getBool();
	}

	public Sexp getDeclaration() {
		Sexp type = new Cons("->", new Symbol("nat"), new Symbol("bool"));
		return new Cons("define", new Symbol(id), new Symbol("::"), type);
	}

	public Sexp getDefinition() {
		return new Cons("assert", new Cons("=", new Symbol(id), sexp));
	}

	public Sexp index(Sexp index, boolean pure) {
		if (pure) {
			return new Cons(sexp, index);
		} else {
			return new Cons(id, index);
		}
	}

	@Override
	public String toString() {
		return text;
	}

	final public static Candidate TRUE = new Candidate("canTrue", Util.lambdaI(new Symbol("true")),
			"true");
	final public static Candidate FALSE = new Candidate("canFalse",
			Util.lambdaI(new Symbol("false")), "false");
}
