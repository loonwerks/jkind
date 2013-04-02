package jkind.slicing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;

public class DependencyMap {
	private Map<String, Set<String>> map;

	public DependencyMap(Node node) {
		this.map = new HashMap<String, Set<String>>();

		computeOneStepDependencies(node);
		closeDependencies();

		/*
		 * Assertions cause everything they (transitively) touch to be related
		 * so we must handle them after computing the initial dependency map.
		 * Then we must compute the closure again using dependency information
		 * gained from assertions.
		 */
		analyzeAssertions(node.assertions);
		closeDependencies();
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

	private void analyzeAssertions(List<Expr> assertions) {
		for (Expr assertion : assertions) {
			Set<String> deps = new HashSet<String>();
			for (String id : IdExtractorVisitor.getIds(assertion)) {
				deps.addAll(map.get(id));
			}

			for (String id : deps) {
				map.put(id, new HashSet<String>(deps));
			}
		}
	}

	public Set<String> get(String id) {
		return map.get(id);
	}
}
