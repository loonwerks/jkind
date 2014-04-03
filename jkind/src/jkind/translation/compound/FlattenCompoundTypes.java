package jkind.translation.compound;

import jkind.lustre.InlinedProgram;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Flatten arrays and records to scalars
 * 
 * Assumption: All node calls have been inlined.
 */
public class FlattenCompoundTypes extends ExprMapVisitor {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		ip = RemoveNonConstantArrayIndices.inlinedProgram(ip);
		ip = FlattenCompoundComparisons.inlinedProgram(ip);
		ip = FlattenCompoundVariables.inlinedProgram(ip);
		ip = FlattenCompoundFunctionOutputs.inlinedProgram(ip);
		ip = FlattenCompoundFunctionInputs.inlinedProgram(ip);
		ip = FlattenCompoundExpressions.inlinedProgram(ip);
		return ip;
	}
}
