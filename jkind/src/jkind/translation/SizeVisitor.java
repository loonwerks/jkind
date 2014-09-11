package jkind.translation;

import java.util.Collection;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.ExprVisitor;

public class SizeVisitor implements ExprVisitor<Integer> {
	@Override
	public Integer visit(ArrayAccessExpr e) {
		return e.array.accept(this) + e.index.accept(this) + 1;
	}

	@Override
	public Integer visit(ArrayExpr e) {
		return visitExprs(e.elements) + 1;
	}
	
	@Override
	public Integer visit(ArrayUpdateExpr e) {
		return e.array.accept(this) + e.index.accept(this) + e.value.accept(this) + 1;
	}

	@Override
	public Integer visit(BinaryExpr e) {
		return e.left.accept(this) + e.right.accept(this) + 1;
	}

	@Override
	public Integer visit(BoolExpr e) {
		return 1;
	}

	@Override
	public Integer visit(CastExpr e) {
		return e.expr.accept(this) + 1;
	}

	@Override
	public Integer visit(CondactExpr e) {
		return e.clock.accept(this) + e.call.accept(this) + visitExprs(e.args) + 1;
	}

	@Override
	public Integer visit(IdExpr e) {
		return 1;
	}

	@Override
	public Integer visit(IfThenElseExpr e) {
		return e.cond.accept(this) + e.thenExpr.accept(this) + e.elseExpr.accept(this) + 1;
	}

	@Override
	public Integer visit(IntExpr e) {
		return 1;
	}

	@Override
	public Integer visit(NodeCallExpr e) {
		return visitExprs(e.args) + 1;
	}

	@Override
	public Integer visit(RealExpr e) {
		return 1;
	}

	@Override
	public Integer visit(RecordAccessExpr e) {
		return e.record.accept(this) + 1;
	}

	@Override
	public Integer visit(RecordExpr e) {
		return visitExprs(e.fields.values()) + 1;
	}

	@Override
	public Integer visit(RecordUpdateExpr e) {
		return e.record.accept(this) + e.value.accept(this) + 1;
	}

	@Override
	public Integer visit(TupleExpr e) {
		return visitExprs(e.elements) + 1;
	}

	@Override
	public Integer visit(UnaryExpr e) {
		return e.expr.accept(this) + 1;
	}

	private int visitExprs(Collection<Expr> exprs) {
		int sum = 0;
		for (Expr expr : exprs) {
			sum += expr.accept(this);
		}
		return sum;
	}
}
