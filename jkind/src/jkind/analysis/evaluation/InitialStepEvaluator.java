package jkind.analysis.evaluation;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.BigFraction;

/**
 * This class is used by invariant generation to suggest upper and lower bounds
 * on states variables. It assumes that all transformations have been performed
 * and the Lustre is in a simple format.
 */
public class InitialStepEvaluator implements ExprVisitor<Value> {
	private Map<String, Value> values = new HashMap<>();
	private ArrayDeque<String> evalStack = new ArrayDeque<>();
	private Map<String, Expr> equations = new HashMap<>();

	public InitialStepEvaluator(Node node) {
		for (VarDecl input : node.inputs) {
			values.put(input.id, null);
		}
		
		for (Equation eq : node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}
	}

	public Value eval(String id) {
		if (values.containsKey(id)) {
			return values.get(id);
		} else if (evalStack.contains(id)) {
			return null;
		}

		evalStack.push(id);
		Value value = equations.get(id).accept(this);
		values.put(id, value);
		evalStack.pop();

		return value;
	}

	@Override
	public Value visit(ArrayAccessExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(ArrayExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(ArrayUpdateExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(BinaryExpr e) {
		Value left = e.left.accept(this);
		if (e.op == BinaryOp.ARROW) {
			return left;
		}

		Value right = e.right.accept(this);
		if (left == null || right == null) {
			return null;
		}
		return left.applyBinaryOp(e.op, right);
	}

	@Override
	public Value visit(BoolExpr e) {
		return BooleanValue.fromBoolean(e.value);
	}

	@Override
	public Value visit(CastExpr e) {
		Value value = e.expr.accept(this);
		if (value == null) {
			return null;
		}

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
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(IdExpr e) {
		return eval(e.id);
	}

	@Override
	public Value visit(IfThenElseExpr e) {
		BooleanValue cond = (BooleanValue) e.cond.accept(this);
		if (cond == null) {
			return null;
		}

		if (cond.value) {
			return e.thenExpr.accept(this);
		} else {
			return e.elseExpr.accept(this);
		}
	}

	@Override
	public Value visit(IntExpr e) {
		return new IntegerValue(e.value);
	}

	@Override
	public Value visit(NodeCallExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(RealExpr e) {
		return new RealValue(new BigFraction(e.value));
	}

	@Override
	public Value visit(RecordAccessExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(RecordExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(RecordUpdateExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(TupleExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(UnaryExpr e) {
		Value value = e.expr.accept(this);
		if (value == null || e.op == UnaryOp.PRE) {
			return null;
		} else {
			return value.applyUnaryOp(e.op);
		}
	}
}
