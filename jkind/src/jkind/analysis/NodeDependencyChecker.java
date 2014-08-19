package jkind.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.Output;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.AstIterVisitor;
import jkind.util.CycleFinder;

public class NodeDependencyChecker {
	public static boolean check(Program program) {
		return new NodeDependencyChecker().check(program.nodes);
	}

	protected boolean check(List<Node> nodes) {
		Map<String, Set<String>> dependencies = new HashMap<>();
		for (Node node : nodes) {
			dependencies.put(node.id, getNodeDependencies(node));
		}

		List<String> cycle = CycleFinder.findCycle(dependencies);
		if (cycle != null) {
			Output.error("cyclic node calls: " + cycle);
			return false;
		}
		return true;
	}

	private static Set<String> getNodeDependencies(Node node) {
		final Set<String> dependencies = new HashSet<>();
		node.accept(new AstIterVisitor() {
			@Override
			public Void visit(NodeCallExpr e) {
				dependencies.add(e.node);
				super.visit(e);
				return null;
			}
		});
		return dependencies;
	}
}
