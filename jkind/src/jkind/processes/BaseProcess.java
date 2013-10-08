package jkind.processes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.processes.messages.BaseStepMessage;
import jkind.processes.messages.InvalidMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.StopMessage;
import jkind.processes.messages.ValidMessage;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.translation.Keywords;
import jkind.translation.Specification;
import jkind.util.SexpUtil;

public class BaseProcess extends Process {
	private InductiveProcess inductiveProcess;
	private Process cexProcess;

	public BaseProcess(Specification spec, JKindSettings settings, Director director) {
		super("Base", spec, settings, director);
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
	}

	public void setCounterexampleProcess(Process cexProcess) {
		this.cexProcess = cexProcess;
	}

	@Override
	public void main() {
		for (int k = 1; k <= settings.n; k++) {
			debug("K = " + k);
			processMessages();
			if (properties.isEmpty()) {
				break;
			}
			assertTransition(k);
			checkProperties(k);
			assertProperties(k);
		}
		sendStop();
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
		Result result;
		do {
			result = solver.query(SexpUtil.conjoinStreams(properties, Sexp.fromInt(k - 1)));

			if (result instanceof SatResult) {
				Model model = ((SatResult) result).getModel();
				BigInteger index = BigInteger.valueOf(k - 1);
				List<String> invalid = new ArrayList<>();
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
		} while (!properties.isEmpty() && result instanceof SatResult);

		sendBaseStep(k);
	}

	private void sendInvalid(List<String> invalid, int k, Model model) {
		InvalidMessage im = new InvalidMessage(invalid, k, model);
		if (cexProcess != null) {
			cexProcess.incoming.add(im);
		} else {
			director.incoming.add(im);
		}

		if (inductiveProcess != null) {
			inductiveProcess.incoming.add(im);
		}
	}

	private void sendBaseStep(int k) {
		if (inductiveProcess != null) {
			inductiveProcess.incoming.add(new BaseStepMessage(k));
		}
	}

	private void assertProperties(int k) {
		if (!properties.isEmpty()) {
			solver.send(new Cons("assert", SexpUtil.conjoinStreams(properties, Sexp.fromInt(k - 1))));
		}
	}

	private void sendStop() {
		if (cexProcess != null) {
			cexProcess.incoming.add(new StopMessage());
		}
	}
}
