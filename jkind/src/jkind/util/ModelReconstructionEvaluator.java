package jkind.util;

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
import jkind.solvers.SimpleModel;
import jkind.translation.Specification;

public class ModelReconstructionEvaluator extends Evaluator {
	public static void reconstruct(Specification spec, SimpleModel model, String property, int k,
			boolean concrete) {
		new ModelReconstructionEvaluator(spec, model, concrete).reconstructValues(property, k);
	}

	private final Specification spec;
	private final SimpleModel model;
	private final boolean concrete;

	private final Map<String, Expr> equations = new HashMap<>();

	private int step;

	private ModelReconstructionEvaluator(Specification spec, SimpleModel model, boolean concrete) {
		this.spec = spec;
		this.model = model;
		this.concrete = concrete;

		for (Equation eq : spec.node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}
	}

	private void reconstructValues(String property, int k) {
		DependencySet dependencies = spec.dependencyMap.get(property);
		for (step = 0; step < k; step++) {
			for (Dependency dependency : dependencies) {
				eval(new IdExpr(dependency.name));
			}
		}
	}

	@Override
	public Value visit(IdExpr e) {
		StreamIndex si = new StreamIndex(e.id, step);

		Value value = model.getValue(si);
		if (value != null) {
			return value;
		}

		if (step < 0) {
			return getDefaultValue(si);
		}

		Expr expr = equations.get(e.id);
		if (expr == null) {
			return getDefaultValue(si);
		}

		value = eval(expr);
		model.putValue(si, value);
		return value;
	}

	private Value getDefaultValue(StreamIndex si) {
		return Util.getDefaultValue(spec.typeMap.get(si.getStream()));
	}

	@Override
	public Value visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			if (!concrete) {
				// Inductive counterexamples never reach the true initial step
				return e.right.accept(this);
			}

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
