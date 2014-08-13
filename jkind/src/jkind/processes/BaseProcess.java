package jkind.processes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.lustre.values.BooleanValue;
import jkind.processes.messages.BaseStepMessage;
import jkind.processes.messages.InvalidMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.StopMessage;
import jkind.processes.messages.UnknownMessage;
import jkind.processes.messages.ValidMessage;
import jkind.sexp.Cons;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.translation.Specification;
import jkind.util.SexpUtil;

public class BaseProcess extends Process {
	private InductiveProcess inductiveProcess;
	private Process cexProcess;
	private List<String> validProperties = new ArrayList<>();

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
		createVariables(-1);
		for (int k = 0; k < settings.n; k++) {
			debug("K = " + (k + 1));
			processMessages();
			if (properties.isEmpty()) {
				break;
			}
			createVariables(k);
			assertBaseTransition(k);
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
				validProperties.addAll(validMessage.valid);
			} else {
				throw new JKindException("Unknown message type in base process: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private void checkProperties(int k) {
		Result result;
		do {
			result = solver.query(SexpUtil.conjoinOffsets(properties, k));

			if (result instanceof SatResult) {
				Model model = ((SatResult) result).getModel();
				List<String> invalid = new ArrayList<>();
				Iterator<String> iterator = properties.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					BooleanValue v = (BooleanValue) model.getValue(SexpUtil.offset(p, k));
					if (!v.value) {
						invalid.add(p);
						iterator.remove();
					}
				}
				sendInvalid(invalid, k, model);
			} else if (result instanceof UnknownResult) {
				sendUnknown(properties);
				properties.clear();
			}
		} while (!properties.isEmpty() && result instanceof SatResult);

		sendBaseStep(k);
	}

	private void sendInvalid(List<String> invalid, int k, Model model) {
		InvalidMessage im = new InvalidMessage(invalid, k + 1, model);
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
			inductiveProcess.incoming.add(new BaseStepMessage(k + 1));
		}
	}

	private void sendUnknown(List<String> unknown) {
		UnknownMessage um = new UnknownMessage(unknown);
		director.incoming.add(um);
		if (inductiveProcess != null) {
			inductiveProcess.incoming.add(um);
		}
	}

	private void assertProperties(int k) {
		if (!properties.isEmpty()) {
			solver.send(new Cons("assert", SexpUtil.conjoinOffsets(properties, k)));
		}
		if (!validProperties.isEmpty()) {
			solver.send(new Cons("assert", SexpUtil.conjoinOffsets(validProperties, k)));
		}
	}

	private void sendStop() {
		if (cexProcess != null) {
			cexProcess.incoming.add(new StopMessage());
		}
	}
}
