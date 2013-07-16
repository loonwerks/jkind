package jkind.analysis.evaluation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.Constant;
import jkind.lustre.Expr;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class ConstantEvaluator implements ExprVisitor<Value> {
	private Map<String, Value> constants;
	private Set<String> hidden;

	public ConstantEvaluator(List<Constant> constantDecls) {
		constants = new HashMap<>();
		for (Constant c : constantDecls) {
			constants.put(c.id, eval(c.expr));
		}
	}

	public void setHidden(Node node) {
		hidden = new HashSet<>();
		for (VarDecl decl : Util.getVarDecls(node)) {
			hidden.add(decl.id);
		}
	}
	
	private Value eval(Expr e) {
		try {
			return e.accept(this);
		} catch (ArithmeticException ae) {
			System.out.println("Error at line " + e.location + " division by zero");
			throw new DivideByZeroException();
		}
	}

	@Override
	public Value visit(BinaryExpr e) {
		Value left = eval(e.left);
		Value right = eval(e.right);
		if (left == null) {
			return null;
		} else {
			return left.applyBinaryOp(e.op, right);
		}
	}

	@Override
	public Value visit(BoolExpr e) {
		return BooleanValue.fromBoolean(e.value);
	}

	@Override
	public Value visit(IdExpr e) {
		if (hidden.contains(e)) {
			return null;
		} else {
			return constants.get(e.id);
		}
	}

	@Override
	public Value visit(IfThenElseExpr e) {
		Value cond = eval(e);
		if (!(cond instanceof BooleanValue)) {
			return null;
		}
		boolean condValue = ((BooleanValue) cond).value;

		if (condValue) {
			return eval(e.thenExpr);
		} else {
			return eval(e.elseExpr);
		}
	}

	@Override
	public Value visit(IntExpr e) {
		return new IntegerValue(e.value);
	}

	@Override
	public Value visit(NodeCallExpr e) {
		return null;
	}
	
	@Override
	public Value visit(RealExpr e) {
		return new RealValue(e.value);
	}

	@Override
	public Value visit(UnaryExpr e) {
		Value value = eval(e.expr);
		if (value == null) {
			return null;
		} else {
			return value.applyUnaryOp(e.op);
		}
	}
}
