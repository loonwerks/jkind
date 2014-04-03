package jkind.translation.compound;

import jkind.lustre.Node;
import jkind.lustre.visitors.ExprMapVisitor;

/**
 * Flatten arrays and records to scalars
 * 
 * Assumption: All node calls have been inlined.
 */
public class FlattenCompoundTypes extends ExprMapVisitor {
	public static Node node(Node node) {
		node = RemoveNonConstantArrayIndices.node(node);
		node = FlattenCompoundComparisons.node(node);
		node = FlattenCompoundVariables.node(node);
		node = FlattenCompoundExpressions.node(node);
		return node;
	}
}
