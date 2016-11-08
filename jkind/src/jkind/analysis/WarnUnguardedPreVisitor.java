package jkind.analysis;

import jkind.StdErr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprIterVisitor;

public class WarnUnguardedPreVisitor extends ExprIterVisitor {
	public static void check(Program program) {
		for (Node node : program.nodes) {
			for (Equation eq : node.equations) {
				eq.expr.accept(UNGUARDED);
			}
			for (Expr e : node.assertions) {
				e.accept(UNGUARDED);
			}
		}
	}

	public static final WarnUnguardedPreVisitor GUARDED = new WarnUnguardedPreVisitor();
	public static final WarnUnguardedPreVisitor UNGUARDED = new WarnUnguardedPreVisitor();

	@Override
	public Void visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			if (this == GUARDED) {
				e.expr.accept(UNGUARDED);
			} else {
				StdErr.warning(e.location, "unguarded pre expression");
			}
		} else {
			super.visit(e);
		}

		return null;
	}

	@Override
	public Void visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			e.left.accept(this);
			e.right.accept(GUARDED);
		} else {
			super.visit(e);
		}

		return null;
	}

	@Override
	public Void visit(NodeCallExpr e) {
		UNGUARDED.visitExprs(e.args);
		return null;
	}
}
