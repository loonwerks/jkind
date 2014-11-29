package jkind.engines.invariant;

import java.util.List;

import jkind.lustre.Expr;
import jkind.solvers.Model;

public interface Invariant {
	public boolean isTrivial();

	public List<Expr> toExprs();

	public void refine(Model model, int k);

	public Invariant copy();

	public void reduceProven(Invariant proven);

	public List<Expr> toFinalInvariants();
}
