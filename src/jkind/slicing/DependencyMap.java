package jkind.slicing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import jkind.lustre.Equation;
import jkind.lustre.Node;

public class DependencyMap {
	private Map<String, Set<String>> map;

	public DependencyMap(Node node) {
		map = new HashMap<String, Set<String>>();
		computeOneStepDependencies(node);
		closeDependencies();
	}

	private void computeOneStepDependencies(Node node) {
		for (Equation eq : node.equations) {
			map.put(eq.id, IdExtractorVisitor.getIds(eq.expr));
		}
	}

	private void closeDependencies() {
		Map<String, Set<String>> transMap = new HashMap<String, Set<String>>();
		for (String root : map.keySet()) {
			transMap.put(root, computeClosure(root));
		}
		map = transMap;
	}

	private Set<String> computeClosure(String root) {
		Set<String> closure = new HashSet<String>();
		closure.add(root);
		Stack<String> todo = new Stack<String>();
		todo.push(root);
		
		while (!todo.empty()) {
			String curr = todo.pop();
			if (map.containsKey(curr)) {
				for (String next : map.get(curr)) {
					if (!closure.contains(next)) {
						closure.add(next);
						todo.push(next);
					}
				}
			}
		}
		return closure;
	}

	public Set<String> get(String id) {
		return map.get(id);
	}
}
