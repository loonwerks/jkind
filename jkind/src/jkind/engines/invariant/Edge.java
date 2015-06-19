package jkind.engines.invariant;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;

public class Edge {
	public final Node source;
	public final Node destination;

	public Edge(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}

	public Expr toInvariant() {
		Expr sRep = source.getRepresentative();
		Expr dRep = destination.getRepresentative();
		return new BinaryExpr(sRep, BinaryOp.IMPLIES, dRep);
	}

	@Override
	public String toString() {
		return source + " -> " + destination;
	}
}
