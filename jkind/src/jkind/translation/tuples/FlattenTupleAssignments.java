package jkind.translation.tuples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.lustre.Equation;
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
	protected List<Equation> visitEquations(List<Equation> es) {
		List<Equation> results = new ArrayList<>();
		for (Equation e : es) {
			results.addAll(visitEquation(e));
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
