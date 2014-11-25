package jkind.invariant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.values.BooleanValue;
import jkind.sexp.Sexp;
import jkind.solvers.Eval;
import jkind.solvers.Model;
import jkind.translation.Lustre2Sexp;

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

	public List<Invariant> toInvariants() {
		List<Invariant> invariants = new ArrayList<>();

		Iterator<Expr> iterator = candidates.iterator();
		Expr first = iterator.next();

		while (iterator.hasNext()) {
			Expr other = iterator.next();
			Expr expr = new BinaryExpr(first, BinaryOp.EQUAL, other);
			invariants.add(new Invariant(expr));
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
		Sexp sexp = expr.accept(new Lustre2Sexp(offset));
		return new Eval(model).eval(sexp) == BooleanValue.TRUE;
	}

	@Override
	public String toString() {
		return candidates.toString();
	}
}
