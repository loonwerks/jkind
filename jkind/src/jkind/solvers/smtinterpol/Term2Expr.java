package jkind.solvers.smtinterpol;

import static java.util.stream.Collectors.toList;
import static jkind.lustre.LustreUtil.castInt;
import static jkind.lustre.LustreUtil.castReal;
import static jkind.lustre.LustreUtil.divide;
import static jkind.lustre.LustreUtil.negative;
import static jkind.lustre.LustreUtil.not;
import static jkind.lustre.LustreUtil.notEqual;
import static jkind.lustre.LustreUtil.real;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.ConstantTerm;
import de.uni_freiburg.informatik.ultimate.logic.Rational;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import jkind.engines.pdr.Lustre2Term;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.RealExpr;
import jkind.util.SexpUtil;

public class Term2Expr {
	public static Expr disjunction(List<Term> disjuncts) {
		if (disjuncts.isEmpty()) {
			return new BoolExpr(false);
		}
		return leftAssociate("or", disjuncts.toArray(new Term[disjuncts.size()]));
	}

	public static Expr expr(Term term) {
		if (term instanceof ConstantTerm) {
			return expr((ConstantTerm) term);
		} else if (term instanceof ApplicationTerm) {
			return expr((ApplicationTerm) term);
		} else {
			throw new IllegalArgumentException("Unhandled term type: " + term.getClass().getSimpleName());
		}
	}

	private static Expr expr(ConstantTerm ct) {
		if (ct.getValue() instanceof BigInteger) {
			BigInteger bi = (BigInteger) ct.getValue();
			return new IntExpr(bi);
		} else if (ct.getValue() instanceof BigDecimal) {
			BigDecimal bd = (BigDecimal) ct.getValue();
			return new RealExpr(bd);
		} else if (ct.getValue() instanceof Rational) {
			Rational r = (Rational) ct.getValue();
			return divide(real(r.numerator()), real(r.denominator()));
		} else {
			throw new IllegalArgumentException("Unhandled constant term type: " + ct.getClass().getSimpleName());
		}
	}

	private static Expr expr(ApplicationTerm at) {
		String name = at.getFunction().getName();
		Term[] params = at.getParameters();
		
		if (SexpUtil.isEncodedFunction(name)) {
			List<Expr> exprParams = Arrays.stream(params).map(p -> expr(p)).collect(toList());
			return new FunctionCallExpr(SexpUtil.decodeFunction(name), exprParams);
		}
		
		if (params.length == 0) {
			switch (name) {
			case "true":
				return new BoolExpr(true);
			case "false":
				return new BoolExpr(false);
			default:
				return new IdExpr(Lustre2Term.decode(name));
			}
		}

		if (params.length == 1) {
			switch (name) {
			case "-":
				return negative(expr(params[0]));
			case "not":
				return not(expr(params[0]));
			case "to_real":
				return castReal(expr(params[0]));
			case "to_int":
				return castInt(expr(params[0]));
			default:
				throw new IllegalArgumentException("Unknown unary operator: " + name);
			}
		}

		if (params.length == 2) {
			switch (name) {
			case "distinct":
				return notEqual(expr(params[0]), expr(params[1]));
			default:
				BinaryOp op = BinaryOp.fromString(name);
				return new BinaryExpr(expr(params[0]), op, expr(params[1]));
			}
		}

		if (params.length == 3 && name.equals("ite")) {
			return new IfThenElseExpr(expr(params[0]), expr(params[1]), expr(params[2]));
		}

		if (leftAssociative(name)) {
			return leftAssociate(name, params);
		} else if (rightAssociative(name)) {
			return rightAssociate(name, params);
		}

		throw new IllegalArgumentException("Unknown term: " + at);
	}

	private static boolean leftAssociative(String name) {
		switch (name) {
		case "and":
		case "or":
		case "xor":
		case "+":
		case "-":
		case "*":
		case "/":
		case "div":
			return true;

		default:
			return false;
		}
	}

	private static Expr leftAssociate(String name, Term[] params) {
		BinaryOp op = BinaryOp.fromString(name);

		Expr expr = expr(params[0]);
		for (int i = 1; i < params.length; i++) {
			expr = new BinaryExpr(expr, op, expr(params[i]));
		}
		return expr;
	}

	private static boolean rightAssociative(String name) {
		return name.equals("=>");
	}

	private static Expr rightAssociate(String name, Term[] params) {
		BinaryOp op = BinaryOp.fromString(name);

		Expr expr = expr(params[params.length - 1]);
		for (int i = params.length - 2; i >= 0; i--) {
			expr = new BinaryExpr(expr(params[i]), op, expr);
		}
		return expr;
	}

}
