package jkind.engine;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Symbol;
import jkind.slicing.DependencySet;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.yices.YicesSolver;
import jkind.translation.Specification;
import jkind.util.StreamIndex;

public class SmoothingEngine extends SolverBasedEngine {
	private YicesSolver yicesSolver;

	public SmoothingEngine(Specification spec, JKindSettings settings, Director director) {
		super("smoothing", spec, settings, director);
	}

	@Override
	protected void initializeSolver() {
		super.initializeSolver();
		yicesSolver = (YicesSolver) solver;
	}
	
	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private void smooth(InvalidMessage im) {
		for (String property : im.invalid) {
			smooth(property, im.k);
		}
	}

	private void smooth(String property, int k) {
		comment("Smoothing: " + property);
		DependencySet relevant = spec.dependencyMap.get(property);

		yicesSolver.push();

		createVariables(-1);
		for (int i = 0; i < k; i++) {
			createVariables(i);
			assertBaseTransition(i);
			if (i > 0) {
				assertDeltaCost(i, relevant);
			}
		}

		Result result = yicesSolver.maxsatQuery(new StreamIndex(property, k - 1).getEncoded());
		if (!(result instanceof SatResult)) {
			throw new JKindException("Failed to recreate counterexample in smoother");
		}

		Model smoothModel = ((SatResult) result).getModel();
		yicesSolver.pop();
		sendCounterexample(property, k, smoothModel);
	}

	private void assertDeltaCost(int k, DependencySet relevant) {
		for (VarDecl input : spec.node.inputs) {
			if (relevant.contains(input.id)) {
				Symbol prev = new StreamIndex(input.id, k - 1).getEncoded();
				Symbol curr = new StreamIndex(input.id, k).getEncoded();
				yicesSolver.weightedAssert(new Cons("=", prev, curr), 1);
			}
		}
	}

	private void sendCounterexample(String property, int k, Model model) {
		comment("Sending " + property);
		director.broadcast(new InvalidMessage(EngineType.SMOOTHING, property, k, model), this);
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
			smooth(im);
			properties.removeAll(im.invalid);
		}
	}

	private boolean shouldHandle(InvalidMessage im) {
		return director.nextResponsible(im) == EngineType.SMOOTHING;
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
