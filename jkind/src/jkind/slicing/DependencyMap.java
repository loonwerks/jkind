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
import jkind.lustre.Function;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class DependencyMap {
	private Map<Dependency, DependencySet> map = new HashMap<>();

	public DependencyMap(Node node, List<String> roots, List<Function> functions) {
		computeOneStepDependencies(node, functions);
		analyzeAssertions(node.assertions);
		closeDependencies(roots);
	}

	private void computeOneStepDependencies(Node node, List<Function> functions) {
		for (VarDecl input : node.inputs) {
			map.put(Dependency.variable(input.id), new DependencySet());
		}

		for (Function fn : functions) {
			map.put(Dependency.function(fn.id), new DependencySet());
		}

		for (Equation eq : node.equations) {
			DependencySet deps = DependencyVisitor.get(eq.expr);
			for (IdExpr idExpr : eq.lhs) {
				map.put(Dependency.variable(idExpr.id), deps);
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

			// All elements in an assertion depend on all other elements in it
			for (Dependency dep : assertionDependencies) {
				map.get(dep).addAll(assertionDependencies);
			}
		}
	}

	private void closeDependencies(List<String> roots) {
		Map<Dependency, DependencySet> transMap = new HashMap<>();
		for (String variableRoot : roots) {
			Dependency dep = Dependency.variable(variableRoot);
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

	private DependencyMap() {
	}

	public static DependencyMap full(Node node, List<Function> functions) {
		DependencyMap result = new DependencyMap();

		DependencySet set = new DependencySet();
		for (VarDecl vd : Util.getVarDecls(node)) {
			set.add(Dependency.variable(vd.id));
		}
		
		for (Function fn : functions) {
			set.add(Dependency.function(fn.id));
		}
		
		for (VarDecl vd : Util.getVarDecls(node)) {
			result.map.put(Dependency.variable(vd.id), set);
		}

		return result;
	}

	public DependencySet get(Dependency dependency) {
		return map.get(dependency);
	}

	public DependencySet get(String var) {
		return get(Dependency.variable(var));
	}

	public DependencySet get(List<String> vars) {
		DependencySet result = new DependencySet();
		for (String var : vars) {
			result.addAll(get(var));
		}
		return result;
	}
}
