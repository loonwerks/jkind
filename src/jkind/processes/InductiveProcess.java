package jkind.processes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.JKindException;
import jkind.processes.messages.BaseStepMessage;
import jkind.processes.messages.InvalidMessage;
import jkind.processes.messages.InvariantMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.ValidMessage;
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

public class InductiveProcess extends Process {
	private int kLimit = 0;
	private BaseProcess baseProcess;
	private List<Sexp> invariants = new ArrayList<Sexp>();

	public InductiveProcess(String filename, List<String> properties, Lustre2Sexps translation,
			Director director) {
		super(properties, translation, director);
		setScratch(filename + ".yc_induct");
	}

	public void setBaseProcess(BaseProcess baseProcess) {
		this.baseProcess = baseProcess;
	}

	@Override
	public void run() {
		try {
			initializeSolver();

			assertTransitionAndInvariants(0);
			for (int k = 1; k <= kMax; k++) {
				processMessagesAndWait(k);
				assertTransitionAndInvariants(k);
				checkProperties(k);
				if (properties.isEmpty()) {
					break;
				}
			}
		} catch (JKindException e) {
			System.out.println("Inductive process failed: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (solver != null) {
				solver.stop();
			}
		}
	}

	protected void initializeSolver() {
		super.initializeSolver();
		solver.send(new Cons("define", Keywords.N, new Symbol("::"), new Symbol("nat")));
	}

	private void processMessagesAndWait(int k) {
		try {
			while (!incomming.isEmpty() || k > kLimit) {
				Message message = incomming.take();
				if (message instanceof InvalidMessage) {
					InvalidMessage invalidMessage = (InvalidMessage) message;
					properties.removeAll(invalidMessage.invalid);
				} else if (message instanceof BaseStepMessage) {
					BaseStepMessage baseStepMessage = (BaseStepMessage) message;
					kLimit = baseStepMessage.step;
				} else if (message instanceof InvariantMessage) {
					InvariantMessage invariantMessage = (InvariantMessage) message;
					invariants.addAll(invariantMessage.invariants);
					assertNewInvariants(invariantMessage.invariants, k);
				} else {
					throw new JKindException("Unknown message type in inductive process: "
							+ message.getClass().getCanonicalName());
				}
			}
		} catch (InterruptedException e) {
			throw new JKindException("Interrupted while waiting for message", e);
		}
	}

	private void assertNewInvariants(List<Sexp> invariants, int k) {
		for (int i = 0; i < k; i++) {
			solver.send(new Cons("assert", conjoin(invariants, getIndex(i))));
		}
	}

	private void assertTransitionAndInvariants(int offset) {
		solver.send(new Cons("assert", new Cons(Keywords.T, getIndex(offset))));
		if (!invariants.isEmpty()) {
			solver.send(new Cons("assert", conjoin(invariants, getIndex(offset))));
		}
	}

	private void checkProperties(int k) {
		List<String> possiblyValid = new ArrayList<String>(properties);

		while (!possiblyValid.isEmpty()) {
			SolverResult result = solver.query(getInductiveQuery(k, possiblyValid));

			if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				int n = getN(model);
				Iterator<String> iterator = possiblyValid.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue("$" + p, n + k);
					if (!v.getBool()) {
						iterator.remove();
					}
				}
			} else if (result.getResult() == Result.UNSAT) {
				sendValid(k, possiblyValid);
				properties.removeAll(possiblyValid);
				return;
			}
		}
	}

	private int getN(Model model) {
		NumericValue value = (NumericValue) model.getValue(Keywords.N);
		return Integer.parseInt(value.toString());
	}

	private Sexp getInductiveQuery(int k, List<String> possiblyValid) {
		List<Sexp> hyps = new ArrayList<Sexp>();
		for (int i = 0; i < k; i++) {
			hyps.add(conjoinStreams(possiblyValid, getIndex(i)));
		}
		Sexp conc = conjoinStreams(possiblyValid, getIndex(k));

		return new Cons("=>", new Cons("and", hyps), conc);
	}

	private Sexp getIndex(int offset) {
		return new Cons("+", Keywords.N, Sexp.fromInt(offset));
	}

	private void sendValid(int k, List<String> valid) {
		baseProcess.incomming.add(new ValidMessage(k, valid));
		director.incomming.add(new ValidMessage(k, valid));
	}
}
