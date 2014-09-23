package jkind.slicing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.ExprVisitor;
import jkind.solvers.Model;
import jkind.solvers.ModelFunction;
import jkind.util.BigFraction;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;
import jkind.util.Util;

public class ModelSlicer implements ExprVisitor<Value> {
	public static Model slice(Model original, Node node, String property, int k) {
		return new ModelSlicer(original, node).slice(property, k);
	}

	private Model original;
	private Map<String, Expr> equations = new HashMap<>();
	private List<Expr> assertions;
	private int k;
	private SimpleModel sliced = new SimpleModel();
	private Set<StreamIndex> visited = new HashSet<>();

	private ModelSlicer(Model original, Node node) {
		this.original = original;
		for (Equation eq : node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}
		this.assertions = node.assertions;
	}

	public Model slice(String property, int k) {
		this.k = k - 1;

		new IdExpr(property).accept(this);

		for (int i = 0; i < k; i++) {
			this.k = i;
			for (Expr assertion : assertions) {
				assertion.accept(this);
			}
		}

		return sliced;
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
		if (e.op == BinaryOp.ARROW) {
			if (k == 0) {
				return e.left.accept(this);
			} else {
				return e.right.accept(this);
			}
		}

		return e.left.accept(this).applyBinaryOp(e.op, e.right.accept(this));
	}

	@Override
	public Value visit(BoolExpr e) {
		return BooleanValue.fromBoolean(e.value);
	}

	@Override
	public Value visit(CastExpr e) {
		return Util.cast(e.type, e.expr.accept(this));
	}

	@Override
	public Value visit(CondactExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Value visit(FunctionCallExpr e) {
		List<Value> args = visitExprs(e.args);
		String enc = SexpUtil.encodeFunction(e.name).str;
		Value value = original.getFunction(enc).getValue(args);
		ModelFunction fn = sliced.getFunction(enc);
		if (fn == null) {
			fn = new ModelFunction();
			sliced.addFunction(enc, fn);
		}
		fn.addValue(args, value);
		return value;
	}

	@Override
	public Value visit(IdExpr e) {
		StreamIndex si = new StreamIndex(e.id, k);

		Value value = sliced.getValue(si);
		if (value != null) {
			return value;
		}

		if (equations.containsKey(e.id) && !visited.contains(si) && k >= 0) {
			visited.add(si);
			value = equations.get(e.id).accept(this);
		} else {
			value = original.getValue(si);
		}

		sliced.addValue(si, value);
		return value;
	}

	@Override
	public Value visit(IfThenElseExpr e) {
		BooleanValue cond = (BooleanValue) e.cond.accept(this);
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
		if (e.op == UnaryOp.PRE) {
			k--;
			Value value = e.expr.accept(this);
			k++;
			return value;
		} else {
			return e.expr.accept(this).applyUnaryOp(e.op);
		}
	}

	private List<Value> visitExprs(List<Expr> exprs) {
		List<Value> values = new ArrayList<>();
		for (Expr expr : exprs) {
			values.add(expr.accept(this));
		}
		return values;
	}
}
