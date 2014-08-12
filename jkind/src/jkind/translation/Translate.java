package jkind.translation;

import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.translation.compound.FlattenCompoundTypes;
import jkind.translation.tuples.FlattenTuples;

public class Translate {
	public static Node translate(Program program) {
		program = InlineEnumValues.program(program);
		program = InlineUserTypes.program(program);
		program = InlineConstants.program(program);
		program = RemoveCondacts.program(program);
		Node main = InlineNodeCalls.program(program);
		main = FlattenTuples.node(main);
		main = FlattenCompoundTypes.node(main);
		main = FlattenPres.node(main);
		return main;
	}
}
