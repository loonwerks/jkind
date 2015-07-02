package jkind.translation.inline;

import java.util.ArrayList;
import java.util.List;

public class InlineNode {
	private final String name;
	private final boolean inlinable;
	private final List<InlineNode> uses = new ArrayList<>();
	private final List<InlineNode> usedBy = new ArrayList<>();

	public InlineNode(String name, boolean inlinable) {
		this.name = name;
		this.inlinable = inlinable;
	}

	public String getName() {
		return name;
	}

	public boolean isInlinable() {
		return inlinable && !uses.contains(this);
	}

	public void addUses(List<InlineNode> uses) {
		for (InlineNode other : uses) {
			addUse(other);
		}
	}

	private void addUse(InlineNode other) {
		this.uses.add(other);
		other.usedBy.add(this);
	}

	public int computeCost() {
		return uses.size();
	}

	public void inline() {
		if (!isInlinable()) {
			throw new IllegalArgumentException("Trying to inline non-inlinable equation");
		}
		
		for (InlineNode parent : usedBy) {
			for (InlineNode child : uses) {
				parent.addUse(child);
			}
		}
	}
}
