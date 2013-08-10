package jkind.slicing;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;

public class DependencyMap {
	private Map<String, Set<String>> map;

	public DependencyMap(Node node, List<String> roots) {
		this.map = new HashMap<>();
		computeOneStepDependencies(node);
		analyzeAssertions(node.assertions);
		closeDependencies(roots);
	}

	private void computeOneStepDependencies(Node node) {
		for (VarDecl input : node.inputs) {
			map.put(input.id, new HashSet<String>());
		}

		for (Equation eq : node.equations) {
			Set<String> deps = IdExtractorVisitor.getIds(eq.expr);
			for (IdExpr idExpr : eq.lhs) {
				map.put(idExpr.id, deps);
			}
		}
	}

	/*
	 * Assertions cause everything they (transitively) touch to be related. For
	 * example, suppose x depends on y and y depends on z. If we assert that x
	 * is always true, then x, y, and z all depends on each other. We encode
	 * this by adding one step dependencies to the map and closing them later.
	 */
	private void analyzeAssertions(List<Expr> assertions) {
		for (Expr assertion : assertions) {
			Set<String> ids = IdExtractorVisitor.getIds(assertion);

			// Everything mentioned in an assertion creates bidirectional
			// dependencies for all its dependencies
			Queue<String> todo = new ArrayDeque<>(ids);
			Set<String> seen = new HashSet<>();
			while (!todo.isEmpty()) {
				String id = todo.remove();
				if (seen.contains(id)) {
					continue;
				} else {
					seen.add(id);
				}

				Set<String> deps = map.get(id);
				for (String dep : deps) {
					map.get(dep).add(id);
				}
				todo.addAll(deps);
			}

			// All variables in an assertion depend on all other variables in it
			for (String id : ids) {
				map.get(id).addAll(ids);
			}
		}
	}

	private void closeDependencies(List<String> roots) {
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
