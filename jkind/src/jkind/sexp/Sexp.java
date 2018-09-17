package jkind.sexp;

import java.math.BigInteger;

public abstract class Sexp {
	protected abstract void toBuilder(StringBuilder sb);

	public static Sexp fromInt(int i) {
		if (i >= 0) {
			return new Symbol(Integer.toString(i));
		} else {
			return new Cons("-", new Symbol("0"), new Symbol(Integer.toString(-i)));
		}
	}

	public static Sexp fromBigInt(BigInteger i) {
		if (i.signum() >= 0) {
			return new Symbol(i.toString());
		} else {
			return new Cons("-", new Symbol("0"), new Symbol(i.negate().toString()));
		}
	}

	public static Sexp fromBoolean(boolean b) {
		return new Symbol(b ? "true" : "false");
	}
}
