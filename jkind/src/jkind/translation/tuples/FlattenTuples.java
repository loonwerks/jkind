package jkind.translation.tuples;

import jkind.lustre.Node;

/**
 * Flatten all tuple expressions via expansion.
 * 
 * Assumption: All node calls have been inlined.
 */
public class FlattenTuples {
	public static Node node(Node node) {
		node = LiftTuples.node(node);
		node = FlattenTupleComparisons.node(node);
		node = FlattenTupleAssignments.node(node);
		return node;
	}
}
