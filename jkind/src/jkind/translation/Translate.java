package jkind.translation;

import jkind.lustre.InlinedProgram;
import jkind.lustre.Program;
import jkind.translation.compound.FlattenCompoundTypes;
import jkind.translation.tuples.FlattenTuples;

public class Translate {
	public static InlinedProgram translate(Program program) {
		program = InlineUserTypes.program(program);
		program = InlineConstants.program(program);
		program = RemoveCondacts.program(program);
		InlinedProgram ip = InlineNodeCalls.program(program);
		ip = SplitFunctions.inlinedProgram(ip);
		ip = FlattenTuples.inlinedProgram(ip);
		ip = FlattenCompoundTypes.inlinedProgram(ip);
		return ip;
	}
}
