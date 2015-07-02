package jkind.translation.inline;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.visitors.AstMapVisitor;

public class InlineEquations {
	public static int MAX_COST = 3;
	
	public static Node node(Node node) {
		Set<String> inline = new InlineGraph(node).getInlineVariables(MAX_COST);

		Map<String, Expr> inlineEquations = new HashMap<>();
		for (Equation eq : node.equations) {
			String id = eq.lhs.get(0).id;
			if (inline.contains(id)) {
				inlineEquations.put(id, eq.expr);
			}
		}

		return new AstMapVisitor() {
			@Override
			public Expr visit(IdExpr e) {
				if (inlineEquations.containsKey(e.id)) {
					return inlineEquations.get(e.id).accept(this);
				} else {
					return e;
				}
			}
		}.visit(node);
	}
}
