package jkind.engines.invariant;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.Expr;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.visitors.Evaluator;

public class ListInvariant implements StructuredInvariant {
	private final List<Expr> exprs = new ArrayList<>();
	
	public ListInvariant(List<Expr> exprs) {
		this.exprs.addAll(exprs);
	}

	@Override
	public boolean isTrivial() {
		return exprs.isEmpty();
	}

	@Override
	public List<Expr> toExprs() {
		return exprs;
	}

	@Override
	public void refine(Evaluator eval) {
		exprs.removeIf(e -> eval.eval(e) == BooleanValue.FALSE);
	}

	@Override
	public ListInvariant copy() {
		return new ListInvariant(exprs);
	}

	@Override
	public void reduceProven(StructuredInvariant proven) {
		if (proven instanceof ListInvariant) {
			ListInvariant listProven = (ListInvariant) proven;
			exprs.removeAll(listProven.exprs);
		}
	}

	@Override
	public List<Expr> toFinalInvariants() {
		return exprs;
	}
}
