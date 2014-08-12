package jkind.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jkind.invariant.Invariant;
import jkind.lustre.EnumType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.VarDecl;
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

	public static Symbol offset(String id, int k) {
		if (k >= 0) {
			return new Symbol("$" + id + "$" + k);
		} else {
			// Using - in symbol names causes parse issues, so we use ~ instead
			return new Symbol("$" + id + "$~" + -k);
		}
	}

	public static VarDecl offset(VarDecl vd, int k) {
		return new VarDecl(offset(vd.id, k).str, vd.type);
	}
	
	public static String getBaseName(String var) {
		ensureMangledStreamName(var);
		return var.substring(1, var.indexOf("$", 1));
	}

	public static String getBaseName(Symbol symbol) {
		return getBaseName(symbol.str);
	}

	public static int getOffset(String var) {
		ensureMangledStreamName(var);
		String str = var.substring(var.lastIndexOf("$") + 1);
		if (str.startsWith("~")) {
			return -Integer.parseInt(str.substring(1));
		} else {
			return Integer.parseInt(str);
		}
	}

	public static int getOffset(Symbol symbol) {
		return getOffset(symbol.str);
	}

	private static void ensureMangledStreamName(String var) {
		if (!isMangledStreamName(var)) {
			throw new IllegalArgumentException("Not a mangled stream name: " + var);
		}
	}

	public static boolean isMangledStreamName(String var) {
		return var.startsWith("$") && var.substring(1).contains("$");
	}

	public static Sexp conjoinOffsets(List<String> ids, int k) {
		if (ids.isEmpty()) {
			return new Symbol("true");
		}

		List<Sexp> conjuncts = new ArrayList<>();
		for (String id : ids) {
			conjuncts.add(offset(id, k));
		}
		return new Cons("and", conjuncts);
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
