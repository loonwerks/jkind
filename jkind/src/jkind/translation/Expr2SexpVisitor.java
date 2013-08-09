package jkind.translation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jkind.Settings;
import jkind.Settings.SolverOption;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.ProjectionExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.UnaryExpr;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.StreamDecl;

public class Expr2SexpVisitor implements ExprVisitor<Sexp> {
	final private Symbol iSym;
	private int offset = 0;

	private static int sideConditionCounter = 0;
	final private List<StreamDecl> sideConditionDeclarations = new ArrayList<>();
	final private List<Sexp> sideConditions = new ArrayList<>();

	public Expr2SexpVisitor(Symbol iSym) {
		this.iSym = iSym;
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

		case INT_DIVIDE:
			if (Settings.solver == SolverOption.CVC4) {
				if (right.equals(new Symbol("1"))) {
					return left;
				} else {
					StreamDecl divDecl = new StreamDecl("%div" + sideConditionCounter, NamedType.INT);
					Sexp div = new Cons(divDecl.getId(), iSym);
					sideConditionCounter++;

					sideConditionDeclarations.add(divDecl);
					sideConditions.add(new Cons("<=", new Cons("*", div, right), left));
					sideConditions.add(new Cons("<", left, new Cons("+", new Cons("*", div, right),
							right)));

					return div;
				}
			} else {
				return new Cons(e.op.toString(), left, right);
			}

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
	public Sexp visit(ProjectionExpr e) {
		System.out.println(e);
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
	}
	
	@Override
	public Sexp visit(RealExpr e) {
		if (Settings.solver == SolverOption.YICES) {
			Sexp numerator = Sexp.fromBigInt(e.value.unscaledValue());
			Sexp denominator = Sexp.fromBigInt(BigDecimal.TEN.pow(e.value.scale()).toBigInteger());
			return new Cons("/", numerator, denominator);
		} else {
			return new Symbol(e.value.toPlainString());
		}
	}

	@Override
	public Sexp visit(RecordExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
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

	public boolean hasSideConditions() {
		return !sideConditionDeclarations.isEmpty();
	}

	public List<StreamDecl> getSideConditionDeclarations() {
		return sideConditionDeclarations;
	}

	public List<Sexp> getSideConditions() {
		return sideConditions;
	}
}
