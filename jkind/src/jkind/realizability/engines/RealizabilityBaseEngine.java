package jkind.realizability.engines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.JKindException;
import jkind.JRealizabilitySettings;
import jkind.engines.StopException;
import jkind.realizability.engines.messages.BaseStepMessage;
import jkind.realizability.engines.messages.Message;
import jkind.realizability.engines.messages.RealizableMessage;
import jkind.realizability.engines.messages.UnknownMessage;
import jkind.realizability.engines.messages.UnrealizableMessage;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.translation.Specification;
import jkind.util.StreamIndex;

public class RealizabilityBaseEngine extends RealizabilityEngine {
	private RealizabilityExtendEngine extendEngine;

	public RealizabilityBaseEngine(Specification spec, JRealizabilitySettings settings,
			RealizabilityDirector director) {
		super("base", spec, settings, director);
	}

	public void setExtendEngine(RealizabilityExtendEngine extendEngine) {
		this.extendEngine = extendEngine;
	}

	@Override
	public void main() {
		try {
			createVariables(-1);
			for (int k = 0; k < settings.n; k++) {
				comment("K = " + (k + 1));
				processMessages();
				createVariables(k);
				assertTransition(k);
				checkRealizable(k);
				assertProperties(k);
			}
		} catch (StopException se) {
		}
	}

	private void processMessages() {
		while (!incoming.isEmpty()) {
			Message message = incoming.poll();
			if (message instanceof RealizableMessage) {
				throw new StopException();
			}
			throw new JKindException("Unknown message type in base process: "
					+ message.getClass().getCanonicalName());
		}
	}

	private void assertTransition(int k) {
		solver.assertSexp(getTransition(k, k == 0));
	}

	private void assertProperties(int k) {
		solver.assertSexp(StreamIndex.conjoinEncodings(spec.node.properties, k));
	}

	private void checkRealizable(int k) {
		Result result = solver.realizabilityQuery(getRealizabilityOutputs(k),
				getTransition(k, k == 0), StreamIndex.conjoinEncodings(spec.node.properties, k));

		if (result instanceof UnsatResult) {
			sendBaseStep(k);
			return;
		}

		if (result instanceof SatResult) {
			Model model = ((SatResult) result).getModel();
			if (settings.reduce) {
				reduceAndSendUnrealizable(k, model);
			} else {
				sendUnrealizable(k, model);
			}
		} else if (result instanceof UnknownResult) {
			sendUnknown();
		}
		throw new StopException();
	}

	private void reduceAndSendUnrealizable(int k, Model model) {
		Sexp realizabilityOutputs = getRealizabilityOutputs(k);
		Sexp transition = getTransition(k, k == 0);
		List<String> properties = new ArrayList<>(spec.node.properties);

		for (String curr : spec.node.properties) {
			properties.remove(curr);
			Result result = solver.realizabilityQuery(realizabilityOutputs, transition,
					StreamIndex.conjoinEncodings(properties, k));
			
			if (result instanceof SatResult) {
				model = ((SatResult) result).getModel();
			} else {
				properties.add(curr);
			}
		}

		sendUnrealizable(k, model, properties);
	}

	private void sendBaseStep(int k) {
		BaseStepMessage bsm = new BaseStepMessage(k + 1);
		director.incoming.add(bsm);
		extendEngine.incoming.add(bsm);
	}

	private void sendUnrealizable(int k, Model model) {
		sendUnrealizable(k, model, Collections.emptyList());
	}

	private void sendUnrealizable(int k, Model model, List<String> properties) {
		UnrealizableMessage im = new UnrealizableMessage(k + 1, model, properties);
		director.incoming.add(im);
		extendEngine.incoming.add(im);
	}

	private void sendUnknown() {
		UnknownMessage um = new UnknownMessage();
		director.incoming.add(um);
		extendEngine.incoming.add(um);
	}
}