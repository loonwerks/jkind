package jkind.invariant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Lambda;
import jkind.solvers.Model;
import jkind.util.Util;

public class Node {
	private List<Candidate> candidates;

	public Node(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public boolean isEmpty() {
		return candidates.isEmpty();
	}
	
	public Candidate getRepresentative() {
		return candidates.get(0);
	}

	public boolean isSingleton() {
		return candidates.size() <= 1;
	}

	public List<Invariant> toInvariants(boolean pure) {
		List<Invariant> invariants = new ArrayList<Invariant>();
		
		Iterator<Candidate> iterator = candidates.iterator();
		Candidate first = iterator.next();
		Sexp firstSexp = first.index(Util.I, pure);
		
		while (iterator.hasNext()) {
			Candidate other = iterator.next();
			String text = first + " = " + other;
			Lambda lambda = new Lambda(Util.I, new Cons("=", firstSexp, other.index(Util.I, pure)));
			invariants.add(new Invariant(lambda, text));
		}
		
		return invariants;
	}

	public List<Node> split(Model model, BigInteger k) {
		List<Candidate> falses = new ArrayList<Candidate>();
		List<Candidate> trues = new ArrayList<Candidate>();
		
		for (Candidate candidate : candidates) {
			if (candidate.isTrue(model, k)) {
				trues.add(candidate);
			} else {
				falses.add(candidate);
			}
		}

		List<Node> chain = new ArrayList<Node>(2);
		chain.add(new Node(falses));
		chain.add(new Node(trues));
		return chain;
	}

	@Override
	public String toString() {
		return candidates.toString();
	}
}
