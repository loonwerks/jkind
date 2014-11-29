package jkind.engines.invariant;

import java.util.List;

import jkind.lustre.Expr;
import jkind.sexp.Sexp;
import jkind.solvers.Model;


public interface Invariant {
	public boolean isTrivial();

	public Sexp toSexp(int k);

	public void refine(Model model, int k);

	public Invariant copy();

	public void reduceProven(Invariant proven);

	public List<Expr> toFinalInvariants();
}
