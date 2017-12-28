package jkind.engines.invariant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.visitors.Evaluator;

public class GraphInvariant implements StructuredInvariant {
	private List<Node> nodes = new ArrayList<>();
	private final Map<Node, Set<Node>> incoming = new HashMap<>();
	private final Map<Node, Set<Node>> outgoing = new HashMap<>();

	public GraphInvariant(List<Expr> candidates) {
		nodes.add(new Node(candidates));
	}

	@Override
	public boolean isTrivial() {
		return nodes.isEmpty() || (nodes.size() == 1 && nodes.get(0).isSingleton());
	}

	private void addEdge(Node source, Node destination) {
		outgoing(source).add(destination);
		incoming(destination).add(source);
	}

	private Set<Node> incoming(Node destination) {
		return safeGet(incoming, destination);
	}

	private Set<Node> outgoing(Node source) {
		return safeGet(outgoing, source);
	}

	private Set<Node> safeGet(Map<Node, Set<Node>> map, Node node) {
		Set<Node> result = map.get(node);
		if (result == null) {
			result = new HashSet<>();
			map.put(node, result);
		}
		return result;
	}

	private Set<Edge> getEdges() {
		Set<Edge> edges = new HashSet<>();
		for (Entry<Node, Set<Node>> entry : outgoing.entrySet()) {
			Node source = entry.getKey();
			for (Node destination : entry.getValue()) {
				edges.add(new Edge(source, destination));
			}
		}
		return edges;
	}

	private void setEdges(List<Edge> edges) {
		incoming.clear();
		outgoing.clear();
		for (Edge edge : edges) {
			addEdge(edge.source, edge.destination);
		}
	}

	@Override
	public List<Expr> toExprs() {
		List<Expr> exprs = new ArrayList<>();
		for (Node node : nodes) {
			exprs.addAll(node.toInvariants());
		}
		for (Edge edge : getEdges()) {
			exprs.add(edge.toInvariant());
		}
		return exprs;
	}

	@Override
	public List<Expr> toFinalInvariants() {
		removeTrivialInvariants();
		return toExprs();
	}

	private void removeTrivialInvariants() {
		for (Node node : nodes) {
			if (isTrue(node.getRepresentative())) {
				Set<Node> in = incoming(node);
				for (Node other : in) {
					outgoing(other).remove(node);
				}
				in.clear();
			} else if (isFalse(node.getRepresentative())) {
				Set<Node> out = outgoing(node);
				for (Node other : out) {
					incoming(other).remove(node);
				}
				out.clear();
			}
		}

		removeUselessNodes();
	}

	private boolean isTrue(Expr expr) {
		return isBoolean(expr, true);
	}

	private boolean isFalse(Expr expr) {
		return isBoolean(expr, false);
	}

	private boolean isBoolean(Expr expr, boolean value) {
		if (expr instanceof BoolExpr) {
			BoolExpr be = (BoolExpr) expr;
			return be.value == value;
		}
		return false;
	}

	@Override
	public void refine(Evaluator eval) {
		splitNodes(eval);
		removeEmptyNodes();
		removeUselessNodes();
	}

	private void splitNodes(Evaluator eval) {
		List<Node> newNodes = new ArrayList<>();
		List<Edge> newEdges = new ArrayList<>();
		Map<Node, List<Node>> chains = new HashMap<>();

		// Split nodes into chains
		for (Node curr : nodes) {
			List<Node> chain = curr.split(eval);
			chains.put(curr, chain);
			newNodes.addAll(chain);
			if (!chain.get(0).isEmpty() && !chain.get(1).isEmpty()) {
				newEdges.add(new Edge(chain.get(0), chain.get(1)));
			}
		}

		// Join chains based on previous edges
		for (Edge edge : getEdges()) {
			List<Node> sourceChain = chains.get(edge.source);
			List<Node> destinationChain = chains.get(edge.destination);
			newEdges.add(new Edge(sourceChain.get(0), destinationChain.get(0)));
			newEdges.add(new Edge(sourceChain.get(1), destinationChain.get(1)));
			if (sourceChain.get(1).isEmpty() && destinationChain.get(0).isEmpty()) {
				newEdges.add(new Edge(sourceChain.get(0), destinationChain.get(1)));
			}
		}

		nodes = newNodes;
		setEdges(newEdges);
	}

	private void removeEmptyNodes() {
		Iterator<Node> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if (node.isEmpty()) {
				iterator.remove();
				rerouteEdgesAroundNode(node);
			}
		}
	}

	private void rerouteEdgesAroundNode(Node node) {
		Set<Node> in = incoming(node);
		Set<Node> out = outgoing(node);

		incoming.remove(node);
		outgoing.remove(node);

		for (Node source : in) {
			outgoing(source).remove(node);
		}
		for (Node destination : out) {
			incoming(destination).remove(node);
		}

		for (Node source : in) {
			for (Node destination : out) {
				addEdge(source, destination);
			}
		}
	}

	private void removeUselessNodes() {
		Iterator<Node> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if (node.isSingleton() && incoming(node).isEmpty() && outgoing(node).isEmpty()) {
				iterator.remove();
				incoming.remove(node);
				outgoing.remove(node);
			}
		}
	}

	@Override
	public StructuredInvariant copy() {
		return new GraphInvariant(this);
	}

	private GraphInvariant(GraphInvariant other) {
		nodes.addAll(other.nodes);
		copy(other.incoming, incoming);
		copy(other.outgoing, outgoing);
	}

	private static void copy(Map<Node, Set<Node>> src, Map<Node, Set<Node>> dst) {
		for (Entry<Node, Set<Node>> entry : src.entrySet()) {
			dst.put(entry.getKey(), new HashSet<>(entry.getValue()));
		}
	}

	@Override
	public void reduceProven(StructuredInvariant proven) {
	}
}
