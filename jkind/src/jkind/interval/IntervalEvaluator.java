package jkind.interval;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.BigFraction;
import jkind.util.StreamIndex;

public class IntervalEvaluator implements ExprVisitor<Interval> {
	private final ModelGeneralizer generalizer;
	private int i;

	public IntervalEvaluator(int i, ModelGeneralizer generalizer) {
		this.i = i;
		this.generalizer = generalizer;
	}

	@Override
	public Interval visit(ArrayAccessExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Interval visit(ArrayExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Interval visit(ArrayUpdateExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Interval visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			if (i == 0) {
				return e.left.accept(this);
			} else {
				return e.right.accept(this);
			}
		}

		Interval left = e.left.accept(this);
		Interval right = e.right.accept(this);
		return left.applyBinaryOp(e.op, right);
	}

	@Override
	public Interval visit(BoolExpr e) {
		return e.value ? BoolInterval.TRUE : BoolInterval.FALSE;
	}

	@Override
	public Interval visit(CastExpr e) {
		NumericInterval interval = (NumericInterval) e.expr.accept(this);
		if (e.type == NamedType.REAL) {
			IntEndpoint low = (IntEndpoint) interval.getLow();
			IntEndpoint high = (IntEndpoint) interval.getHigh();
			return new NumericInterval(low.real(), high.real());
		} else if (e.type == NamedType.INT) {
			RealEndpoint low = (RealEndpoint) interval.getLow();
			RealEndpoint high = (RealEndpoint) interval.getHigh();
			return new NumericInterval(low.floor(), high.floor());
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Interval visit(CondactExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Interval visit(IdExpr e) {
		return generalizer.evalId(new StreamIndex(e.id, i));
	}

	@Override
	public Interval visit(IfThenElseExpr e) {
		BoolInterval cond = (BoolInterval) e.cond.accept(this);
		if (cond.isTrue()) {
			return e.thenExpr.accept(this);
		} else if (cond.isFalse()) {
			return e.elseExpr.accept(this);
		} else {
			return e.thenExpr.accept(this).join(e.elseExpr.accept(this));
		}
	}

	@Override
	public Interval visit(IntExpr e) {
		IntEndpoint v = new IntEndpoint(e.value);
		return new NumericInterval(v, v);
	}

	@Override
	public Interval visit(NodeCallExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Interval visit(RealExpr e) {
		RealEndpoint v = new RealEndpoint(new BigFraction(e.value));
		return new NumericInterval(v, v);
	}

	@Override
	public Interval visit(RecordAccessExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Interval visit(RecordExpr e) {
		throw new IllegalArgumentException();
	}
	
	@Override
	public Interval visit(RecordUpdateExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Interval visit(TupleExpr e) {
		throw new IllegalArgumentException();
	}
	
	@Override
	public Interval visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			i--;
			Interval result = e.expr.accept(this);
			i++;
			return result;
		} else {
			return e.expr.accept(this).applyUnaryOp(e.op);
		}
	}
}
