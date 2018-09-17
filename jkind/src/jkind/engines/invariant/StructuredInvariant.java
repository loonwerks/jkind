package jkind.engines.invariant;

import java.util.List;

import jkind.lustre.Expr;
import jkind.lustre.visitors.Evaluator;

public interface StructuredInvariant {
	public boolean isTrivial();

	public List<Expr> toExprs();

	public void refine(Evaluator eval);

	public StructuredInvariant copy();

	public void reduceProven(StructuredInvariant proven);

	public List<Expr> toFinalInvariants();
}
