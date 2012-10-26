package jkind.slicing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.Node;

public class Slicer {
	public static Node slice(Node node) {
		DependencyMap depMap = new DependencyMap(node);
		Set<String> keep = getPropertyDependencies(node, depMap);
		return keepOnlyListedEquations(node, keep);
	}

	private static Set<String> getPropertyDependencies(Node node, DependencyMap depMap) {
		Set<String> keep = new HashSet<String>();
		for (String prop : node.properties) {
			keep.addAll(depMap.get(prop));
		}
		return keep;
	}
	
	private static Node keepOnlyListedEquations(Node node, Set<String> keep) {
		List<Equation> slicedEquations = new ArrayList<Equation>();
		for (Equation eq : node.equations) {
			if (keep.contains(eq.id)) {
				slicedEquations.add(eq);
			}
		}
		return new Node(node.inputs, node.outputs, node.locals, slicedEquations, node.properties);
	}
}
