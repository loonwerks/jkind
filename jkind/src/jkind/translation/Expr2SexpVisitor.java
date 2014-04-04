package jkind.translation;

import java.math.BigDecimal;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
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
import jkind.lustre.visitors.ExprVisitor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Expr2SexpVisitor implements ExprVisitor<Sexp> {
	final private Symbol iSym;
	private int offset = 0;

	public Expr2SexpVisitor(Symbol iSym) {
		this.iSym = iSym;
	}

	@Override
	public Sexp visit(ArrayAccessExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(ArrayExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(ArrayUpdateExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(BinaryExpr e) {
		Sexp left = e.left.accept(this);
		Sexp right = e.right.accept(this);

		switch (e.op) {
		case NOTEQUAL:
		case XOR:
			return new Cons("not", new Cons("=", left, right));

		case ARROW:
			Sexp cond = new Cons("=", iSym, Sexp.fromInt(-offset));
			return new Cons("ite", cond, left, right);

		default:
			return new Cons(e.op.toString(), left, right);
		}
	}

	@Override
	public Sexp visit(BoolExpr e) {
		return e.value ? new Symbol("true") : new Symbol("false");
	}
	
	@Override
	public Sexp visit(CastExpr e) {
		if (e.type == NamedType.REAL) {
			return new Cons("to_real", e.expr.accept(this));
		} else if (e.type == NamedType.INT) {
			return new Cons("to_int", e.expr.accept(this));
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Sexp visit(CondactExpr e) {
		throw new IllegalArgumentException("Condacts must be removed before translation to sexp");
	}

	@Override
	public Sexp visit(IdExpr e) {
		return new Cons("$" + e.id, new Cons("+", iSym, Sexp.fromInt(offset)));
	}

	@Override
	public Sexp visit(IfThenElseExpr e) {
		return new Cons("ite", e.cond.accept(this), e.thenExpr.accept(this),
				e.elseExpr.accept(this));
	}

	@Override
	public Sexp visit(IntExpr e) {
		return Sexp.fromBigInt(e.value);
	}

	@Override
	public Sexp visit(NodeCallExpr e) {
		throw new IllegalArgumentException("Node calls must be inlined before translation to sexp");
	}

	@Override
	public Sexp visit(RealExpr e) {
		Sexp numerator = Sexp.fromBigInt(e.value.unscaledValue());
		Sexp denominator = Sexp.fromBigInt(BigDecimal.TEN.pow(e.value.scale()).toBigInteger());
		return new Cons("/", numerator, denominator);
	}

	@Override
	public Sexp visit(RecordAccessExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(RecordExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(RecordUpdateExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
	}
	
	@Override
	public Sexp visit(TupleExpr e) {
		throw new IllegalArgumentException("Tuples must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(UnaryExpr e) {
		switch (e.op) {
		case PRE:
			offset--;
			Sexp expr = e.expr.accept(this);
			offset++;
			return expr;

		case NEGATIVE:
			return new Cons("-", new Symbol("0"), e.expr.accept(this));

		default:
			return new Cons(e.op.toString(), e.expr.accept(this));
		}
	}
}
