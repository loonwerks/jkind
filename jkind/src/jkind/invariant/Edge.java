package jkind.invariant;

import jkind.solvers.Lambda;

public class Edge {
	final public Node source;
	final public Node destination;

	public Edge(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}

	public Invariant toInvariant() {
		Candidate sRep = source.getRepresentative();
		Candidate dRep = destination.getRepresentative();

		String text = sRep + " => " + dRep;
		Lambda lambda = Lambda.cons("=>", sRep.getLambda(), dRep.getLambda());
		
		return new Invariant(lambda, text);
	}
	
	@Override
	public String toString() {
		return source + " -> " + destination;
	}
}
