package jkind.translation;

import java.math.BigDecimal;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.UnaryExpr;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;

public class Expr2SexpVisitor implements ExprVisitor<Sexp> {
	final private Symbol iSym;
	final private int offset;

	private Expr2SexpVisitor(Symbol iSym, int offset) {
		this.iSym = iSym;
		this.offset = offset;
	}

	public Expr2SexpVisitor(Symbol iSym) {
		this(iSym, 0);
	}

	private Expr2SexpVisitor pre() {
		return new Expr2SexpVisitor(iSym, offset - 1);
	}

	@Override
	public Sexp visit(BinaryExpr e) {
		Sexp left = e.left.accept(this);
		Sexp right = e.right.accept(this);

		switch (e.op) {
		case NOTEQUAL:
		case XOR:
			return new Cons("/=", left, right);

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
	public Sexp visit(IdExpr e) {
		return new Cons(e.id, new Cons("+", iSym, Sexp.fromInt(offset)));
	}

	@Override
	public Sexp visit(IfThenElseExpr e) {
		return new Cons("ite", e.cond.accept(this), e.thenExpr.accept(this),
				e.elseExpr.accept(this));
	}

	@Override
	public Sexp visit(IntExpr e) {
		return Sexp.fromInt(e.value);
	}

	@Override
	public Sexp visit(RealExpr e) {
		Sexp numerator = Sexp.fromBigInt(e.value.unscaledValue());
		Sexp denominator = Sexp.fromBigInt(BigDecimal.TEN.pow(e.value.scale()).toBigInteger());
		return new Cons("/", numerator, denominator);
	}

	@Override
	public Sexp visit(UnaryExpr e) {
		switch (e.op) {
		case PRE:
			return e.expr.accept(this.pre());
			
		case NEGATIVE:
			return new Cons("-", new Symbol("0"), e.expr.accept(this));
			
		default:
			return new Cons(e.op.toString(), e.expr.accept(this));
		}
	}
}
