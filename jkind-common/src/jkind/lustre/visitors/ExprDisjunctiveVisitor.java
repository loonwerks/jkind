package jkind.lustre.visitors;

import java.util.Collection;

import jkind.lustre.*;

public class ExprDisjunctiveVisitor implements ExprVisitor<Boolean> {
	@Override
	public Boolean visit(ArrayAccessExpr e) {
		return e.array.accept(this) || e.index.accept(this);
	}

	@Override
	public Boolean visit(ArrayExpr e) {
		return visitExprs(e.elements);
	}

	@Override
	public Boolean visit(ArrayUpdateExpr e) {
		return e.array.accept(this) || e.index.accept(this) || e.value.accept(this);
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		return e.left.accept(this) || e.right.accept(this);
	}

	@Override
	public Boolean visit(BoolExpr e) {
		return false;
	}

	@Override
	public Boolean visit(CastExpr e) {
		return e.expr.accept(this);
	}

	@Override
	public Boolean visit(CondactExpr e) {
		return e.clock.accept(this) || e.call.accept(this) || visitExprs(e.args);
	}

	@Override
	public Boolean visit(FunctionCallExpr e) {
		return visitExprs(e.args);
	}

	@Override
	public Boolean visit(IdExpr e) {
		return false;
	}

	@Override
	public Boolean visit(IfThenElseExpr e) {
		return e.cond.accept(this) || e.thenExpr.accept(this) || e.elseExpr.accept(this);
	}

	@Override
	public Boolean visit(IntExpr e) {
		return false;
	}

	@Override
	public Boolean visit(NodeCallExpr e) {
		return visitExprs(e.args);
	}

	@Override
	public Boolean visit(RepairExpr e) {
		throw new IllegalArgumentException("Repair expression cannot appear here, it must be resolved first.");
	}


	@Override
	public Boolean visit(RealExpr e) {
		return false;
	}

	@Override
	public Boolean visit(RecordAccessExpr e) {
		return e.record.accept(this);
	}

	@Override
	public Boolean visit(RecordExpr e) {
		return visitExprs(e.fields.values());
	}

	@Override
	public Boolean visit(RecordUpdateExpr e) {
		return e.record.accept(this) || e.value.accept(this);
	}

	@Override
	public Boolean visit(TupleExpr e) {
		return visitExprs(e.elements);
	}

	@Override
	public Boolean visit(UnaryExpr e) {
		return e.expr.accept(this);
	}

	protected Boolean visitExprs(Collection<Expr> list) {
		for (Expr e : list) {
			if (e.accept(this)) {
				return true;
			}
		}
		return false;
	}
}
