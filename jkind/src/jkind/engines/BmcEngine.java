package jkind.engines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.values.BooleanValue;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.translation.Specification;
import jkind.util.StreamIndex;

public class BmcEngine extends SolverBasedEngine {
	private List<String> validProperties = new ArrayList<>();

	public BmcEngine(Specification spec, JKindSettings settings, Director director) {
		super("bmc", spec, settings, director);
	}

	@Override
	public void main() {
		createVariables(-1);
		for (int k = 0; k < settings.n; k++) {
			comment("K = " + (k + 1));
			processMessages();
			if (properties.isEmpty()) {
				break;
			}
			createVariables(k);
			assertBaseTransition(k);
			checkProperties(k);
			assertProperties(k);
		}
	}

	private void checkProperties(int k) {
		Result result;
		do {
			result = solver.query(StreamIndex.conjoinEncodings(properties, k));

			if (result instanceof SatResult) {
				Model model = ((SatResult) result).getModel();
				List<String> invalid = new ArrayList<>();
				Iterator<String> iterator = properties.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					StreamIndex si = new StreamIndex(p, k);
					BooleanValue v = (BooleanValue) model.getValue(si);
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
		director.broadcast(new InvalidMessage(EngineType.BMC, invalid, k + 1, model), this);
	}

	private void sendBaseStep(int k) {
		director.broadcast(new BaseStepMessage(EngineType.BMC, k + 1), this);
	}

	private void sendUnknown(List<String> unknown) {
		director.broadcast(new UnknownMessage(EngineType.BMC, unknown), this);
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
