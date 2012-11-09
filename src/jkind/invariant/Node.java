package jkind.invariant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
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

	public List<Sexp> toInvariants(Sexp index, boolean pure) {
		List<Sexp> conjuncts = new ArrayList<Sexp>();
		Iterator<Candidate> iterator = candidates.iterator();
		Sexp rep = iterator.next().index(index, pure);
		while (iterator.hasNext()) {
			conjuncts.add(new Cons("=", rep, iterator.next().index(index, pure)));
		}
		return conjuncts;
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
