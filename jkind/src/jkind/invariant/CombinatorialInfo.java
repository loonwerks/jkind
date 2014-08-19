package jkind.invariant;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprDisjunctiveVisitor;

public class CombinatorialInfo {
	private Set<String> combinatorialIds = new HashSet<>();

	public CombinatorialInfo(Node node) {
		for (Equation eq : node.equations) {
			if (!containsPre(eq.expr)) {
				combinatorialIds.add(eq.lhs.get(0).id);
			}
		}
	}

	private boolean containsPre(Expr expr) {
		return expr.accept(new ExprDisjunctiveVisitor() {
			@Override
			public Boolean visit(UnaryExpr e) {
				return e.op == UnaryOp.PRE || super.visit(e);
			}
		});
	}

	public boolean isCombinatorial(String id) {
		return combinatorialIds.contains(id);
	}
}
