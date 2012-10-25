package jkind.processes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;
import jkind.solvers.SolverResult;
import jkind.solvers.SolverResult.Result;
import jkind.solvers.Value;
import jkind.translation.Keywords;
import jkind.translation.Lustre2Sexps;

public class BaseProcess extends Process {
	private InductiveProcess inductiveProcess;

	public BaseProcess(List<String> properties, Lustre2Sexps translation) {
		super(properties, translation);
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
	}

	@Override
	public void run() {
		try {
			initializeSolver();

			for (int k = 0; k < kMax; k++) {
				processMessages();
				assertTransition(k);
				checkProperties(k);
				if (properties.isEmpty()) {
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Base process failed");
			e.printStackTrace();
		}
	}

	private void processMessages() {
		while (!incomming.isEmpty()) {
			Message message = incomming.poll();
			if (message instanceof ValidMessage) {
				ValidMessage validMessage = (ValidMessage) message;
				for (String p : validMessage.valid) {
					System.out.println("Property " + p + " is valid at k = " + validMessage.k);
				}
			} else {
				throw new IllegalArgumentException("Unknown message type in base process: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private void assertTransition(int i) throws IOException {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(i))));
	}

	private void checkProperties(int i) throws IOException {
		List<String> invalid = new ArrayList<String>();

		SolverResult result;
		do {
			result = solver.query(conjoin(properties, Sexp.fromInt(i)));

			if (result.getResult() == null) {
				throw new IllegalArgumentException("Unknown result from solver");
			} else if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				Iterator<String> iterator = properties.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue(p, i);
					if (!v.getBool()) {
						invalid.add(p);
						iterator.remove();
						printCounterexample(p, i, model);
					}
				}
			}
		} while (!properties.isEmpty() && result.getResult() == Result.SAT);

		sendMessage(i, invalid);
	}

	private void printCounterexample(String p, int i, Model model) {
		System.out.println("Property " + p + " is invalid at step " + i);
		for (String fn : model.getFunctions()) {
			System.out.print(fn + ": ");
			Map<Integer, Value> fnMap = model.getFunction(fn);
			for (int j = 0; j <= i; j++) {
				System.out.print("\t" + fnMap.get(j));
			}
			System.out.println();
		}
	}

	private void sendMessage(int i, List<String> invalid) {
		/*
		 * NOTE: Order is important here. We must send the invalid properties
		 * before we notify the inductive process to move on to the next step.
		 */

		if (inductiveProcess != null) {
			if (!invalid.isEmpty()) {
				inductiveProcess.incomming.add(new InvalidMessage(invalid));
			}
			inductiveProcess.incomming.add(new BaseStepMessage(i));
		}
	}
}
