package jkind.engines.invariant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.LustreUtil;
import jkind.lustre.values.BooleanValue;
import jkind.solvers.Model;
import jkind.solvers.ModelEvaluator;

public class Node {
	private List<Expr> candidates;

	public Node(List<Expr> candidates) {
		this.candidates = candidates;
	}

	public boolean isEmpty() {
		return candidates.isEmpty();
	}

	public Expr getRepresentative() {
		return candidates.get(0);
	}

	public boolean isSingleton() {
		return candidates.size() <= 1;
	}

	public List<Expr> toInvariants() {
		Iterator<Expr> iterator = candidates.iterator();
		Expr first = iterator.next();

		if (first instanceof BoolExpr) {
			BoolExpr bool = (BoolExpr) first;
			return optimizeInvariants(bool.value, iterator);
		}

		List<Expr> invariants = new ArrayList<>();
		while (iterator.hasNext()) {
			Expr other = iterator.next();
			invariants.add(new BinaryExpr(first, BinaryOp.EQUAL, other));
		}

		return invariants;
	}

	/**
	 * By optimizing simple invariants we can prove some properties directly
	 * from invariant generation
	 */
	private List<Expr> optimizeInvariants(boolean value, Iterator<Expr> iterator) {
		List<Expr> invariants = new ArrayList<>();
		while (iterator.hasNext()) {
			Expr expr = iterator.next();
			invariants.add(value ? expr : LustreUtil.not(expr));
		}
		return invariants;
	}

	public List<Node> split(Model model, int offset) {
		List<Expr> falses = new ArrayList<>();
		List<Expr> trues = new ArrayList<>();

		for (Expr candidate : candidates) {
			if (isTrue(candidate, offset, model)) {
				trues.add(candidate);
			} else {
				falses.add(candidate);
			}
		}

		List<Node> chain = new ArrayList<>(2);
		chain.add(new Node(falses));
		chain.add(new Node(trues));
		return chain;
	}

	private boolean isTrue(Expr expr, int offset, Model model) {
		ModelEvaluator evaluator = new ModelEvaluator(model, offset);
		return evaluator.eval(expr) == BooleanValue.TRUE;
	}

	@Override
	public String toString() {
		return candidates.toString();
	}
}
