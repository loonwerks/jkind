package jkind.interval;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.slicing.Dependency;
import jkind.slicing.DependencySet;
import jkind.slicing.DependencyVisitor;

public class ReverseDependencyMap {
	private Map<Dependency, Set<Dependency>> map;

	public ReverseDependencyMap(Node node, DependencySet deps) {
		this.map = new HashMap<>();
		computeOneStepDependencies(node);
		closeDependencies(deps);
	}

	private void computeOneStepDependencies(Node node) {
		for (Equation eq : node.equations) {
			DependencySet deps = DependencyVisitor.get(eq.expr);
			for (Dependency dep : deps) {
				Set<Dependency> set = map.get(dep);
				if (set == null) {
					set = new HashSet<>();
					map.put(dep, set);
				}
				for (IdExpr idExpr : eq.lhs) {
					set.add(Dependency.variable(idExpr.id));
				}
			}
		}
	}

	private void closeDependencies(DependencySet deps) {
		Map<Dependency, Set<Dependency>> transMap = new HashMap<>();
		for (Dependency root : deps) {
			transMap.put(root, computeClosure(root));
		}
		map = transMap;
	}

	private Set<Dependency> computeClosure(Dependency dep) {
		Set<Dependency> closure = new HashSet<>();
		closure.add(dep);
		Stack<Dependency> todo = new Stack<>();
		todo.push(dep);

		while (!todo.empty()) {
			Dependency curr = todo.pop();
			if (map.containsKey(curr)) {
				for (Dependency next : map.get(curr)) {
					if (!closure.contains(next)) {
						closure.add(next);
						todo.push(next);
					}
				}
			}
		}
		return closure;
	}

	public Set<Dependency> get(String id) {
		return map.get(id);
	}
}
