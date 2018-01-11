package jkind.interval;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.slicing.Dependency;
import jkind.slicing.DependencySet;
import jkind.slicing.DependencyVisitor;

public class ReverseDependencyMap {
	private Map<Dependency, DependencySet> map = new HashMap<>();

	public ReverseDependencyMap(Node node, DependencySet roots) {
		computeOneStepDependencies(node);
		closeDependencies(roots);
	}

	private void computeOneStepDependencies(Node node) {
		for (Equation eq : node.equations) {
			DependencySet deps = DependencyVisitor.get(eq.expr);
			for (Dependency dep : deps) {
				DependencySet set = map.get(dep);
				if (set == null) {
					set = new DependencySet();
					map.put(dep, set);
				}
				for (IdExpr idExpr : eq.lhs) {
					set.add(Dependency.variable(idExpr.id));
				}
			}
		}
	}

	private void closeDependencies(DependencySet roots) {
		Map<Dependency, DependencySet> transMap = new HashMap<>();
		for (Dependency root : roots) {
			transMap.put(root, computeClosure(root));
		}
		map = transMap;
	}

	private DependencySet computeClosure(Dependency root) {
		DependencySet closure = new DependencySet();
		closure.add(root);
		Stack<Dependency> todo = new Stack<>();
		todo.push(root);

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

	public DependencySet get(String id) {
		return map.get(Dependency.variable(id));
	}
}
