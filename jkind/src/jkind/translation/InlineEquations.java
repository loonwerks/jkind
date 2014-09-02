package jkind.translation;

import java.util.HashMap;
import java.util.Map;

import jkind.invariant.CombinatorialInfo;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.visitors.AstIterVisitor;

public class InlineEquations {
	public static Node node(Node node) {
		Map<String, Expr> equations = getCombinatorialEquations(node);
		final Map<String, Integer> uses = new HashMap<>();

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

		return new SubstitutionVisitor(equations) {
			@Override
			public Expr visit(IdExpr e) {
				if (map.containsKey(e.id) && uses.get(e.id) <= 5) {
					return map.get(e.id).accept(this);
				} else {
					return e;
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
