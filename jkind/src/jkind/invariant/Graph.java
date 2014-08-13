package jkind.invariant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.util.SexpUtil;

public class Graph {
	private List<Node> nodes;
	private Map<Node, Set<Node>> incoming;
	private Map<Node, Set<Node>> outgoing;

	public Graph(List<Candidate> candidates) {
		this.nodes = new ArrayList<>();
		nodes.add(new Node(candidates));
		this.incoming = new HashMap<>();
		this.outgoing = new HashMap<>();
	}
	
	public int size() {
		return nodes.size();
	}

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

	public Sexp toInvariant(int k) {
		return SexpUtil.conjoinInvariants(toInvariants(), k);
	}

	public List<Invariant> toFinalInvariants() {
		removeTrivialInvariants();
		return toInvariants();
	}

	private void removeTrivialInvariants() {
		for (Node node : nodes) {
			if (node.getRepresentative() == Candidate.TRUE) {
				Set<Node> in = incoming(node);
				for (Node other : in) {
					outgoing(other).remove(node);
				}
				in.clear();
			} else if (node.getRepresentative() == Candidate.FALSE) {
				Set<Node> out = outgoing(node);
				for (Node other : out) {
					incoming(other).remove(node);
				}
				out.clear();
			}
		}

		removeUselessNodes();
	}

	private List<Invariant> toInvariants() {
		List<Invariant> invariants = new ArrayList<>();
		for (Node node : nodes) {
			invariants.addAll(node.toInvariants());
		}
		for (Edge edge : getEdges()) {
			invariants.add(edge.toInvariant());
		}
		return invariants;
	}

	public void refine(Model model, int k) {
		splitNodes(model, k);
		removeEmptyNodes();
		removeUselessNodes();
	}

	private void splitNodes(Model model, int k) {
		List<Node> newNodes = new ArrayList<>();
		List<Edge> newEdges = new ArrayList<>();
		Map<Node, List<Node>> chains = new HashMap<>();

		// Split nodes into chains
		for (Node curr : nodes) {
			List<Node> chain = curr.split(model, k);
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

	public Graph(Graph other) {
		this.nodes = new ArrayList<>(other.nodes);
		this.incoming = new HashMap<>();
		this.outgoing = new HashMap<>();
		copy(other.incoming, incoming);
		copy(other.outgoing, outgoing);
	}

	private static void copy(Map<Node, Set<Node>> src, Map<Node, Set<Node>> dst) {
		for (Entry<Node, Set<Node>> entry : src.entrySet()) {
			dst.put(entry.getKey(), new HashSet<>(entry.getValue()));
		}
	}
}
