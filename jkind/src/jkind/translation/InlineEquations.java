package jkind.translation;

import java.util.HashMap;
import java.util.Map;

import jkind.invariant.CombinatorialInfo;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;

public class InlineEquations {
	public static Node node(Node node) {
		Map<String, Expr> equations = getCombinatorialEquations(node);
		return new SubstitutionVisitor(equations) {
			private int depth = 0;
			
			@Override
			public Expr visit(IdExpr e) {
				if (depth < 10 && map.containsKey(e.id)) {
					depth++;
					Expr replacement = map.get(e.id).accept(this);
					depth--;
					return replacement;
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
