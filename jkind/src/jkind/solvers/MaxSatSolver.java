package jkind.solvers;

import jkind.sexp.Sexp;

public interface MaxSatSolver {
	public void assertSoft(Sexp sexp);

	public Result maxsatQuery(Sexp query);
}
