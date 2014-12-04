package jkind.translation;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import jkind.engines.invariant.CombinatorialInfo;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.visitors.AstIterVisitor;
import jkind.lustre.visitors.AstMapVisitor;

public class InlineEquations {
	private static final int USES_THRESHOLD = 5;
	private static final int SIZE_THRESHOLD = 5;

	public static Node node(Node node) {
		final Map<String, Expr> equations = getCombinatorialEquations(node);
		final Map<String, Integer> uses = new HashMap<>();
		final Stack<String> stack = new Stack<>();

		node.accept(new AstIterVisitor() {
			@Override
			public Void visit(IdExpr e) {
				Integer v = uses.get(e.id);
				if (v == null) {
					v = 0;
				}
				uses.put(e.id, v + 1);
				return null;
			}
		});

		int todo_reconstruct_inlined_variables;

		return new AstMapVisitor() {
			@Override
			public Expr visit(IdExpr e) {
				if (shouldInline(e.id)) {
					stack.push(e.id);
					Expr v = equations.get(e.id).accept(this);
					stack.pop();
					return v;
				} else {
					return e;
				}
			}

			private boolean shouldInline(String id) {
				if (stack.contains(id)) {
					return false;
				} else if (!equations.containsKey(id)) {
					return false;
				} else if (uses.get(id) <= USES_THRESHOLD) {
					return true;
				} else if (equations.get(id).accept(new SizeVisitor()) <= SIZE_THRESHOLD) {
					return true;
				} else {
					return false;
				}
			}
		}.visit(node);
	}

	private static Map<String, Expr> getCombinatorialEquations(Node node) {
		CombinatorialInfo info = new CombinatorialInfo(node);
		Map<String, Expr> equations = new HashMap<>();
		for (Equation eq : node.equations) {
			String id = eq.lhs.get(0).id;
			if (info.isCombinatorial(id)) {
				equations.put(id, eq.expr);
			}
		}
		return equations;
	}
}
