package jkind.processes;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.invariant.Candidate;
import jkind.invariant.CandidateGenerator;
import jkind.invariant.Graph;
import jkind.invariant.Invariant;
import jkind.processes.messages.InvariantMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.StopMessage;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.translation.Specification;

public class InvariantProcess extends Process {
	private InductiveProcess inductiveProcess;

	public InvariantProcess(Specification spec, JKindSettings settings) {
		super("Invariant", spec, settings, null);
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
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

	private boolean checkForStopMessage() {
		while (!incoming.isEmpty()) {
			Message message = incoming.poll();
			if (message instanceof StopMessage) {
				throw new StopException();
			} else {
				throw new JKindException("Unknown message type in inductive process: "
						+ message.getClass().getCanonicalName());
			}
		}
		return false;
	}

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
			checkForStopMessage();

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
			checkForStopMessage();

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
		inductiveProcess.incoming.add(new InvariantMessage(invs));
	}
}
