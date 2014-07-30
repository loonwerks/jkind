package jkind.analysis.evaluation;

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
import jkind.lustre.Constant;
import jkind.lustre.EnumType;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.ArrayValue;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.RecordValue;
import jkind.lustre.values.TupleValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.BigFraction;
import jkind.util.Util;

public class ConstantEvaluator implements ExprVisitor<Value> {
	private final Map<String, Value> constants = new HashMap<>();
	private final Map<String, Expr> constantDefinitions = new HashMap<>();

	public ConstantEvaluator() {
	}

	public ConstantEvaluator(Program program) {
		for (EnumType et : Util.getEnumTypes(program.types)) {
			for (String id : et.values) {
				constants.put(id, new EnumValue(id));
			}
		}
		
		for (Constant c : program.constants) {
			constantDefinitions.put(c.id, c.expr);
		}
		
		for (Constant c : program.constants) {
			addConstant(c);
		}
	}

	public Value addConstant(Constant c) {
		return constants.put(c.id, c.expr.accept(this));
	}

	public boolean containsConstant(String id) {
		return constants.containsKey(id);
	}

	@Override
	public Value visit(ArrayAccessExpr e) {
		ArrayValue array = (ArrayValue) e.array.accept(this);
		IntegerValue index = (IntegerValue) e.index.accept(this);
		if (array == null || index == null) {
			return null;
		}
		return array.elements.get(index.value.intValue());
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
		ArrayValue array = (ArrayValue) e.array.accept(this);
		IntegerValue index = (IntegerValue) e.index.accept(this);
		Value value = e.value.accept(this);
		if (array == null || index == null || value == null) {
			return null;
		}
		return array.update(index.value.intValue(), value);
	}

	@Override
	public Value visit(BinaryExpr e) {
		Value left = e.left.accept(this);
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
		return null;
	}

	@Override
	public Value visit(IdExpr e) {
		if (constants.containsKey(e.id)) {
			return constants.get(e.id);
		} else if (constantDefinitions.containsKey(e.id)) {
			return constantDefinitions.get(e.id).accept(this);
		} else {
			return null;
		}
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
		return null;
	}

	@Override
	public Value visit(RealExpr e) {
		return new RealValue(new BigFraction(e.value));
	}

	@Override
	public Value visit(RecordAccessExpr e) {
		RecordValue record = (RecordValue) e.record.accept(this);
		if (record == null) {
			return null;
		}
		return record.fields.get(e.field);
	}

	@Override
	public Value visit(RecordExpr e) {
		Map<String, Value> fields = new HashMap<>();
		for (Entry<String, Expr> entry : e.fields.entrySet()) {
			Value value = entry.getValue().accept(this);
			if (value == null) {
				return null;
			}
			fields.put(entry.getKey(), value);
		}
		return new RecordValue(fields);
	}

	@Override
	public Value visit(RecordUpdateExpr e) {
		RecordValue record = (RecordValue) e.record.accept(this);
		Value value = e.value.accept(this);
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
		Value value = e.expr.accept(this);
		if (value == null) {
			return null;
		} else if (e.op == UnaryOp.PRE) {
			return value;
		} else {
			return value.applyUnaryOp(e.op);
		}
	}

	private List<Value> visitExprs(List<Expr> es) {
		List<Value> values = new ArrayList<>();
		for (Expr e : es) {
			Value value = e.accept(this);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return values;
	}
}
