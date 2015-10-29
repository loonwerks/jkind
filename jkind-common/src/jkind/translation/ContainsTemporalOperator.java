package jkind.translation;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprDisjunctiveVisitor;

/**
 * Check if an expression contains a 'pre' or '->' operator.
 */
public class ContainsTemporalOperator extends ExprDisjunctiveVisitor {
	public static boolean check(Expr e) {
		return e.accept(new ContainsTemporalOperator());
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		return e.op == BinaryOp.ARROW || super.visit(e);
	}

	@Override
	public Boolean visit(UnaryExpr e) {
		return e.op == UnaryOp.PRE || super.visit(e);
	}
}
