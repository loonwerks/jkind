package jkind.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import jkind.analysis.evaluation.Evaluator;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.Value;
import jkind.slicing.Dependency;
import jkind.slicing.DependencySet;
import jkind.solvers.Model;
import jkind.solvers.SimpleModel;
import jkind.translation.Specification;

public class ModelReconstructionEvaluator extends Evaluator {
	public static Model reconstruct(Specification spec, Model model, String property, int k) {
		return new ModelReconstructionEvaluator(spec, model).reconstructValues(property, k);
	}

	private final Specification spec;
	private final Model original;
	private final SimpleModel full;

	private final Map<String, Expr> equations = new HashMap<>();
	private final Deque<StreamIndex> stack = new ArrayDeque<>();

	private int step;

	private ModelReconstructionEvaluator(Specification spec, Model original) {
		this.spec = spec;
		this.original = original;
		this.full = new SimpleModel();

		for (Equation eq : spec.node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}
	}

	private Model reconstructValues(String property, int k) {
		DependencySet dependencies = spec.dependencyMap.get(property);
		for (step = 0; step < k; step++) {
			for (Dependency dependency : dependencies) {
				eval(new IdExpr(dependency.name));
			}
		}
		return full;
	}

	@Override
	public Value visit(IdExpr e) {
		StreamIndex si = new StreamIndex(e.id, step);

		Value value = full.getValue(si);
		if (value != null) {
			return value;
		}

		if (stack.contains(si) || step < 0) {
			return original.getValue(si);
		}
		stack.push(si);

		Expr expr = equations.get(e.id);
		if (expr == null) {
			value = getOrComputeOriginalValue(si);
		} else {
			Value prev = original.getValue(si);
			value = eval(expr);
			if (value == null) {
				value = prev;
			}

			if (prev != null && value != null && !prev.equals(value)) {
				throw new IllegalStateException("Internal JKind error: evaluation did not match model");
			}
		}
		full.putValue(si, value);

		stack.pop();
		return value;
	}

	private Value getOrComputeOriginalValue(StreamIndex si) {
		Value value = original.getValue(si);
		if (value != null) {
			return value;
		}

		return Util.getDefaultValue(spec.typeMap.get(si.getStream()));
	}

	@Override
	public Value visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			if (step == 0) {
				return e.left.accept(this);
			} else {
				return e.right.accept(this);
			}
		} else {
			return super.visit(e);
		}
	}

	@Override
	public Value visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			step--;
			Value value = e.expr.accept(this);
			step++;
			return value;
		} else {
			return super.visit(e);
		}
	}
}
