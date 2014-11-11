package jkind.pdr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.lustre.Node;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Pdr {
	private final List<Frame> trace = new ArrayList<>();
	private final PdrSolver solver = new PdrSolver();
	private final Set<Predicate> predicates = new HashSet<>();

	private final Term I;
	private final Term T;
	private final Term P;

	private final List<Term> base;
	private final List<Term> prime;

	public Pdr(Node node) {
		Lustre2Term lustre2Term = new Lustre2Term(solver);
		solver.setVarDecls(lustre2Term.getVariables(node));
		this.base = solver.getVariables("");
		this.prime = solver.getVariables("'");

		this.I = lustre2Term.getInit();
		this.T = lustre2Term.getTransition(node);
		this.P = lustre2Term.getProperty(node);

		predicates.addAll(PredicateCollector.collect(I, base));
		predicates.addAll(PredicateCollector.collect(P, base));
	}

	public Cube check() {
		trace.add(new Frame(I));
		trace.add(new Frame());

		try {
			while (true) {
				blockCubes();
				if (propogateClauses()) {
					return null;
				}
			}
		} catch (CounterexampleException cex) {
			return cex.getInit();
		}
	}

	private void blockCubes() {
		Model m;
		// exists P-cube c s.t. c |= trace.last() /\ ~P
		while ((m = checkSat(and(lastFrame(), not(P)))) != null) {
			recBlock(extractCube(m), trace.size() - 1);
		}
	}

	private boolean propogateClauses() {
		trace.add(new Frame());
		for (int i = 1; i < trace.size() - 1; i++) {
			for (Cube c : trace.get(i).getCubes()) {
				if (checkSat(and(trace.get(i), T, prime(c))) == null) {
					trace.get(i + 1).add(c);
				}
			}

			if (checkSat(and(trace.get(i + 1), not(trace.get(i)))) == null) {
				return true;
			}
		}

		return false;
	}

	private void recBlock(Cube s, int i) {
		if (checkSat(and(trace.get(0), s)) != null) {
			throw new CounterexampleException(s);
		}

		if (i == 0) {
			return;
		}

		while (true) {
			Cube c = extractCube(checkSat(and(trace.get(i - 1), T, not(s), prime(s))));
			if (c == null) {
				break;
			}

			c.setNext(s);
			recBlock(c, i - 1);
		}

		// TODO: Generalize s
		for (int j = 1; j <= i; j++) {
			trace.get(j).add(s);
		}
	}

	private Frame lastFrame() {
		return trace.get(trace.size() - 1);
	}

	private Model checkSat(Term query) {
		return solver.checkSat(query);
	}

	private Term not(Term term) {
		return solver.not(term);
	}

	private Term not(Frame frame) {
		return solver.not(solver.frame(frame));
	}

	private Term not(Cube s) {
		return solver.not(solver.cube(s));
	}

	private Term and(Frame frame, Term... terms) {
		return solver.and(solver.frame(frame), solver.and(terms));
	}

	private Term and(Frame frame, Cube s) {
		return solver.and(solver.frame(frame), solver.cube(s));
	}
	
	private Cube extractCube(Model model) {
		return solver.extractCube(model, predicates, base);
	}

	private Term prime(Cube c) {
		return solver.apply(c, prime);
	}
}
