package jkind.pdr;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.NamedType;
import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Pdr {
	private final List<Frame> trace = new ArrayList<>();
	private final PdrSolver solver = new PdrSolver();

	private final Term I;
	private final Term T;
	private final Term P;

	public Pdr(Sexp I, Sexp T, Sexp P) {
		solver.addVariable(new VarDecl("x", NamedType.BOOL));
		solver.addVariable(new VarDecl("y", NamedType.BOOL));
		solver.addVariable(new VarDecl("z", NamedType.BOOL));

		this.I = solver.convert(I);
		this.T = solver.convert(T);
		this.P = solver.convert(P);
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
		while (true) {
			// exists P-cube c s.t. c |= trace.last() /\ ~P
			Model m = checkSat(and(lastFrame(), not(P)));
			if (m == null) {
				return;
			}

			Cube c = extractCube(m);
			recBlock(c, trace.size() - 1);
		}
	}

	private boolean propogateClauses() {
		trace.add(new Frame());
		for (int i = 1; i < trace.size() - 1; i++) {
			for (Cube c : trace.get(i).getCubes()) {
				Term query = and(trace.get(i), T, prime(c));
				if (checkSat(query) == null) {
					trace.get(i + 1).add(c);
				}
			}

			Term query = and(trace.get(i + 1), not(trace.get(i)));
			if (checkSat(query) == null) {
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
		trace.get(i).add(s);
	}

	private Cube extractCube(Model model) {
		return solver.extractCube(model);
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

	private Term and(Frame frame, Term... terms) {
		return solver.and(solver.toTerm(frame), solver.and(terms));
	}

	private Term and(Frame frame, Cube s) {
		return solver.and(solver.toTerm(frame), solver.toTerm(s));
	}

	private Term prime(Cube c) {
		return solver.prime(c);
	}

	private Term not(Frame frame) {
		return solver.not(solver.toTerm(frame));
	}

	private Term not(Cube s) {
		return solver.not(solver.toTerm(s));
	}
}
