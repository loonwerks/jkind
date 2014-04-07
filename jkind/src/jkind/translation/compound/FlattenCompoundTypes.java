package jkind.translation.compound;

import jkind.lustre.Program;

/**
 * Flatten arrays and records to scalars
 * 
 * Assumption: All node calls have been inlined.
<<<<<<< HEAD
 */
public class FlattenCompoundTypes {
	public static Program program(Program program) {
		program = RemoveNonConstantArrayIndices.program(program);
		program = RemoveArrayUpdates.program(program);
		program = RemoveRecordUpdates.program(program);
		program = FlattenCompoundComparisons.program(program);
		program = FlattenCompoundVariables.program(program);
		program = FlattenCompoundFunctionOutputs.program(program);
		program = FlattenCompoundFunctionInputs.program(program);
		program = FlattenCompoundExpressions.program(program);
		return program;
	}
}
