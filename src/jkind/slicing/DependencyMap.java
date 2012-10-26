package jkind.slicing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Node;

public class DependencyMap {
	private HashMap<String, Set<String>> map;

	public DependencyMap(Node node) {
		map = new HashMap<String, Set<String>>();
		computeOneStepDependencies(node);
		transitivelyCloseDependencies();
		reflexivelyCloseDependencise();
	}

	private void computeOneStepDependencies(Node node) {
		for (Equation eq : node.equations) {
			map.put(eq.id, IdExtractorVisitor.getIds(eq.expr));
		}
	}

	private void transitivelyCloseDependencies() {
		boolean changed;
		do {
			changed = false;
			for (String id : map.keySet()) {
				Set<String> set = map.get(id);
				int n = set.size();
				for (String dep : new HashSet<String>(set)) {
					if (map.containsKey(dep)) {
						set.addAll(map.get(dep));
					}
				}
				if (set.size() > n) {
					changed = true;
				}
			}
			
		} while (changed);
	}

	private void reflexivelyCloseDependencise() {
		for (String id : map.keySet()) {
			map.get(id).add(id);
		}
	}

	public Set<String> get(String id) {
		return map.get(id);
	}
}
