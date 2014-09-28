package jkind.slicing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import jkind.lustre.values.Value;
import jkind.solvers.Model;
import jkind.solvers.ModelFunction;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;

public class ModelSlicer extends Evaluator {
	public static Model slice(Model original, Node node, DependencyMap dependencyMap,
			String property, int k) {
		return new ModelSlicer(original, node, dependencyMap).slice(property, k);
	}

	private final Model original;
	private final Map<String, Expr> equations = new HashMap<>();
	private final List<Expr> assertions;
	private final DependencyMap dependencyMap;

	private int k;
	private final SimpleModel sliced = new SimpleModel();
	private final Set<StreamIndex> visited = new HashSet<>();

	private ModelSlicer(Model original, Node node, DependencyMap dependencyMap) {
		this.original = original;
		for (Equation eq : node.equations) {
			equations.put(eq.lhs.get(0).id, eq.expr);
		}
		this.assertions = node.assertions;
		this.dependencyMap = dependencyMap;
	}

	public Model slice(String property, int k) {
		this.k = k - 1;

		new IdExpr(property).accept(this);

		for (int i = 0; i < k; i++) {
			this.k = i;
			for (Expr assertion : assertions) {
				if (assertionRelevant(assertion, property)) {
					assertion.accept(this);
				}
			}
		}

		return sliced;
	}

	private boolean assertionRelevant(Expr assertion, String property) {
		DependencySet propertyDependencies = dependencyMap.get(Dependency.variable(property));
		DependencySet assertionDependencies = DependencyVisitor.get(assertion);
		return !assertionDependencies.isEmpty()
				&& propertyDependencies.contains(assertionDependencies.iterator().next());
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

		return super.visit(e);
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
