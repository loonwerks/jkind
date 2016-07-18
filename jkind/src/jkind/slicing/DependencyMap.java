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
	private Map<Dependency, DependencySet> map = new HashMap<>();

	public DependencyMap(Node node, List<String> roots) {
		computeOneStepDependencies(node);
		analyzeAssertions(node.assertions);
		closeDependencies(roots);
	}

	private void computeOneStepDependencies(Node node) {
		for (VarDecl input : node.inputs) {
			map.put(new Dependency(input.id), new DependencySet());
		}
		
		for (Equation eq : node.equations) {
			DependencySet deps = DependencyVisitor.get(eq.expr);
			for (IdExpr idExpr : eq.lhs) {
				map.put(new Dependency(idExpr.id), deps);
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
			DependencySet assertionDependencies = DependencyVisitor.get(assertion);

			// Everything mentioned in an assertion creates bidirectional
			// dependencies for all its dependencies
			Queue<Dependency> todo = new ArrayDeque<>(assertionDependencies.getSet());
			Set<Dependency> seen = new HashSet<>();
			while (!todo.isEmpty()) {
				Dependency curr = todo.remove();
				if (!seen.add(curr)) {
					continue;
				}

				DependencySet deps = map.get(curr);
				for (Dependency dep : deps) { 
					map.get(dep).add(curr); 
				}
				todo.addAll(deps.getSet());
			}

			// All variables in an assertion depend on all other variables in it
			for (Dependency dep : assertionDependencies) {
				map.get(dep).addAll(assertionDependencies);
			}
		}
	}

	private void closeDependencies(List<String> roots) {
		Map<Dependency, DependencySet> transMap = new HashMap<>();
		for (String variableRoot : roots) {
			Dependency dep = new Dependency(variableRoot);
			transMap.put(dep, computeClosure(dep));
		}
		map = transMap;
	}

	private DependencySet computeClosure(Dependency dep) {
		DependencySet closure = new DependencySet();
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

	public DependencySet get(Dependency dependency) {
		return map.get(dependency);
	}

	public DependencySet get(String var) {
		return get(new Dependency(var));
	}
	
	public DependencySet get(List<String> vars) {
		DependencySet result = new DependencySet();
		for (String var : vars) {
			result.addAll(get(var));
		}
		return result;
	}
}
