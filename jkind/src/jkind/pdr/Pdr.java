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
	private Term Tp;
	private final Term T;
	private final Term P;

	private final List<Term> base;
	private final List<Term> prime;
	private final List<Term> bar;
	private final List<Term> barPrime;

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

		this.bar = solver.getVariables("-");
		this.barPrime = solver.getVariables("-'");
		this.Tp = computeTp();

	}

	private Term computeTp() {
		return and(eqP(base, bar), T(bar, barPrime), eqP(barPrime, prime));
	}

	public Cube check() {
		trace.add(new Frame(I));

		// TODO: Clean up
		Frame f = new Frame();
		Cube c = new Cube();
		c.addPositive(new Predicate(I, base));
		f.add(c);
		trace.add(f);

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
			try {
				// TODO: Generalize cube
				recBlock(extractCube(m), trace.size() - 1);
			} catch (CounterexampleException cex) {
				if (!refine(cex)) {
					throw cex;
				}
			}
		}
	}

	private boolean propogateClauses() {
		trace.add(new Frame());
		for (int i = 1; i < trace.size() - 1; i++) {
			for (Cube c : trace.get(i).getCubes()) {
				if (checkSat(and(trace.get(i), Tp, prime(c))) == null) {
					// TODO: Check subsumption
					trace.get(i + 1).add(c);
				}
			}

			// TODO: Check more efficiently?
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
			Cube c = extractCube(checkSat(and(trace.get(i - 1), Tp, not(s), prime(s))));
			if (c == null) {
				break;
			}

			// TODO: Generalize c

			c.setNext(s);
			recBlock(c, i - 1);
		}

		// TODO: Generalize s
		for (int j = 1; j <= i; j++) {
			trace.get(j).add(s);
		}
	}

	private boolean refine(CounterexampleException cex) {
		List<Cube> cubes = getCubes(cex);
		if (cubes.size() < 2) {
			throw new IllegalArgumentException();
		} else if (cubes.size() == 2) {
			return refineByBlocking(cubes);
		} else {
			return refineByPredicates(cubes);
		}
	}

	private boolean refineByBlocking(List<Cube> cubes) {
		// Counterexample too short for interpolation
		// TODO: Double check this
		if (cubes.size() != 2) {
			throw new IllegalArgumentException();
		}

		Model m = checkSat(and(cubes.get(0), T, prime(cubes.get(1)), not(prime(P))));
		if (m != null) {
			return false;
		} else {
			trace.get(1).add(cubes.get(1));
			return true;
		}
	}

	private boolean refineByPredicates(List<Cube> cubes) {
		List<Term> conjuncts = new ArrayList<>();

		List<Term> vars = solver.getVariables("$0");
		for (int i = 0; i < cubes.size() - 1; i++) {
			List<Term> nextVars = solver.getVariables("$" + (i + 1));
			conjuncts.add(solver.apply(cubes.get(i), vars));
			conjuncts.add(T(vars, nextVars));
			vars = nextVars;
		}
		conjuncts.add(solver.apply(cubes.get(cubes.size() - 1), vars));
		conjuncts.add(not(P(vars)));

		Model m = checkSat(solver.and(conjuncts));
		if (m != null) {
			return false;
		}

		else if (cubes.size() == 2) {
			trace.get(1).add(cubes.get(1));
		} else {

		}
		return true;
	}

	private List<Cube> getCubes(CounterexampleException cex) {
		List<Cube> result = new ArrayList<>();
		Cube curr = cex.getInit();
		while (curr != null) {
			result.add(curr);
			curr = curr.getNext();
		}
		return result;
	}

	private Term eqP(List<Term> variables1, List<Term> variables2) {
		Term[] terms = new Term[predicates.size()];
		int i = 0;
		for (Predicate p : predicates) {
			terms[i++] = solver.term("=", solver.apply(p, variables1), solver.apply(p, variables2));
		}
		return solver.and(terms);
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

	private Term and(Term... terms) {
		return solver.and(terms);
	}

	private Term and(Frame frame, Cube cube) {
		return solver.and(solver.frame(frame), solver.cube(cube));
	}

	private Term and(Cube cube, Term... terms) {
		return solver.and(solver.cube(cube), solver.and(terms));
	}

	private Cube extractCube(Model model) {
		return solver.extractCube(model, predicates, base);
	}

	private Term prime(Cube cube) {
		return solver.apply(cube, prime);
	}

	private Term prime(Term term) {
		return solver.subst(term, base, prime);
	}

	private Term T(List<Term> variables1, List<Term> variables2) {
		return solver.subst(solver.subst(T, base, variables1), prime, variables2);
	}

	private Term P(List<Term> variables) {
		return solver.subst(P, base, variables);
	}
}
