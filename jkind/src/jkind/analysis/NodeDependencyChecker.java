package jkind.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.StdErr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.util.CycleFinder;
import jkind.util.Util;

public class NodeDependencyChecker {
	public static boolean check(Program program) {
		return new NodeDependencyChecker().check(program.nodes);
	}

	protected boolean check(List<Node> nodes) {
		Map<String, Set<String>> dependencies = new HashMap<>();
		for (Node node : nodes) {
			dependencies.put(node.id, Util.getNodeDependenciesByName(node));
		}

		List<String> cycle = CycleFinder.findCycle(dependencies);
		if (cycle != null) {
			StdErr.error("cyclic node calls: " + cycle);
			return false;
		}
		return true;
	}
}
