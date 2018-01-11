package jkind.lustre.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.values.ArrayValue;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.RecordValue;
import jkind.lustre.values.TupleValue;
import jkind.lustre.values.Value;
import jkind.util.BigFraction;
import jkind.util.Util;

public abstract class Evaluator implements ExprVisitor<Value> {
	public Value eval(Expr e) {
		return e.accept(this);
	}

	public IntegerValue evalInt(Expr e) {
		return (IntegerValue) eval(e);
	}

	@Override
	public Value visit(ArrayAccessExpr e) {
		ArrayValue array = (ArrayValue) eval(e.array);
		IntegerValue index = (IntegerValue) eval(e.index);
		if (array == null || index == null) {
			return null;
		}
		return array.get(index.value);
	}

	@Override
	public Value visit(ArrayExpr e) {
		List<Value> elements = visitExprs(e.elements);
		if (elements == null) {
			return null;
		}
		return new ArrayValue(elements);
	}

	@Override
	public Value visit(ArrayUpdateExpr e) {
		ArrayValue array = (ArrayValue) eval(e.array);
		IntegerValue index = (IntegerValue) eval(e.index);
		Value value = eval(e.value);
		if (array == null || index == null || value == null) {
			return null;
		}
		return array.update(index.value, value);
	}

	@Override
	public Value visit(BinaryExpr e) {
		Value left = eval(e.left);
		Value right = eval(e.right);
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
		Value value = eval(e.expr);
		if (value == null) {
			return null;
		}
		return Util.cast(e.type, value);
	}

	@Override
	public Value visit(CondactExpr e) {
		return null;
	}

	@Override
	public Value visit(FunctionCallExpr e) {
		return null;
	}

	@Override
	public Value visit(IfThenElseExpr e) {
		BooleanValue cond = (BooleanValue) eval(e.cond);
		if (cond == null) {
			return null;
		}

		if (cond.value) {
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
		return new RealValue(BigFraction.valueOf(e.value));
	}

	@Override
	public Value visit(RecordAccessExpr e) {
		RecordValue record = (RecordValue) eval(e.record);
		if (record == null) {
			return null;
		}
		return record.fields.get(e.field);
	}

	@Override
	public Value visit(RecordExpr e) {
		Map<String, Value> fields = new HashMap<>();
		for (Entry<String, Expr> entry : e.fields.entrySet()) {
			Value value = eval(entry.getValue());
			if (value == null) {
				return null;
			}
			fields.put(entry.getKey(), value);
		}
		return new RecordValue(fields);
	}

	@Override
	public Value visit(RecordUpdateExpr e) {
		RecordValue record = (RecordValue) eval(e.record);
		Value value = eval(e.value);
		if (record == null || value == null) {
			return null;
		}
		return record.update(e.field, value);
	}

	@Override
	public Value visit(TupleExpr e) {
		List<Value> elements = visitExprs(e.elements);
		if (elements == null) {
			return null;
		}
		return new TupleValue(elements);
	}

	@Override
	public Value visit(UnaryExpr e) {
		Value value = eval(e.expr);
		if (value == null) {
			return null;
		}
		return value.applyUnaryOp(e.op);
	}

	protected List<Value> visitExprs(List<Expr> es) {
		List<Value> values = new ArrayList<>();
		for (Expr e : es) {
			Value value = eval(e);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return values;
	}
}
