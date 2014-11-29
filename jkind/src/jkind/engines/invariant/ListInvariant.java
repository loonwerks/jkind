package jkind.engines.invariant;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import jkind.analysis.evaluation.Evaluator;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;

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
	public Sexp toSexp(int k) {
		return SexpUtil.conjoinInvariants(exprs, k);
	}

	@Override
	public void refine(Model model, int k) {
		Evaluator eval = new Evaluator() {
			@Override
			public Value visit(IdExpr e) {
				return model.getValue(new StreamIndex(e.id, k));
			}
		};
		
		ListIterator<Expr> iterator = exprs.listIterator();
		while (iterator.hasNext()) {
			Expr expr = iterator.next();
			if (expr.accept(eval) == BooleanValue.FALSE) {
				iterator.remove();
			}
		}
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
