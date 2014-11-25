package jkind.invariant;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;

public class Edge {
	final public Node source;
	final public Node destination;

	public Edge(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}

	public Invariant toInvariant() {
		Expr sRep = source.getRepresentative();
		Expr dRep = destination.getRepresentative();
		Expr expr = new BinaryExpr(sRep, BinaryOp.IMPLIES, dRep);
		return new Invariant(expr);
	}
	
	@Override
	public String toString() {
		return source + " -> " + destination;
	}
}
