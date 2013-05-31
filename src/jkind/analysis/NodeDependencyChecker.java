package jkind.analysis;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.IterVisitor;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;

public class NodeDependencyChecker {
	public static boolean check(Program program) {
		Map<String, Set<String>> dependencies = new HashMap<String, Set<String>>();
		for (Node node : program.nodes) {
			dependencies.put(node.id, getNodeDependencies(node));
		}

		return new NodeDependencyChecker(dependencies).check();
	}

	private static Set<String> getNodeDependencies(Node node) {
		final Set<String> dependencies = new HashSet<String>();
		IterVisitor nodeCallCollector = new IterVisitor() {
			@Override
			public Void visit(NodeCallExpr e) {
				dependencies.add(e.node);
				super.visit(e);
				return null;
			}
		};
		
		for (Equation eq : node.equations) {
			eq.expr.accept(nodeCallCollector);
		}
		return dependencies;
	}
	
	private Map<String, Set<String>> dependencies;
	private Deque<String> callStack;

	private NodeDependencyChecker(Map<String, Set<String>> dependencies) {
		this.dependencies = dependencies;
		this.callStack = new ArrayDeque<String>();
	}

	private boolean check() {
		for (String root : dependencies.keySet()) {
			if (!check(root)) {
				return false;
			}
		}
		
		return true;
	}

	private boolean check(String curr) {
		if (callStack.contains(curr)) {
			callStack.addLast(curr);
			while (!curr.equals(callStack.peekFirst())) {
				callStack.removeFirst();
			}
			System.out.println("Error: recursive node calls: " + callStack);
			return false;
		}

		callStack.addLast(curr);
		for (String next : dependencies.get(curr)) {
			if (!check(next)) {
				return false;
			}
		}
		callStack.removeLast();
		
		return true;
	}
}
