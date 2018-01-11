package jkind.analysis;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.CastExpr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.Node;
import jkind.lustre.visitors.AstIterVisitor;

/**
 * The arith-only feature for yices yields generally improved performance, but
 * it is not available if we use casting, modulus, or integer division.
 */
public class YicesArithOnlyCheck extends AstIterVisitor {
	public static boolean check(Node node) {
		YicesArithOnlyCheck visitor = new YicesArithOnlyCheck();
		node.accept(visitor);
		return visitor.arithOnly;
	}

	private boolean arithOnly = true;

	@Override
	public Void visit(BinaryExpr e) {
		if (e.op == BinaryOp.MODULUS || e.op == BinaryOp.INT_DIVIDE) {
			arithOnly = false;
		} else {
			super.visit(e);
		}
		return null;
	}

	@Override
	public Void visit(CastExpr e) {
		arithOnly = false;
		return null;
	}
	
	@Override
	public Void visit(FunctionCallExpr e) {
		arithOnly = false;
		return null;
	}
}
