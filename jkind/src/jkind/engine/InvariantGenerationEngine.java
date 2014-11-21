package jkind.engine;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.invariant.Candidate;
import jkind.invariant.CandidateGenerator;
import jkind.invariant.Graph;
import jkind.invariant.Invariant;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.translation.Specification;

public class InvariantGenerationEngine extends Engine {
	public InvariantGenerationEngine(Specification spec, JKindSettings settings, Director director) {
		super("invariant-generation", spec, settings, director);
	}

	@Override
	public void main() {
		try {
			Graph graph = createGraph();
			if (graph.isTrivial()) {
				debug("No invariants proposed");
				return;
			}

			createVariables(-1);
			createVariables(0);
			for (int k = 1; k <= settings.n; k++) {
				debug("K = " + k);

				refineBaseStep(k - 1, graph);
				if (graph.isTrivial()) {
					debug("No invariants remaining after base step");
					return;
				}

				createVariables(k);
				sendInvariant(refineInductiveStep(k, graph));
			}
		} catch (StopException se) {
		}
	}

	private class StopException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	};

	private Graph createGraph() {
		List<Candidate> candidates = new CandidateGenerator(spec).generate();
		debug("Proposed " + candidates.size() + " candidates");
		return new Graph(candidates);
	}

	private void refineBaseStep(int k, Graph graph) {
		solver.push();
		Result result;

		for (int i = 0; i <= k; i++) {
			assertBaseTransition(i);
		}

		do {
			checkForStop();

			result = solver.query(graph.toInvariant(k));

			if (result instanceof SatResult) {
				Model model = ((SatResult) result).getModel();
				graph.refine(model, k);
				debug("Base step refinement, graph size = " + graph.size());
			} else if (result instanceof UnknownResult) {
				throw new StopException();
			}
		} while (!graph.isTrivial() && result instanceof SatResult);

		solver.pop();
	}

	private Graph refineInductiveStep(int k, Graph original) {
		solver.push();
		Graph graph = new Graph(original);
		Result result;

		for (int i = 0; i <= k; i++) {
			assertInductiveTransition(i);
		}

		do {
			checkForStop();

			result = solver.query(getInductiveQuery(k, graph));

			if (result instanceof SatResult) {
				Model model = ((SatResult) result).getModel();
				graph.refine(model, k);
				debug("Inductive step refinement, graph size = " + graph.size());
			}
		} while (!graph.isTrivial() && result instanceof SatResult);

		solver.pop();
		return graph;
	}

	private void checkForStop() {
		processMessages();
		if (properties.isEmpty()) {
			throw new StopException();
		}
	}

	private Sexp getInductiveQuery(int k, Graph graph) {
		List<Sexp> hyps = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			hyps.add(graph.toInvariant(i));
		}
		Sexp conc = graph.toInvariant(k);

		return new Cons("=>", new Cons("and", hyps), conc);
	}

	private void sendInvariant(Graph graph) {
		List<Invariant> invs = graph.toFinalInvariants();
		debug("Sending invariants:");
		for (Invariant invariant : invs) {
			debug("  " + invariant.toString());
		}

		director.broadcast(new InvariantMessage(EngineType.INVARIANT_GENERATION, invs), this);
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
		properties.removeAll(um.unknown);
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		properties.removeAll(vm.valid);
	}
}
