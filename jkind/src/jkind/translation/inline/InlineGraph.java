package jkind.translation.inline;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.translation.ContainsTemporalOperator;
import jkind.translation.IdListExtractorVisitor;

public class InlineGraph {
	private Map<String, InlineNode> map = new HashMap<>();
	private Set<String> inline = new HashSet<>();

	public InlineGraph(Node node) {
		createNodes(node);
		createEdges(node);
	}

	private void createNodes(Node node) {
		for (VarDecl vd : node.inputs) {
			map.put(vd.id, new InlineNode(vd.id, false));
		}

		for (Equation eq : node.equations) {
			boolean inlinable = !ContainsTemporalOperator.check(eq.expr);
			for (IdExpr lhs : eq.lhs) {
				map.put(lhs.id, new InlineNode(lhs.id, inlinable));
			}
		}
	}

	private void createEdges(Node node) {
		for (Equation eq : node.equations) {
			List<String> usesIds = IdListExtractorVisitor.getIds(eq.expr);
			List<InlineNode> uses = usesIds.stream().map(i -> map.get(i)).collect(toList());
			for (IdExpr lhs : eq.lhs) {
				map.get(lhs.id).addUses(uses);
			}
		}
	}

	/*
	 * TODO: Move this out to another class? Or elevant the priority queue?
	 */
	public Set<String> getInlineVariables(int maxCost) {
		PriorityQueue<FixedCostInlineNode> pq = new PriorityQueue<>();
		for (InlineNode in : map.values()) {
			int cost = in.computeCost();
			if (cost <= maxCost && in.isInlinable()) {
				pq.add(new FixedCostInlineNode(cost, in));
			}
		}

		while (!pq.isEmpty()) {
			FixedCostInlineNode head = pq.poll();
			InlineNode node = head.getNode();

			int newCost = node.computeCost();
			if (newCost > head.getCost()) {
				if (newCost <= maxCost && node.isInlinable()) {
					pq.add(new FixedCostInlineNode(newCost, node));
				}
				continue;
			}
			
			inline.add(node.getName());
			node.inline();
		}

		return inline;
	}
}
