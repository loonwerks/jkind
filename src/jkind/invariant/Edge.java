package jkind.invariant;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;

public class Edge {
	final public Node source;
	final public Node destination;

	public Edge(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}

	public Sexp toInvariant(Sexp index, boolean pure) {
		Sexp sRep = source.getRepresentative().index(index, pure);
		Sexp dRep = destination.getRepresentative().index(index, pure);
		return new Cons("=>", sRep, dRep);
	}
	
	@Override
	public String toString() {
		return source + " -> " + destination;
	}
}
