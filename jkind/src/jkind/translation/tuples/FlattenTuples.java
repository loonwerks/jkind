package jkind.translation.tuples;

import jkind.lustre.InlinedProgram;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Flatten all tuple expressions via expansion.
 * 
 * Assumption: All node calls have been inlined.
 */
public class FlattenTuples extends ExprMapVisitor {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		ip = LiftTuples.inlinedProgram(ip);
		ip = FlattenTupleComparisons.inlinedProgram(ip);
		ip = FlattenTupleAssignments.inlinedProgram(ip);
		return ip;
	}
}
