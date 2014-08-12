package jkind.translation;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprIterVisitor;

public class ContainsTemporalOperator extends ExprIterVisitor {
	public static boolean check(Expr e) {
		ContainsTemporalOperator visitor = new ContainsTemporalOperator();
		e.accept(visitor);
		return visitor.containsTemporalOperator;
	}

	private boolean containsTemporalOperator = false;

	@Override
	public Void visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			containsTemporalOperator = true;
		} else {
			super.visit(e);
		}
		return null;
	}

	@Override
	public Void visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			containsTemporalOperator = true;
		} else {
			super.visit(e);
		}
		return null;
	}
}
