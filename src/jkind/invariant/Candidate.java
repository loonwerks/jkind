package jkind.invariant;

import java.math.BigInteger;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;

public class Candidate {
	final public String id;
	final public Sexp sexp;

	public Candidate(String id, Sexp sexp) {
		this.id = id;
		this.sexp = sexp;
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
		return id;
	}

	final public static Candidate TRUE = new Candidate("canTrue", new Cons("lambda", new Cons("i",
			new Symbol("::"), new Symbol("nat")), new Symbol("true")));
	final public static Candidate FALSE = new Candidate("canFalse", new Cons("lambda", new Cons("i",
			new Symbol("::"), new Symbol("nat")), new Symbol("false")));
}
