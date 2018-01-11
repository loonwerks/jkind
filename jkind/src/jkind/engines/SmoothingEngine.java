package jkind.engines;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.EngineType;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Symbol;
import jkind.slicing.Dependency;
import jkind.slicing.DependencySet;
import jkind.solvers.MaxSatSolver;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.translation.Specification;
import jkind.util.StreamIndex;

public class SmoothingEngine extends SolverBasedEngine {
	public static final String NAME = "smoothing";
	private MaxSatSolver maxSatSolver;

	public SmoothingEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
	}

	@Override
	protected void initializeSolver() {
		super.initializeSolver();
		maxSatSolver = (MaxSatSolver) solver;
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private void smooth(InvalidMessage im) {
		for (String property : im.invalid) {
			if (properties.remove(property)) {
				smooth(property, im);
			}
		}
	}

	private void smooth(String property, InvalidMessage im) {
		comment("Smoothing: " + property);
		DependencySet relevant = spec.dependencyMap.get(property);

		solver.push();

		createVariables(-1);
		for (int i = 0; i < im.length; i++) {
			createVariables(i);
			assertBaseTransition(i);
			if (i > 0) {
				assertDeltaCost(i, relevant);
			}
		}

		Result result = maxSatSolver.maxsatQuery(new StreamIndex(property, im.length - 1)
				.getEncoded());
		if (!(result instanceof SatResult)) {
			throw new JKindException("Failed to recreate counterexample in smoother");
		}

		Model smoothModel = ((SatResult) result).getModel();
		solver.pop();
		sendCounterexample(property, smoothModel, im);
	}

	private void assertDeltaCost(int k, DependencySet relevant) {
		for (VarDecl input : spec.node.inputs) {
			if (relevant.contains(Dependency.variable(input.id))) {
				Symbol prev = new StreamIndex(input.id, k - 1).getEncoded();
				Symbol curr = new StreamIndex(input.id, k).getEncoded();
				maxSatSolver.assertSoft(new Cons("=", prev, curr));
			}
		}
	}

	private void sendCounterexample(String property, Model model, InvalidMessage im) {
		comment("Sending " + property);
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
		if (im.getNextDestination() == EngineType.SMOOTHING) {
			smooth(im);
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
