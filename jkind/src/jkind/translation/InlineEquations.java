package jkind.translation;

import java.util.HashMap;
import java.util.Map;

import jkind.invariant.CombinatorialInfo;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;

public class InlineEquations {
	public static Node node(Node node) {
		CombinatorialInfo info = new CombinatorialInfo(node);
		
		Map<String, Expr> equations = getCombinatorialEquations(node, info);
		
		return new SubstitutionVisitor(equations).visit(node);
	}

	private static Map<String, Expr> getCombinatorialEquations(Node node, CombinatorialInfo info) {
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
