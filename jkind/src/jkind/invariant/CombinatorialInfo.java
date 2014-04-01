package jkind.invariant;

import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprIterVisitor;

public class CombinatorialInfo {
	private Set<String> combinatorialIds;
	
	public CombinatorialInfo(Node node) {
		this.combinatorialIds = new HashSet<>();
		for (Equation eq : node.equations) {
			if (isCombinatorialExpr(eq.expr)) {
				combinatorialIds.add(eq.lhs.get(0).id);
			}
		}
	}

	private boolean isCombinatorialExpr(Expr expr) {
		final boolean flag[] = {true};
		
		expr.accept(new ExprIterVisitor() {
			@Override
			public Void visit(UnaryExpr e) {
				if (e.op == UnaryOp.PRE) {
					flag[0] = false;
					return null;
				} else {
					return super.visit(e);
				}
			}
		});
		
		return flag[0];
	}

	public boolean isCombinatorial(String id) {
		return combinatorialIds.contains(id);
	}
}
