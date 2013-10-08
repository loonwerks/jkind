package jkind.interval;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.slicing.IdExtractorVisitor;

public class ReverseDependencyMap {
	private Map<String, Set<String>> map;

	public ReverseDependencyMap(Node node, Set<String> roots) {
		this.map = new HashMap<>();
		computeOneStepDependencies(node);
		closeDependencies(roots);
	}

	private void computeOneStepDependencies(Node node) {
		for (Equation eq : node.equations) {
			Set<String> deps = IdExtractorVisitor.getIds(eq.expr);
			for (String dep : deps) {
				Set<String> set = map.get(dep);
				if (set == null) {
					set = new HashSet<>();
					map.put(dep, set);
				}
				for (IdExpr idExpr : eq.lhs) {
					set.add(idExpr.id);
				}
			}
		}
	}

	private void closeDependencies(Set<String> roots) {
		Map<String, Set<String>> transMap = new HashMap<>();
		for (String root : roots) {
			transMap.put(root, computeClosure(root));
		}
		map = transMap;
	}

	private Set<String> computeClosure(String root) {
		Set<String> closure = new HashSet<>();
		closure.add(root);
		Stack<String> todo = new Stack<>();
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
