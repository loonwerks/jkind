package jkind.processes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.processes.messages.BaseStepMessage;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.InvalidMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.ValidMessage;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;
import jkind.solvers.SolverResult;
import jkind.solvers.SolverResult.Result;
import jkind.translation.Keywords;
import jkind.translation.Lustre2Sexps;

public class BaseProcess extends Process {
	private InductiveProcess inductiveProcess;

	public BaseProcess(List<String> properties, Lustre2Sexps translation, Director director) {
		super(properties, translation, director);
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
	}

	@Override
	public void run() {
		try {
			initializeSolver();

			for (int k = 1; k <= kMax; k++) {
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
				properties.removeAll(validMessage.valid);
			} else {
				throw new IllegalArgumentException("Unknown message type in base process: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private void assertTransition(int k) throws IOException {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(k-1))));
	}

	private void checkProperties(int k) throws IOException {
		List<String> invalid = new ArrayList<String>();

		SolverResult result;
		do {
			result = solver.query(conjoin(properties, Sexp.fromInt(k-1)));

			if (result.getResult() == null) {
				throw new IllegalArgumentException("Unknown result from solver");
			} else if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				Iterator<String> iterator = properties.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue(p, k-1);
					if (!v.getBool()) {
						invalid.add(p);
						iterator.remove();
					}
				}
				sendInvalid(invalid, k, model);
				invalid = new ArrayList<String>();
			}
		} while (!properties.isEmpty() && result.getResult() == Result.SAT);
		
		sendBaseStep(k);
	}

	private void sendInvalid(List<String> invalid, int k, Model model) {
		director.incomming.add(new CounterexampleMessage(invalid, k, model));
		
		if (inductiveProcess != null) {
			inductiveProcess.incomming.add(new InvalidMessage(invalid));
		}
	}
	
	private void sendBaseStep(int k) {
		inductiveProcess.incomming.add(new BaseStepMessage(k));
	}
}
