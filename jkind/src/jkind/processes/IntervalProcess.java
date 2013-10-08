package jkind.processes;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.interval.ModelGeneralizer;
import jkind.interval.ModelGeneralizer.AlgebraicLoopException;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.InvalidMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.StopMessage;
import jkind.results.Counterexample;
import jkind.translation.Specification;

public class IntervalProcess extends Process {
	public IntervalProcess(Specification spec, JKindSettings settings, Director director) {
		super("Interval", spec, settings, director);
	}

	@Override
	protected void main() {
		waitForMessage();
	}

	private void waitForMessage() {
		try {
			while (true) {
				Message message = incoming.take();
				if (message instanceof InvalidMessage) {
					InvalidMessage im = (InvalidMessage) message;
					generalize(im);
				} else if (message instanceof StopMessage) {
					return;
				} else {
					throw new JKindException("Unknown message type in interval generalization process: "
							+ message.getClass().getCanonicalName());
				}
			}
		} catch (InterruptedException e) {
			throw new JKindException("Interrupted while waiting for message", e);
		}

	}

	private void generalize(InvalidMessage im) {
		for (String property : im.invalid) {
			debug("Generalizing: " + property);
			try {
				Counterexample cex = new ModelGeneralizer(spec, property, im.model, im.k).generalize();
				director.incoming.add(new CounterexampleMessage(property, cex));
			} catch (AlgebraicLoopException e) {
				debug("Detected algebraic loop");
				director.incoming.add(new InvalidMessage(property, im.k, im.model));
			}
		}
	}
}
