package jkind.pdr;

import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Sexp;
import de.uni_freiburg.informatik.ultimate.logic.ApplicationTerm;
import de.uni_freiburg.informatik.ultimate.logic.Logics;
import de.uni_freiburg.informatik.ultimate.logic.Model;
import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Sort;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import de.uni_freiburg.informatik.ultimate.logic.Util;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;

public class Pdr {
	private final List<Frame> trace = new ArrayList<>();
	private final Script solver = new SMTInterpol();

	private final Term I;
	private final Term T;
	private final Term P;

	public Pdr(Sexp I, Sexp T, Sexp P) {
		solver.setLogic(Logics.QF_UFLIRA);
		solver.setOption(":verbosity", 3);

		solver.declareFun("x", new Sort[0], solver.sort("Bool"));
		solver.declareFun("y", new Sort[0], solver.sort("Bool"));
		solver.declareFun("z", new Sort[0], solver.sort("Bool"));
		solver.declareFun("x'", new Sort[0], solver.sort("Bool"));
		solver.declareFun("y'", new Sort[0], solver.sort("Bool"));
		solver.declareFun("z'", new Sort[0], solver.sort("Bool"));

		SexpConvert sexpConvert = new SexpConvert(solver);
		this.I = sexpConvert.convert(I);
		this.T = sexpConvert.convert(T);
		this.P = sexpConvert.convert(P);
	}

	public Cube check() {
		trace.add(new Frame(I));
		trace.add(new Frame());

		while (true) {
			Cube cex = blockCubes();
			if (cex != null) {
				return cex;
			}

			if (propogateClauses()) {
				return null;
			}
		}
	}

	private Cube blockCubes() {
		while (true) {
			// exists P-cube c s.t. c |= trace.last() /\ ~P
			Model m = checkSat(Util.and(solver, lastFrame().toTerm(solver), Util.not(solver, P)));
			if (m == null) {
				return null;
			}

			Cube c = extractCube(m);
			Cube cex = recBlock(c, trace.size() - 1);
			if (cex != null) {
				return cex;
			}
		}
	}

	private boolean propogateClauses() {
		trace.add(new Frame());
		for (int i = 1; i < trace.size() - 1; i++) {
			for (Clause c : trace.get(i).getClauses()) {
				Term query = Util.and(solver, trace.get(i).toTerm(solver), T,
						c.negate().prime(solver).toTerm(solver));
				if (checkSat(query) == null) {
					trace.get(i + 1).add(c);
				}
			}

			Term query = Util.and(solver, Util.not(solver, trace.get(i).toTerm(solver)),
					trace.get(i + 1).toTerm(solver));
			if (checkSat(query) == null) {
				return true;
			}
		}

		return false;
	}

	private Cube recBlock(Cube s, int i) {
		Term query = Util.and(solver, trace.get(0).toTerm(solver), s.toTerm(solver));
		if (checkSat(query) != null) {
			return s;
		}
		
		if (i == 0) {
			return null;
		}

		while (true) {
			query = Util.and(solver, trace.get(i - 1).toTerm(solver), T,
					s.negate().toTerm(solver), s.prime(solver).toTerm(solver));
			Cube c = extractCube(checkSat(query));
			if (c == null) {
				break;
			}

			c.setNext(s);
			Cube result = recBlock(c, i - 1);
			if (result != null) {
				return result;
			}
		}

		// TODO: Generalize ~s
		Clause g = s.negate();

		trace.get(i).add(g);

		return null;
	}

	private Model checkSat(Term query) {
		solver.push(1);
		solver.assertTerm(query);
		switch (solver.checkSat()) {
		case UNSAT:
			solver.pop(1);
			return null;

		case SAT:
			Model model = solver.getModel();
			solver.pop(1);
			return model;

		default:
			throw new IllegalArgumentException();
		}
	}

	private Frame lastFrame() {
		return trace.get(trace.size() - 1);
	}

	private Cube extractCube(Model m) {
		if (m == null) {
			return null;
		}

		Term x = solver.term("x");
		Term y = solver.term("y");
		Term z = solver.term("z");

		Cube c = new Cube();
		if (isTrue(m.evaluate(x))) {
			c.addPositive(x);
		} else {
			c.addNegative(x);
		}
		if (isTrue(m.evaluate(y))) {
			c.addPositive(y);
		} else {
			c.addNegative(y);
		}
		if (isTrue(m.evaluate(z))) {
			c.addPositive(z);
		} else {
			c.addNegative(z);
		}

		return c;
	}

	private boolean isTrue(Term term) {
		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			return at.getFunction().getName().equals("true");
		}
		return false;
	}
}
