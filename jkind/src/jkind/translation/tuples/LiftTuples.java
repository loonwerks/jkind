package jkind.translation.tuples;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.Program;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.AstMapVisitor;

/**
 * Lift all tuples as far as possible
 */
public class LiftTuples extends AstMapVisitor {
	public static Program program(Program program) {
		return new LiftTuples().visit(program);
	}

	@Override
	public Expr visit(BinaryExpr e) {
		Expr left = e.left.accept(this);
		Expr right = e.right.accept(this);
		if (left instanceof TupleExpr && e.op == BinaryOp.ARROW) {
			return TupleUtil.mapBinary(e.op, (TupleExpr) left, (TupleExpr) right);
		} else {
			return new BinaryExpr(left, e.op, right);
		}
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		Expr cond = e.cond.accept(this);
		Expr thenExpr = e.thenExpr.accept(this);
		Expr elseExpr = e.elseExpr.accept(this);
		if (thenExpr instanceof TupleExpr) {
			return TupleUtil.mapIf(cond, (TupleExpr) thenExpr, (TupleExpr) elseExpr);
		} else {
			return new IfThenElseExpr(cond, thenExpr, elseExpr);
		}
	}

	@Override
	public Expr visit(UnaryExpr e) {
		Expr expr = e.expr.accept(this);
		if (expr instanceof TupleExpr) {
			return TupleUtil.mapUnary(e.op, (TupleExpr) expr);
		} else {
			return new UnaryExpr(e.op, expr);
		}
	}
}
