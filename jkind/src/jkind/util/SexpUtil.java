package jkind.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jkind.invariant.Invariant;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class SexpUtil {
	public static Sexp conjoin(List<? extends Sexp> conjuncts) {
		if (conjuncts.isEmpty()) {
			return new Symbol("true");
		} else if (conjuncts.size() == 1) {
			return conjuncts.get(0);
		} else {
			return new Cons("and", conjuncts);
		}
	}

	public static Sexp disjoin(List<Sexp> disjuncts) {
		if (disjuncts.isEmpty()) {
			return new Symbol("false");
		} else if (disjuncts.size() == 1) {
			return disjuncts.get(0);
		} else {
			return new Cons("or", disjuncts);
		}
	}

	public static Sexp conjoinInvariants(Collection<Invariant> invariants, int k) {
		List<Sexp> conjuncts = new ArrayList<>();
		for (Invariant invariant : invariants) {
			conjuncts.add(invariant.instantiate(k));
		}
		return SexpUtil.conjoin(conjuncts);
	}
}
