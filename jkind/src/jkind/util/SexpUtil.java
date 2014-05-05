package jkind.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jkind.invariant.Invariant;
import jkind.lustre.EnumType;
import jkind.lustre.SubrangeIntType;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class SexpUtil {
	public static Sexp subrangeConstraint(String id, Sexp index, SubrangeIntType subrange) {
		Sexp var = new Cons("$" + id, index);
		return boundConstraint(var, Sexp.fromBigInt(subrange.low), Sexp.fromBigInt(subrange.high));
	}

	public static Sexp enumConstraint(String id, Sexp index, EnumType et) {
		Sexp var = new Cons("$" + id, index);
		return boundConstraint(var, Sexp.fromInt(0), Sexp.fromInt(et.values.size() - 1));
	}

	private static Sexp boundConstraint(Sexp var, Sexp low, Sexp high) {
		return new Cons("and", new Cons("<=", low, var), new Cons("<=", var, high));
	}

	public static Sexp conjoin(Collection<? extends Sexp> fns, Sexp i) {
		if (fns.isEmpty()) {
			return new Symbol("true");
		}

		List<Sexp> args = new ArrayList<>();
		for (Sexp fn : fns) {
			args.add(new Cons(fn, i));
		}
		return new Cons("and", args);
	}

	public static Sexp conjoinStreams(Collection<String> ids, Sexp i) {
		List<Sexp> symbols = new ArrayList<>();
		for (String id : ids) {
			symbols.add(new Symbol("$" + id));
		}
		return conjoin(symbols, i);
	}

	public static Sexp conjoinInvariants(Collection<Invariant> invariants, Sexp i) {
		if (invariants.isEmpty()) {
			return new Symbol("true");
		}

		List<Sexp> sexps = new ArrayList<>();
		for (Invariant invariant : invariants) {
			sexps.add(invariant.instantiate(i));
		}
		return new Cons("and", sexps);
	}

	final public static Symbol I = new Symbol("i");
}
