package jkind.slicing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.analysis.evaluation.Evaluator;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;
import jkind.solvers.Function;
import jkind.solvers.Model;
import jkind.solvers.SimpleFunction;
import jkind.solvers.SimpleModel;
import jkind.translation.Lustre2Sexp;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;

public class ModelSlicer extends Evaluator {
	public static SimpleModel slice(Model original, Node node, DependencyMap dependencyMap,
			String property, int k) {
		return new ModelSlicer(original, node, dependencyMap).slice(property, k);
	}

	private final Model original;
	private final Map<String, Expr> equations = new HashMap<>();
	private final List<Expr> assertions;
	private final DependencyMap dependencyMap;

	private int k;
	private final SimpleModel sliced = new SimpleModel();

	private ModelSlicer(Model original, Node node, DependencyMap dependencyMap) {
		this.original = original;
		for (Equation eq : node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}
		this.assertions = node.assertions;
		this.dependencyMap = dependencyMap;
	}

	private SimpleModel slice(String property, int k) {
		DependencySet keep = dependencyMap.get(Dependency.variable(property));
		sliceVariables(keep);
		sliceFunctions(keep, k);
		return sliced;
	}

	private void sliceVariables(DependencySet keep) {
		for (String var : original.getVariableNames()) {
			StreamIndex si = StreamIndex.decode(var);
			if (si != null && keep.contains(Dependency.variable(si.getStream()))) {
				sliced.addValue(si, original.getValue(var));
			}
		}
	}

	private void sliceFunctions(DependencySet keep, int k) {
		evalEquations(keep, k);
		evalAssertions(keep, k);
	}

	private void evalEquations(DependencySet keep, int k) {
		for (Dependency dep : keep.getSet()) {
			if (dep.type == DependencyType.VARIABLE && equations.containsKey(dep.name)) {
				Expr expr = equations.get(dep.name);
				for (int i = 0; i < k; i++) {
					eval(expr, i);
				}
			}
		}
	}

	private void evalAssertions(DependencySet keep, int k) {
		for (Expr assertion : assertions) {
			if (assertionRelevant(assertion, keep)) {
				for (int i = 0; i < k; i++) {
					eval(assertion, i);
				}
			}
		}
	}

	private boolean assertionRelevant(Expr assertion, DependencySet keep) {
		DependencySet assertionDependencies = DependencyVisitor.get(assertion);
		return !assertionDependencies.isEmpty() && keep.contains(assertionDependencies.first());
	}

	private void eval(Expr expr, int k) {
		this.k = k;
		expr.accept(this);
	}

	@Override
	public Value visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			if (k == 0 && initialCounterexample()) {
				return e.left.accept(this);
			} else {
				return e.right.accept(this);
			}
		}

		return super.visit(e);
	}

	private boolean initialCounterexample() {
		BooleanValue init = (BooleanValue) original.getValue(Lustre2Sexp.INIT.str);
		return init.value;
	}

	@Override
	public Value visit(FunctionCallExpr e) {
		List<Value> args = visitExprs(e.args);
		String enc = SexpUtil.encodeFunction(e.name).str;
		Function originalFn = original.getFunction(enc);
		Value value = originalFn == null ? null : originalFn.getValue(args);
		SimpleFunction fn = sliced.getFunction(enc);
		if (fn == null) {
			fn = new SimpleFunction();
			sliced.addFunction(enc, fn);
		}
		fn.addValue(args, value);
		return value;
	}

	@Override
	public Value visit(IdExpr e) {
		return sliced.getValue(new StreamIndex(e.id, k));
	}

	@Override
	public Value visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			k--;
			Value value = e.expr.accept(this);
			k++;
			return value;
		} else {
			return super.visit(e);
		}
	}
}
