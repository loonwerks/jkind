package jkind.engines;

import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.EngineType;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.interval.ModelGeneralizer;
import jkind.interval.ModelGeneralizer.AlgebraicLoopException;
import jkind.solvers.Model;
import jkind.translation.Specification;

public class IntervalGeneralizationEngine extends Engine {
	public static final String NAME = "interval-generalization";
	
	public IntervalGeneralizationEngine(Specification spec, JKindSettings settings,
			Director director) {
		super(NAME, spec, settings, director);
	}

	@Override
	protected void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private void generalize(InvalidMessage im) {
		for (String property : im.invalid) {
			if (properties.remove(property)) {
				try {
					ModelGeneralizer generalizer = new ModelGeneralizer(spec, property, im.model,
							im.length);
					Model generalized = generalizer.generalize();
					sendInvalid(property, generalized, im);
				} catch (AlgebraicLoopException e) {
					sendInvalid(property, im.model, im);
				}
			}
		}
	}

	private void sendInvalid(String property, Model model, InvalidMessage im) {
		Itinerary itinerary = im.getNextItinerary();
		director.broadcast(new InvalidMessage(im.source, property, im.length, model, itinerary));
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		if (im.getNextDestination() == EngineType.INTERVAL_GENERALIZATION) {
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
