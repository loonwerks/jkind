package jkind.translation.tuples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TupleExpr;
import jkind.lustre.visitors.AstMapVisitor;

/**
 * Expand tuple assignments into single value assignments
 * 
 * Assumption: All tuple expressions have been lifted as far as possible.
 */

public class FlattenTupleAssignments extends AstMapVisitor {
	public static Program program(Program program) {
		return new FlattenTupleAssignments().visit(program);
	}

	@Override
	public Node visit(Node node) {
		List<Equation> equations = visitEquations(node.equations);
		return new Node(node.id, node.inputs, node.outputs, node.locals, equations,
				node.properties, node.assertions);
	}

	private List<Equation> visitEquations(List<Equation> equations) {
		List<Equation> results = new ArrayList<>();
		for (Equation eq : equations) {
			results.addAll(visitEquation(eq));
		}
		return results;
	}

	private List<Equation> visitEquation(Equation eq) {
		if (eq.lhs.size() == 1) {
			return Collections.singletonList(eq);
		}

		List<Equation> results = new ArrayList<>();
		TupleExpr tuple = (TupleExpr) eq.expr;
		for (int i = 0; i < eq.lhs.size(); i++) {
			results.add(new Equation(eq.lhs.get(i), tuple.elements.get(i)));
		}
		return results;
	}
}
