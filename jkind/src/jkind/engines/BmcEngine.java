package jkind.engines;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.translation.Specification;
import jkind.util.StreamIndex;

public class BmcEngine extends SolverBasedEngine {
	public static final String NAME = "bmc";
	private List<String> validProperties = new ArrayList<>();

	public BmcEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
	}

	@Override
	public void main() {
		createVariables(-1);
		for (int k = 0; k < settings.n; k++) {
			comment("K = " + (k + 1));
			processMessages();
			if (properties.isEmpty()) {
				return;
			}
			createVariables(k);
			assertBaseTransition(k);
			checkProperties(k);
			assertProperties(k);
		}
		sendUnknown(properties);
	}

	private void checkProperties(int k) {
		Result result;
		do {
			result = solver.query(StreamIndex.conjoinEncodings(properties, k));

			if (result instanceof SatResult || result instanceof UnknownResult) {
				Model model = getModel(result);
				if (model == null) {
					sendUnknown(properties);
					properties.clear();
					break;
				}

				List<String> bad = getFalseProperties(properties, k, model);
				properties.removeAll(bad);

				if (result instanceof SatResult) {
					sendInvalid(bad, k, model);
				} else {
					sendUnknown(bad);
				}
			}
		} while (!properties.isEmpty() && result instanceof SatResult);

		sendBaseStep(k);
	}

	private void sendInvalid(List<String> invalid, int k, Model model) {
		Itinerary itinerary = director.getInvalidMessageItinerary();
		director.broadcast(new InvalidMessage(getName(), invalid, k + 1, model, itinerary));
	}

	private void sendBaseStep(int k) {
		director.broadcast(new BaseStepMessage(k + 1, properties));
	}

	private void sendUnknown(List<String> unknown) {
		director.receiveMessage(new UnknownMessage(getName(), unknown));
	}

	private void assertProperties(int k) {
		assertProperties(properties, k);
		assertProperties(validProperties, k);
	}

	private void assertProperties(List<String> properties, int k) {
		for (String prop : properties) {
			solver.assertSexp(new StreamIndex(prop, k).getEncoded());
		}
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		properties.removeAll(im.invalid);
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		properties.removeAll(vm.valid);
		validProperties.addAll(vm.valid);
	}
}
