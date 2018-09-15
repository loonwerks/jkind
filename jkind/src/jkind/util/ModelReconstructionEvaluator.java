package jkind.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.JKindException;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.Evaluator;
import jkind.slicing.Dependency;
import jkind.slicing.DependencySet;
import jkind.slicing.DependencyType;
import jkind.slicing.DependencyVisitor;
import jkind.solvers.Model;
import jkind.solvers.SimpleModel;
import jkind.translation.Specification;

public class ModelReconstructionEvaluator extends Evaluator {
	public static Model reconstruct(Specification userSpec, Specification analysisSpec, Model model, String property,
			int k, boolean concrete) {
		Set<String> inlinedVariables = Util.setDifference(userSpec.typeMap.keySet(), analysisSpec.typeMap.keySet());
		ModelReconstructionEvaluator eval = new ModelReconstructionEvaluator(userSpec, inlinedVariables, model,
				concrete);
		eval.reconstructValues(property, k);
		return eval.model;
	}

	private final Specification spec;
	private final Model originalModel;
	private final SimpleModel model;
	private final boolean concrete;
	private final Set<String> inlinedVariables;

	private final Map<String, Expr> equations = new HashMap<>();
	private final Set<StreamIndex> evaluating = new HashSet<>();

	private int step;

	private ModelReconstructionEvaluator(Specification userSpec, Set<String> inlinedVariables, Model originalModel,
			boolean concrete) {
		this.spec = userSpec;
		this.inlinedVariables = inlinedVariables;
		this.originalModel = originalModel;
		this.model = new SimpleModel(userSpec.functions);
		this.concrete = concrete;

		for (Equation eq : userSpec.node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}
	}

	private void reconstructValues(String property, int k) {
		DependencySet dependencies = spec.dependencyMap.get(property);
		for (step = 0; step < k; step++) {
			for (Dependency dependency : dependencies) {
				if (dependency.type == DependencyType.VARIABLE) {
					try {
						Value v1 = eval(new IdExpr(dependency.name));

						// Compare computed value (v1) with solver value (v2) when possible
						if (!inlinedVariables.contains(dependency.name)) {
							Value v2 = originalModel.getValue(new StreamIndex(dependency.name, step));
							if (v2 != null && !v1.equals(v2)) {
								throw new JKindException(
										"Unable to reconstruct counterexample: variable value changed: "
												+ dependency.name);
							}
						}
					} catch (ArithmeticException ae) {
						// Division by zero, ignore
					}
				}
			}

			for (Expr assertion : spec.node.assertions) {
				DependencySet assertionDependencies = DependencyVisitor.get(assertion);
				if (dependencies.containsAll(assertionDependencies)) {
					BooleanValue bv = (BooleanValue) eval(assertion);
					if (!bv.value) {
						throw new JKindException("Unable to reconstruct counterexample: assertion became false");
					}
				}
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

		Expr expr = equations.get(e.id);
		if (expr == null) {
			// Input variable
			value = originalModel.getValue(si);
		} else {
			// Equation variable
			if (inlinedVariables.contains(e.id)) {
				// Inlined variables are always evaluated, ignoring any
				// restrictions on loops since the model knows nothing about
				// such variables
				value = eval(expr);
			} else if (step >= 0 && !evaluating.contains(si)) {
				evaluating.add(si);
				value = eval(expr);
				evaluating.remove(si);
			} else {
				value = originalModel.getValue(si);
			}
		}

		if (value == null) {
			throw new JKindException("Unable to reconstruct counterexample: variable has null value");
		}
		model.putValue(si, value);
		return value;
	}

	@Override
	public Value visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			if (!concrete) {
				// Inductive counterexamples never reach the true initial step
				return eval(e.right);
			}

			if (step == 0) {
				return eval(e.left);
			} else {
				return eval(e.right);
			}
		} else {
			return super.visit(e);
		}
	}

	@Override
	public Value visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			step--;
			Value value = eval(e.expr);
			step++;
			return value;
		} else {
			return super.visit(e);
		}
	}

	@Override
	public Value visit(FunctionCallExpr e) {
		String name = SexpUtil.encodeFunction(e.function);
		List<Value> inputs = visitExprs(e.args);

		Value output = model.evaluateFunction(name, inputs);
		if (output == null) {
			output = originalModel.evaluateFunction(name, inputs);
			if (output == null) {
				Type outputType = model.getFunctionTable(name).getOutput().type;
				output = Util.getDefaultValue(outputType);
			}

			model.getFunctionTable(name).addRow(inputs, output);
		}

		return output;
	}
}
