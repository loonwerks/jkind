package jkind.pdr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Result;
import jkind.solvers.UnsatResult;
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
		
		solver.declareFun("x", new Sort[0], solver.sort("Bool"));
		solver.declareFun("y", new Sort[0], solver.sort("Bool"));
		solver.declareFun("z", new Sort[0], solver.sort("Bool"));
		solver.declareFun("x'", new Sort[0], solver.sort("Bool"));
		solver.declareFun("y'", new Sort[0], solver.sort("Bool"));
		solver.declareFun("z'", new Sort[0], solver.sort("Bool"));
		
		this.I = convert(I);
		this.T = convert(T);
		this.P = convert(P);
	}

	private Term convert(Sexp sexp) {
		if (sexp instanceof Symbol) {
			return convert((Symbol) sexp);
		} else if (sexp instanceof Cons) {
			return convert((Cons) sexp);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Term convert(Symbol symbol) {
		return solver.term(symbol.str);
	}

	private Term convert(Cons cons) {
		Term[] args = new Term[cons.args.size()];
		for (int i = 0; i < cons.args.size(); i++) {
			args[i] = convert(cons.args.get(i));
		}
		return solver.term(cons.head.toString(), args);
	}

	public boolean check() {
		trace.add(new Frame(I));
		trace.add(new Frame());
		while (true) {
			// exists P-cube c s.t. c |= trace.last() /\ ~P
			Model m = checkSat(Util.and(solver, lastFrame().getTerm(solver), Util.not(solver, P)));
			Cube c = extractCube(m);
			return recBlock(c, trace.size() - 1);
		}
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

	private boolean recBlock(Cube s, int i) {
		if (i == 0) {
			return false;
		}

		Term currFrame = trace.get(i).getTerm(solver);
		Term query = Util.and(solver, currFrame, T, s.negate().toTerm(solver), s.prime(solver)
				.toTerm(solver));
		Cube c = extractCube(checkSat(query));
		while (c != null) {

		}
		return false;
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
}
