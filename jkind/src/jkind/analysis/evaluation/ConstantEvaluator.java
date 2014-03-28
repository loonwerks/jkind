package jkind.analysis.evaluation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.Expr;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.ProjectionExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.RecordValue;
import jkind.lustre.values.Value;
import jkind.util.BigFraction;
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
			throw new DivisionException();
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
	public Value visit(CastExpr e) {
		Value value = eval(e.expr);
		if (e.type == NamedType.REAL && value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			return new RealValue(new BigFraction(iv.value));
		} else if (e.type == NamedType.INT && value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			return new IntegerValue(rv.value.floor());
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Value visit(CondactExpr e) {
		return null;
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
	public Value visit(ProjectionExpr e) {
		Value value = e.expr.accept(this);
		if (value instanceof RecordValue) {
			RecordValue recordValue = (RecordValue) value;
			return recordValue.fields.get(e.field);
		} else {
			return null;
		}
	}

	@Override
	public Value visit(RealExpr e) {
		return new RealValue(new BigFraction(e.value));
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

	@Override
	public Value visit(RecordExpr e) {
		Map<String, Value> fields = new HashMap<>();
		for (Entry<String, Expr> entry : e.fields.entrySet()) {
			fields.put(entry.getKey(), entry.getValue().accept(this));
		}
		return new RecordValue(fields);
	}
}
