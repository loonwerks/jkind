package jkind.invariant;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.translation.Util;

public class Edge {
	final public Node source;
	final public Node destination;

	public Edge(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}

	public Invariant toInvariant(boolean pure) {
		Candidate sRep = source.getRepresentative();
		Candidate dRep = destination.getRepresentative();
		Sexp sexp = Util.lambdaI(new Cons("=>", sRep.index(Util.I, pure), dRep.index(Util.I, pure)));
		return new Invariant(sexp, sRep + " => " + dRep);
	}
	
	@Override
	public String toString() {
		return source + " -> " + destination;
	}
}
