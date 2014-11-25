package jkind.engines;

import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.interval.ModelGeneralizer;
import jkind.interval.ModelGeneralizer.AlgebraicLoopException;
import jkind.solvers.Model;
import jkind.translation.Specification;

public class IntervalGeneralizationEngine extends Engine {
	public IntervalGeneralizationEngine(Specification spec, JKindSettings settings,
			Director director) {
		super("interval-generalization", spec, settings, director);
	}
	
	@Override
	protected void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private boolean shouldHandle(InvalidMessage im) {
		return director.nextResponsible(im) == EngineType.INTERVAL_GENERALIZATION;
	}

	private void generalize(InvalidMessage im) {
		for (String property : im.invalid) {
			try {
				ModelGeneralizer generalizer = new ModelGeneralizer(spec, property, im.model, im.length);
				Model generalized = generalizer.generalize();
				sendInvalid(property, im.length, generalized);
			} catch (AlgebraicLoopException e) {
				sendInvalid(property, im.length, im.model);
			}
		}
	}

	private void sendInvalid(String property, int k, Model model) {
		director.broadcast(new InvalidMessage(EngineType.INTERVAL_GENERALIZATION, property, k, model), this);
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		if (shouldHandle(im)) {
			generalize(im);
			properties.removeAll(im.invalid);
		}
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		properties.removeAll(um.unknown);
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		properties.removeAll(vm.valid);
	}
}
