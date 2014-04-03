package jkind.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.Node;
import jkind.lustre.TupleExpr;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Flatten tuple types to scalars so that all equations are single assignment.
 * 
 * Assumption: All node calls have been inlined.
 */
public class FlattenTuples extends ExprMapVisitor {
	public static Node node(Node node) {
		return visitNode(node);
	}

	private static Node visitNode(Node node) {
		List<Equation> equations = visitEquations(node.equations);
		return new Node(node.id, node.inputs, node.outputs, node.locals, equations,
				node.properties, node.assertions);
	}

	private static List<Equation> visitEquations(List<Equation> equations) {
		List<Equation> results = new ArrayList<>();
		for (Equation eq : equations) {
			results.addAll(visitEquation(eq));
		}
		return results;
	}

	private static List<Equation> visitEquation(Equation eq) {
		if (eq.lhs.size() == 1) {
			return Collections.singletonList(eq);
		}

		List<Equation> results = new ArrayList<>();
		for (int i = 0; i < eq.lhs.size(); i++) {
			results.add(new Equation(eq.lhs.get(i), eq.expr.accept(new FlattenTuples(i))));
		}
		return results;
	}

	private final int index;

	public FlattenTuples(int index) {
		this.index = index;
	}

	@Override
	public Expr visit(TupleExpr e) {
		return e.elements.get(index);
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		return new IfThenElseExpr(e.location, e.cond, e.thenExpr.accept(this),
				e.elseExpr.accept(this));
	}
}
