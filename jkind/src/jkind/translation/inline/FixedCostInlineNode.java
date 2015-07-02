package jkind.translation.inline;


public class FixedCostInlineNode implements Comparable<FixedCostInlineNode> {
	private final int cost;
	private final InlineNode node;
	
	public FixedCostInlineNode(int cost, InlineNode node) {
		this.cost = cost;
		this.node = node;
	}

	public int getCost() {
		return cost;
	}

	public InlineNode getNode() {
		return node;
	}

	@Override
	public int compareTo(FixedCostInlineNode other) {
		return Integer.compare(cost, other.cost);
	}
}
