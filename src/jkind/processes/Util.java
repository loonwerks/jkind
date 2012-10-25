package jkind.processes;

import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;

public class Util {
	public static Sexp conjoin(List<String> ids, int i) {
		List<Sexp> args = new ArrayList<Sexp>();
		for (String id : ids) {
			args.add(new Cons(id, Sexp.fromInt(i)));
		}
		return new Cons("and", args);
	}

	public static Sexp not(Sexp arg) {
		return new Cons("not", arg);
	}
}
