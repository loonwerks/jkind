package jkind.invariant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.solvers.Lambda;
import jkind.solvers.Model;

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

	public List<Invariant> toInvariants() {
		List<Invariant> invariants = new ArrayList<>();
		
		Iterator<Candidate> iterator = candidates.iterator();
		Candidate first = iterator.next();
		Lambda firstLambda = first.getLambda();
		
		while (iterator.hasNext()) {
			Candidate other = iterator.next();
			String text = first + " = " + other;
			Lambda lambda = Lambda.cons("=", firstLambda, other.getLambda());
			invariants.add(new Invariant(lambda, text));
		}
		
		return invariants;
	}

	public List<Node> split(Model model, int offset) {
		List<Candidate> falses = new ArrayList<>();
		List<Candidate> trues = new ArrayList<>();
		
		for (Candidate candidate : candidates) {
			if (candidate.isTrue(model, offset)) {
				trues.add(candidate);
			} else {
				falses.add(candidate);
			}
		}

		List<Node> chain = new ArrayList<>(2);
		chain.add(new Node(falses));
		chain.add(new Node(trues));
		return chain;
	}

	@Override
	public String toString() {
		return candidates.toString();
	}
}
