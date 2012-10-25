package jkind.translation;

import jkind.sexp.Symbol;

public class Keywords {
	// Arbitrary 'n' used in induction
	final public static String N = "@n";
	final public static Symbol N_SYM = new Symbol(N);
	
	// Transition relation
	final public static String T = "@T";
	final public static Symbol T_SYM = new Symbol(T);
}
