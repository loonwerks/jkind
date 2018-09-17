package jkind.lustre.visitors;

import java.util.Collection;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
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

public class ExprIterVisitor implements ExprVisitor<Void> {
	@Override
	public Void visit(ArrayAccessExpr e) {
		e.array.accept(this);
		e.index.accept(this);
		return null;
	}

	@Override
	public Void visit(ArrayExpr e) {
		visitExprs(e.elements);
		return null;
	}

	@Override
	public Void visit(ArrayUpdateExpr e) {
		e.array.accept(this);
		e.index.accept(this);
		e.value.accept(this);
		return null;
	}

	@Override
	public Void visit(BinaryExpr e) {
		e.left.accept(this);
		e.right.accept(this);
		return null;
	}

	@Override
	public Void visit(BoolExpr e) {
		return null;
	}

	@Override
	public Void visit(CastExpr e) {
		e.expr.accept(this);
		return null;
	}

	@Override
	public Void visit(CondactExpr e) {
		e.clock.accept(this);
		e.call.accept(this);
		visitExprs(e.args);
		return null;
	}

	@Override
	public Void visit(FunctionCallExpr e) {
		visitExprs(e.args);
		return null;
	}

	@Override
	public Void visit(IdExpr e) {
		return null;
	}

	@Override
	public Void visit(IfThenElseExpr e) {
		e.cond.accept(this);
		e.thenExpr.accept(this);
		e.elseExpr.accept(this);
		return null;
	}

	@Override
	public Void visit(IntExpr e) {
		return null;
	}

	@Override
	public Void visit(NodeCallExpr e) {
		visitExprs(e.args);
		return null;
	}

	@Override
	public Void visit(RealExpr e) {
		return null;
	}

	@Override
	public Void visit(RecordAccessExpr e) {
		e.record.accept(this);
		return null;
	}

	@Override
	public Void visit(RecordExpr e) {
		visitExprs(e.fields.values());
		return null;
	}

	@Override
	public Void visit(RecordUpdateExpr e) {
		e.record.accept(this);
		e.value.accept(this);
		return null;
	}

	@Override
	public Void visit(TupleExpr e) {
		visitExprs(e.elements);
		return null;
	}

	@Override
	public Void visit(UnaryExpr e) {
		e.expr.accept(this);
		return null;
	}

	protected Void visitExprs(Collection<Expr> list) {
		for (Expr e : list) {
			e.accept(this);
		}
		return null;
	}
}
