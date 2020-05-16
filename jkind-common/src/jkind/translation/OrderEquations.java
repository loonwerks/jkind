package jkind.translation;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.JKindException;
import jkind.lustre.Equation;
import jkind.lustre.Node;
import jkind.lustre.builders.NodeBuilder;
import jkind.util.CycleFinder;
import jkind.util.TopologicalSorter;
import jkind.util.Util;

/**
 * Order equations in assignment-use order if possible. Throw
 * AlgebraicLoopException if not possible. This transformation assumes all
 * equations are to single variables.
 */
public class OrderEquations {
	public static Node node(Node node) {
		Map<String, Set<String>> dependencies = Util.getDirectDependencies(node);

		ensureNoCycles(dependencies);
		ensureAllSingleAssignment(node);

		NodeBuilder builder = new NodeBuilder(node);
		builder.clearEquations();
		builder.addEquations(getSortedEquations(node.equations, dependencies));

		return builder.build();
	}

	private static void ensureNoCycles(Map<String, Set<String>> dependencies) {
		List<String> cycle = CycleFinder.findCycle(dependencies);
		if (cycle != null) {
			throw new AlgebraicLoopException(cycle);
		}
	}

	private static void ensureAllSingleAssignment(Node node) {
		for (Equation eq : node.equations) {
			if (eq.lhs.size() != 1) {
				throw new JKindException("OrderEquations expected single variable assignment, but found: " + eq.lhs);
			}
		}
	}

	private static Collection<Equation> getSortedEquations(List<Equation> equations,
			Map<String, Set<String>> dependencies) {
		TopologicalSorter<Equation> sorter = new TopologicalSorter<>();
		for (Equation eq : equations) {
			String id = eq.lhs.get(0).id;
			sorter.put(id, eq, dependencies.get(id));
		}
		return sorter.getSortedValues();
	}

	public static class AlgebraicLoopException extends JKindException {
		private static final long serialVersionUID = 1L;

		public AlgebraicLoopException(List<String> cycle) {
			super("Algebraic loop: " + cycle);
		}
	}
}
