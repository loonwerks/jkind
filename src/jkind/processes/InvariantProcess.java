package jkind.processes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.Type;
import jkind.processes.messages.InvariantMessage;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;
import jkind.solvers.NumericValue;
import jkind.solvers.SolverResult;
import jkind.solvers.SolverResult.Result;
import jkind.translation.Keywords;
import jkind.translation.Lustre2Sexps;

/**
 * This process is based on:
 * 
 * [KYT11]: Temesghen Kahsai, Yeting Ge, Cesare Tinelli. Instantiation-based
 * invariant discovery. In Proceedings of NASA Formal Methods 2011. LNCS 6617.
 * 2011. http://clc.cs.uiowa.edu/Kind/Papers/kyt11.pdf
 * 
 */
public class InvariantProcess extends Process {
	private InductiveProcess inductiveProcess;
	private List<Invariant> possibleInvariants;
	private Map<String, Type> typeMap;
	private int invariantIndex = 0;

	public InvariantProcess(String filename, Lustre2Sexps translation, Map<String, Type> typeMap) {
		super(null, translation, null);
		setScratch(filename + ".yc_inv");
		this.typeMap = typeMap;
		this.incomming = null;
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
	}

	@Override
	protected void initializeSolver() {
		super.initializeSolver();
		solver.send(new Cons("define", Keywords.N, new Symbol("::"), new Symbol("nat")));
	}

	@Override
	public void main() {
		createPossibleInvariants();
		if (possibleInvariants.isEmpty()) {
			return;
		}

		int k = basePhase();
		if (possibleInvariants.isEmpty()) {
			return;
		}

		inductivePhase(k);
		if (possibleInvariants.isEmpty()) {
			return;
		}

		removeTrivialInvariants();
		if (possibleInvariants.isEmpty()) {
			return;
		}

		sendInvariants(possibleInvariants);
	}

	private void createPossibleInvariants() {
		possibleInvariants = new ArrayList<Invariant>();
		Sexp i = new Symbol("i");
		Sexp ty = new Cons(i, new Symbol("::"), new Symbol("nat"));

		for (String id : typeMap.keySet()) {
			if (typeMap.get(id) == Type.BOOL) {
				Sexp s = new Cons("$" + id, i);
				possibleInvariants.add(createInvariant(new Cons("lambda", ty, s)));
				possibleInvariants.add(createInvariant(new Cons("lambda", ty, new Cons("not", s))));
			}
		}

		defineInvariants();
	}

	private Invariant createInvariant(Sexp sexp) {
		return new Invariant("inv" + invariantIndex++, sexp);
	}

	private void defineInvariants() {
		for (Invariant inv : possibleInvariants) {
			Sexp type = new Cons("->", new Symbol("nat"), new Symbol("bool"));
			Sexp def = new Cons("define", new Symbol(inv.id), new Symbol("::"), type);
			solver.send(def);
			solver.send(new Cons("assert", new Cons("=", new Symbol(inv.id), inv.sexp)));
		}
	}

	private int basePhase() {
		int k = 0;

		solver.push();
		do {
			k = k + 1;
			assertBaseTransition(k);
		} while (!possibleInvariants.isEmpty() && refineBaseInvariants(k));
		solver.pop();

		return k;
	}

	private void assertBaseTransition(int k) {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(k - 1))));
	}

	private boolean refineBaseInvariants(int k) {
		SolverResult result;
		boolean refined = false;

		do {
			result = solver.query(conjoinIds(getInvariantIds(), Sexp.fromInt(k - 1)));

			if (result.getResult() == Result.SAT) {
				refined = true;
				Model model = result.getModel();
				Iterator<Invariant> iterator = possibleInvariants.iterator();
				while (iterator.hasNext()) {
					Invariant inv = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue(inv.id, k - 1);
					if (!v.getBool()) {
						iterator.remove();
					}
				}
			}
		} while (!possibleInvariants.isEmpty() && result.getResult() == Result.SAT);

		return refined;
	}

	private List<String> getInvariantIds() {
		List<String> ids = new ArrayList<String>();
		for (Invariant inv : possibleInvariants) {
			ids.add(inv.id);
		}
		return ids;
	}

	private void inductivePhase(int k) {
		solver.push();
		for (int i = 0; i <= k; i++) {
			assertInductiveTransition(i);
		}
		refineInductiveInvariants(k);
		solver.pop();
	}

	private void assertInductiveTransition(int k) {
		solver.send(new Cons("assert", new Cons(Keywords.T, getInductiveIndex(k))));
	}

	private Sexp getInductiveIndex(int offset) {
		return new Cons("+", Keywords.N, Sexp.fromInt(offset));
	}

	private void refineInductiveInvariants(int k) {
		SolverResult result;
		do {
			result = solver.query(getInductiveQuery(k, getInvariantIds()));

			if (result.getResult() == null) {
				throw new JKindException("Unknown result from solver");
			} else if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				int n = getN(model);
				Iterator<Invariant> iterator = possibleInvariants.iterator();
				while (iterator.hasNext()) {
					Invariant inv = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue(inv.id, n + k);
					if (!v.getBool()) {
						iterator.remove();
					}
				}
			}
		} while (!possibleInvariants.isEmpty() && result.getResult() != Result.UNSAT);
	}

	private int getN(Model model) {
		NumericValue value = (NumericValue) model.getValue(Keywords.N);
		return Integer.parseInt(value.toString());
	}

	private Sexp getInductiveQuery(int k, List<String> invs) {
		List<Sexp> hyps = new ArrayList<Sexp>();
		for (int i = 0; i < k; i++) {
			hyps.add(conjoinIds(invs, getInductiveIndex(i)));
		}
		Sexp conc = conjoinIds(invs, getInductiveIndex(k));

		return new Cons("=>", new Cons("and", hyps), conc);
	}

	private void removeTrivialInvariants() {
		List<Invariant> nontrivial = new ArrayList<Invariant>();

		assertInductiveTransition(0);
		SolverResult result;
		do {
			result = solver.query(conjoinIds(getInvariantIds(), getInductiveIndex(0)));

			if (result.getResult() == null) {
				throw new JKindException("Unknown result from solver");
			} else if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				int n = getN(model);
				Iterator<Invariant> iterator = possibleInvariants.iterator();
				while (iterator.hasNext()) {
					Invariant inv = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue(inv.id, n);
					if (!v.getBool()) {
						iterator.remove();
						nontrivial.add(inv);
					}
				}
			}
		} while (!possibleInvariants.isEmpty() && result.getResult() != Result.UNSAT);

		possibleInvariants = nontrivial;
	}

	private void sendInvariants(List<Invariant> invariants) {
		List<Sexp> sexps = new ArrayList<Sexp>();
		for (Invariant inv : invariants) {
			sexps.add(inv.sexp);
		}
		inductiveProcess.incomming.add(new InvariantMessage(sexps));
	}
}
