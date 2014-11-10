package jkind.pdr;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Main {
	public static void main(String[] args) {
		Symbol x = new Symbol("x");
		Symbol y = new Symbol("y");
		Symbol z = new Symbol("z");
		Sexp nx = new Cons("not", x);
		Sexp ny = new Cons("not", y);
		Sexp nz = new Cons("not", z);
		Sexp I = new Cons("and", nx, ny, nz);
		
		Symbol xp = new Symbol("x'");
		Symbol yp = new Symbol("y'");
		Symbol zp = new Symbol("z'");
		Sexp T = new Cons("=", xp, x);
		
		Sexp P = new Cons("not", new Cons("and", x, y, z));
		
		Pdr pdr = new Pdr(I, T, P);
		System.out.println(pdr.check());
	}
}
