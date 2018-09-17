package jkind.analysis.evaluation;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.Evaluator;

/**
 * This class is used by invariant generation to suggest upper and lower bounds
 * on states variables. It assumes that all transformations have been performed
 * and the Lustre is in a simple format.
 */
public class InitialStepEvaluator extends Evaluator {
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
	public Value visit(BinaryExpr e) {
		if (e.op == BinaryOp.ARROW) {
			return e.left.accept(this);
		} else {
			return super.visit(e);
		}
	}

	@Override
	public Value visit(IdExpr e) {
		return eval(e.id);
	}

	@Override
	public Value visit(UnaryExpr e) {
		if (e.op == UnaryOp.PRE) {
			return null;
		} else {
			return super.visit(e);
		}
	}
}
