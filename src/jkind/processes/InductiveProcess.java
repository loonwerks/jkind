package jkind.processes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	public InductiveProcess(List<String> properties, Lustre2Sexps translation) {
		super(properties, translation);
	}

	public void setBaseProcess(BaseProcess baseProcess) {
		this.baseProcess = baseProcess;
	}

	/*
	 * NOTE: The inductive process starts at k = 1 which is regular induction,
	 * while the base process starts at step 0 which is the initial state. This
	 * means some step values need to be converted when passed between
	 * processes.
	 */
	@Override
	public void run() {
		try {
			initializeSolver();

			assertTransition(0);
			for (int k = 1; k <= kMax; k++) {
				processMessagesAndWait(k);
				assertTransition(k);
				checkProperties(k);
				if (properties.isEmpty()) {
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Inductive process failed");
			e.printStackTrace();
		} catch (InterruptedException e) {
		}
	}
	
	protected void initializeSolver() throws IOException {
		super.initializeSolver();
		solver.send(new Cons("define", Keywords.N_SYM, new Symbol("::"), new Symbol("nat")));
	}

	private void processMessagesAndWait(int k) throws InterruptedException {
		while (!incomming.isEmpty() || k > kLimit) {
			Message message = incomming.take();
			if (message instanceof InvalidMessage) {
				InvalidMessage invalidMessage = (InvalidMessage) message;
				properties.removeAll(invalidMessage.invalid);
			} else if (message instanceof BaseStepMessage) {
				BaseStepMessage baseStepMessage = (BaseStepMessage) message;
				kLimit = baseStepMessage.step + 1;
			} else {
				throw new IllegalArgumentException("Unknown message type in inductive process: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private void assertTransition(int offset) throws IOException {
		Sexp i = new Cons("+", Keywords.N_SYM, Sexp.fromInt(offset));
		solver.send(new Cons("assert", new Cons(Keywords.T, i)));
	}

	private void checkProperties(int k) throws IOException {
		List<String> possiblyValid = new ArrayList<String>(properties);

		while (!possiblyValid.isEmpty()) {
			SolverResult result = solver.query(getInductiveQuery(k, possiblyValid));
			
			
			if (result.getResult() == null) {
				throw new IllegalArgumentException("Unknown result from solver");
			} else if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				int n = getN(model);
				Iterator<String> iterator = possiblyValid.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue(p, n+k);
					if (!v.getBool()) {
						iterator.remove();
					}
				}
			} else if (result.getResult() == Result.UNSAT) {
				sendMessage(k, possiblyValid);
				properties.removeAll(possiblyValid);
				return;
			}
		}
	}

	private int getN(Model model) {
		NumericValue value = (NumericValue) model.getValue(Keywords.N);
		return Integer.parseInt(value.toString());
	}

	private Sexp getInductiveQuery(int k, List<String> possiblyValid) throws IOException {
		List<Sexp> hyps = new ArrayList<Sexp>();
		for (int i = 0; i < k; i++) {
			hyps.add(conjoin(possiblyValid, getOffset(i)));
		}
		Sexp conc = conjoin(possiblyValid, getOffset(k));
		
		return new Cons("=>", new Cons("and", hyps), conc);
	}
	
	private Sexp getOffset(int i) {
		return new Cons("+", Keywords.N_SYM, Sexp.fromInt(i));
	}

	private void sendMessage(int k, List<String> valid) {
		baseProcess.incomming.add(new ValidMessage(k, valid));
	}
}
