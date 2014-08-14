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
	public static Sexp subrangeConstraint(String id, SubrangeIntType subrange) {
		return boundConstraint(id, Sexp.fromBigInt(subrange.low), Sexp.fromBigInt(subrange.high));
	}

	public static Sexp enumConstraint(String id, EnumType et) {
		return boundConstraint(id, Sexp.fromInt(0), Sexp.fromInt(et.values.size() - 1));
	}

	private static Sexp boundConstraint(String id, Sexp low, Sexp high) {
		Symbol var = new Symbol(id);
		return new Cons("and", new Cons("<=", low, var), new Cons("<=", var, high));
	}

	public static Sexp conjoin(List<? extends Sexp> sexps) {
		if (sexps.isEmpty()) {
			return new Symbol("true");
		}

		return new Cons("and", sexps);
	}

	public static Sexp conjoinInvariants(Collection<Invariant> invariants, int k) {
		if (invariants.isEmpty()) {
			return new Symbol("true");
		}

		List<Sexp> conjuncts = new ArrayList<>();
		for (Invariant invariant : invariants) {
			conjuncts.add(invariant.instantiate(k));
		}
		return new Cons("and", conjuncts);
	}
}
