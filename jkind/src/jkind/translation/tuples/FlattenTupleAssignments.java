package jkind.translation.tuples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.lustre.Equation;
import jkind.lustre.InlinedProgram;
import jkind.lustre.Node;
import jkind.lustre.TupleExpr;

/**
 * Expand tuple assignments into single value assignments
 * 
 * Assumption: All tuple expressions have been lifted as far as possible.
 */

public class FlattenTupleAssignments {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		return new InlinedProgram(ip.functions, visitNode(ip.node));
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
		TupleExpr tuple = (TupleExpr) eq.expr;
		for (int i = 0; i < eq.lhs.size(); i++) {
			results.add(new Equation(eq.lhs.get(i), tuple.elements.get(i)));
		}
		return results;
	}
}
