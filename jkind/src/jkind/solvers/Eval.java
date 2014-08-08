package jkind.solvers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.util.BigFraction;
import jkind.util.Util;
import jkind.lustre.BinaryOp;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;

public class Eval {
	private Model model;

	public Eval(Model model) {
		this.model = model;
	}

	public Value eval(Sexp sexp) {
		if (sexp instanceof Symbol) {
			return evalSymbol(((Symbol) sexp).sym);
		} else if (sexp instanceof Cons) {
			return evalCons((Cons) sexp);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Value evalSymbol(String sym) {
		if (sym.equals("true")) {
			return BooleanValue.TRUE;
		} else if (sym.equals("false")) {
			return BooleanValue.FALSE;
		} else if (sym.contains("/")) {
			return Util.parseValue("real", sym);
		} else {
			return Util.parseValue("int", sym);
		}
	}

	private Value evalCons(Cons sexp) {
		String fn = ((Symbol) sexp.head).sym;
		if (fn.equals("ite")) {
			return isTrue(eval(sexp.args.get(0))) ? eval(sexp.args.get(1)) : eval(sexp.args.get(2));
		} else if (fn.equals("and")) {
			for (Sexp arg : sexp.args) {
				if (!isTrue(eval(arg))) {
					return BooleanValue.FALSE;
				}
			}
			return BooleanValue.TRUE;
		}

		List<Value> args = new ArrayList<>();
		for (Sexp arg : sexp.args) {
			args.add(eval(arg));
		}
		return evalFunction(fn, args);
	}

	private boolean isTrue(Value v) {
		return v == BooleanValue.TRUE;
	}

	private Value evalFunction(String fn, List<Value> args) {
		switch (fn) {
		case "=":
			return checkEquality(args.get(0), args.get(1));
		case "-": {
			if (args.size() == 1) {
				return args.get(0).applyUnaryOp(UnaryOp.NEGATIVE);
			} else {
				return applyBinaryOp(args.get(0), BinaryOp.MINUS, args.get(1));
			}
		}
		case "/":
			return applyBinaryOp(args.get(0), BinaryOp.DIVIDE, args.get(1));
		case "<=":
			return applyBinaryOp(args.get(0), BinaryOp.LESSEQUAL, args.get(1));
		case ">=":
			return applyBinaryOp(args.get(0), BinaryOp.GREATEREQUAL, args.get(1));
		case "<":
			return applyBinaryOp(args.get(0), BinaryOp.LESS, args.get(1));
		case ">":
			return applyBinaryOp(args.get(0), BinaryOp.GREATER, args.get(1));
		case "not":
			return args.get(0).applyUnaryOp(UnaryOp.NOT);
		default:
			BigInteger index = new BigInteger(args.get(0).toString());
			return model.getFunctionValue(fn, index);
		}
	}

	private Value checkEquality(Value left, Value right) {
		if (left instanceof RealValue && right instanceof IntegerValue) {
			right = promote(right);
		} else if (left instanceof IntegerValue && right instanceof RealValue) {
			left = promote(left);
		}
		return BooleanValue.fromBoolean(left.equals(right));
	}

	private Value applyBinaryOp(Value left, BinaryOp op, Value right) {
		if (op == BinaryOp.DIVIDE) {
			left = promote(left);
			right = promote(right);
		} else if (left instanceof RealValue && right instanceof IntegerValue) {
			right = promote(right);
		} else if (left instanceof IntegerValue && right instanceof RealValue) {
			left = promote(left);
		}
		return left.applyBinaryOp(op, right);
	}

	private RealValue promote(Value value) {
		if (value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			return new RealValue(new BigFraction(iv.value));
		} else if (value instanceof RealValue) {
			return (RealValue) value;
		} else {
			throw new IllegalArgumentException();
		}
	}
}