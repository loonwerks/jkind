package jkind.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.Output;
import jkind.lustre.Constant;
import jkind.lustre.Program;
import jkind.util.CycleFinder;

public class ConstantDependencyChecker {
	public static boolean check(Program program) {
		return new ConstantDependencyChecker().check(program.constants);
	}

	protected boolean check(List<Constant> constants) {
		Map<String, Set<String>> dependencies = new HashMap<>();
		for (Constant c : constants) {
			dependencies.put(c.id, IdExtractorVisitor.getIds(c.expr));
		}

		List<String> cycle = CycleFinder.findCycle(dependencies);
		if (cycle != null) {
			Output.error("cyclic constants: " + cycle);
			return false;
		}
		return true;
	}
}
