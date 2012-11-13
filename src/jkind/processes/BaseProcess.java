package jkind.processes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.JKindException;
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
import jkind.translation.Specification;

public class BaseProcess extends Process {
	private InductiveProcess inductiveProcess;

	public BaseProcess(Specification spec, Director director) {
		super(spec, director);
		setScratch(spec.filename + ".yc_base");
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
	}

	@Override
	public void main() {
		for (int k = 1; k <= kMax; k++) {
			debug("K = " + k);
			processMessages();
			if (properties.isEmpty()) {
				break;
			}
			assertTransition(k);
			checkProperties(k);
			assertProperties(k);
		}
	}

	private void processMessages() {
		while (!incoming.isEmpty()) {
			Message message = incoming.poll();
			if (message instanceof ValidMessage) {
				ValidMessage validMessage = (ValidMessage) message;
				properties.removeAll(validMessage.valid);
			} else {
				throw new JKindException("Unknown message type in base process: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private void assertTransition(int k) {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(k - 1))));
	}

	private void checkProperties(int k) {
		SolverResult result;
		do {
			result = solver.query(conjoinStreams(properties, Sexp.fromInt(k - 1)));

			if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				BigInteger index = BigInteger.valueOf(k - 1);
				List<String> invalid = new ArrayList<String>();
				Iterator<String> iterator = properties.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					BoolValue v = (BoolValue) model.getFunctionValue("$" + p, index);
					if (!v.getBool()) {
						invalid.add(p);
						iterator.remove();
					}
				}
				sendInvalid(invalid, k, model);
			}
		} while (!properties.isEmpty() && result.getResult() == Result.SAT);

		sendBaseStep(k);
	}

	private void sendInvalid(List<String> invalid, int k, Model model) {
		director.incoming.add(new CounterexampleMessage(invalid, k, model));

		if (inductiveProcess != null) {
			inductiveProcess.incoming.add(new InvalidMessage(invalid));
		}
	}

	private void sendBaseStep(int k) {
		if (inductiveProcess != null) {
			inductiveProcess.incoming.add(new BaseStepMessage(k));
		}
	}

	private void assertProperties(int k) {
		if (!properties.isEmpty()) {
			solver.send(new Cons("assert", conjoinStreams(properties, Sexp.fromInt(k - 1))));
		}
	}
}
