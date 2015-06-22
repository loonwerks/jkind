package jkind.translation;

import java.util.HashMap;
import java.util.Map;

import jkind.engines.invariant.CombinatorialInfo;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.visitors.AstIterVisitor;

public class InlineEquations {
	public static Node node(Node node) {
		Map<String, Expr> map = getInliningMap(node);
		return new SubstitutionVisitor(map).visit(node);
	}

	private static Map<String, Expr> getInliningMap(Node node) {
		ResolvingSubstitutionMap map = new ResolvingSubstitutionMap();
		Map<String, Integer> uses = countUses(node);
		CombinatorialInfo info = new CombinatorialInfo(node);

		for (Equation eq : node.equations) {
			String id = eq.lhs.get(0).id;
			if (isUsedOnce(id, uses) && info.isCombinatorial(id)) {
				map.put(id, eq.expr);
			}
		}
		return map;
	}

	private static Map<String, Integer> countUses(Node node) {
		Map<String, Integer> uses = new HashMap<>();
		node.accept(new AstIterVisitor() {
			@Override
			public Void visit(IdExpr e) {
				int count = uses.getOrDefault(e.id, 0);
				uses.put(e.id, count + 1);
				return null;
			}
		});
		return uses;
	}

	private static boolean isUsedOnce(String id, Map<String, Integer> uses) {
		Integer count = uses.getOrDefault(id, 0);
		return count == 1;
	}
}
