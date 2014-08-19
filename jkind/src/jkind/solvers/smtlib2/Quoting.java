package jkind.solvers.smtlib2;

import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Quoting {
	public static boolean needsQuotes(Symbol symb) {
		return symb.str.contains("[");
	}

	public static Symbol quoteSymbol(Symbol symb) {
		return new Symbol("|" + symb.str + "|");
	}
	
	public static Sexp quoteSexp(Sexp sexp) {
		if (sexp instanceof Cons) {
			Cons cons = (Cons) sexp;
			return new Cons(quoteSexp(cons.head), quoteSexps(cons.args));
		} else if (sexp instanceof Symbol) {
			Symbol symb = (Symbol) sexp;
			if (needsQuotes(symb)) {
				return quoteSymbol(symb);
			} else {
				return symb;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	private static List<Sexp> quoteSexps(List<? extends Sexp> sexps) {
		List<Sexp> result = new ArrayList<>();
		for (Sexp sexp : sexps) {
			result.add(quoteSexp(sexp));
		}
		return result;
	}

	public static String unquote(String text) {
		if (text.startsWith("|")) {
			return text.substring(1, text.length() - 1);
		} else {
			return text;
		}
	}

}
