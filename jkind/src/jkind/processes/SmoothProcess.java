package jkind.processes;

import java.util.Set;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.lustre.VarDecl;
import jkind.processes.messages.InvalidMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.StopMessage;
import jkind.sexp.Cons;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.translation.Specification;
import jkind.util.StreamIndex;

public class SmoothProcess extends Process {
	private Process cexProcess;

	public SmoothProcess(Specification spec, JKindSettings settings, Director director) {
		super("Smoothing", spec, settings, director);
	}

	@Override
	public void main() {
		waitForMessage();
	}

	public void setCounterexampleProcess(Process cexProcess) {
		this.cexProcess = cexProcess;
	}

	private void waitForMessage() {
		try {
			while (true) {
				Message message = incoming.take();
				if (message instanceof InvalidMessage) {
					InvalidMessage im = (InvalidMessage) message;
					for (String property : im.invalid) {
						smooth(property, im.k);
					}
				} else if (message instanceof StopMessage) {
					return;
				} else {
					throw new JKindException("Unknown message type in reduce invariants process: "
							+ message.getClass().getCanonicalName());
				}
			}
		} catch (InterruptedException e) {
			throw new JKindException("Interrupted while waiting for message", e);
		}
	}

	private void smooth(String property, int k) {
		debug("Smoothing: " + property);
		Set<String> relevant = spec.dependencyMap.get(property);

		solver.push();

		createVariables(-1);
		for (int i = 0; i < k; i++) {
			createVariables(i);
			assertBaseTransition(i);
			if (i > 0) {
				assertDeltaCost(i, relevant);
			}
		}

		Result result = solver.maxsatQuery(new StreamIndex(property, k - 1).getEncoded());
		if (!(result instanceof SatResult)) {
			throw new JKindException("Failed to recreate counterexample in smoother");
		}

		Model smoothModel = ((SatResult) result).getModel();
		solver.pop();
		sendCounterexample(property, k, smoothModel);
	}

	private void assertDeltaCost(int k, Set<String> relevant) {
		for (VarDecl input : spec.node.inputs) {
			if (relevant.contains(input.id)) {
				Symbol prev = new StreamIndex(input.id, k - 1).getEncoded();
				Symbol curr = new StreamIndex(input.id, k).getEncoded();
				solver.weightedAssert(new Cons("=", prev, curr), 1);
			}
		}
	}

	private void sendCounterexample(String property, int k, Model model) {
		debug("Sending " + property);
		InvalidMessage im = new InvalidMessage(property, k, model);

		if (cexProcess != null) {
			cexProcess.incoming.add(im);
		} else {
			director.incoming.add(im);
		}
	}
}
