package jkind.engines.invariant;

import java.util.ArrayList;
import java.util.List;

import jkind.analysis.evaluation.Evaluator;
import jkind.lustre.Expr;
import jkind.lustre.values.BooleanValue;
import jkind.solvers.Model;
import jkind.solvers.ModelEvaluator;

public class ListInvariant implements Invariant {
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
	public void refine(Model model, int k) {
		Evaluator evaluator = new ModelEvaluator(model, k);
		exprs.removeIf(e -> evaluator.eval(e) == BooleanValue.FALSE);
	}

	@Override
	public ListInvariant copy() {
		return new ListInvariant(exprs);
	}

	@Override
	public void reduceProven(Invariant proven) {
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
