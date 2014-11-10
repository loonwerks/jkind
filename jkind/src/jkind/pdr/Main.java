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
		Sexp I = new Cons("and", nx, ny, z);
		
		Symbol xp = new Symbol("x'");
		Symbol yp = new Symbol("y'");
		Symbol zp = new Symbol("z'");
		Sexp T = new Cons("and", new Cons("=", xp, y), new Cons("=", yp, z), new Cons("=", zp, x));
		
		Sexp P = new Cons("or", nx, ny);
		
		Pdr pdr = new Pdr(I, T, P);
		showCex(pdr.check());
	}

	private static void showCex(Cube c) {
		while (c != null) {
			System.out.println(c);
			c = c.getNext();
		}
		System.out.println("Done");
	}
}
