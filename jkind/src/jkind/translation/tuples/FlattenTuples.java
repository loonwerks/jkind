package jkind.translation.tuples;

import jkind.lustre.Program;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Flatten all tuple expressions via expansion.
 * 
 * Assumption: All node calls have been inlined.
 */
public class FlattenTuples extends ExprMapVisitor {
	public static Program program(Program program) {
		program = LiftTuples.program(program);
		program = FlattenTupleComparisons.program(program);
		program = FlattenTupleAssignments.program(program);
		return program;
	}
}
