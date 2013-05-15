package jkind.processes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.invariant.Candidate;
import jkind.invariant.CandidateGenerator;
import jkind.invariant.Graph;
import jkind.invariant.Invariant;
import jkind.lustre.Type;
import jkind.processes.messages.InvariantMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.StopMessage;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.NumericValue;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.StreamDecl;
import jkind.solvers.StreamDef;
import jkind.solvers.VarDecl;
import jkind.translation.Keywords;
import jkind.translation.Specification;

public class InvariantProcess extends Process {
	private InductiveProcess inductiveProcess;
	private Map<String, StreamDef> definitions;
	private Map<String, StreamDecl> declarations;

	public InvariantProcess(Specification spec) {
		super("Invariant", spec, null);
		definitions = new HashMap<String, StreamDef>();
		declarations = new HashMap<String, StreamDecl>();
		for (StreamDecl decl : spec.translation.getDeclarations()) {
			declarations.put(decl.getId().toString(), decl);
		}
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
	}

	@Override
	protected void initializeSolver() {
		super.initializeSolver();
		solver.send(new VarDecl(Keywords.N, Type.INT));
	}

	@Override
	public void main() {
		try {
			Graph graph = createGraph();
			if (graph.isTrivial()) {
				debug("No invariants proposed");
				return;
			}

			for (int k = 1; k <= kMax; k++) {
				debug("K = " + k);

				refineBaseStep(k, graph);
				if (graph.isTrivial()) {
					debug("No invariants remaining after base step");
					return;
				}

				assertInductiveTransition(k);
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
		Graph graph = new Graph(candidates);
		defineCandidates(candidates);
		return graph;
	}

	private void defineCandidates(List<Candidate> candidates) {
		for (Candidate candidate : candidates) {
			definitions.put(candidate.def.getId().toString(), candidate.def);
			solver.send(candidate.def);
		}
	}

	private void refineBaseStep(int k, Graph graph) {
		solver.push();
		Result result;

		for (int i = 0; i < k; i++) {
			assertBaseTransition(i);
		}

		do {
			checkForStopMessage();
			result = solver.query(graph.toInvariant(Sexp.fromInt(k - 1)));

			if (result instanceof SatResult) {
				Model model = getModel(result);
				graph.refine(model, BigInteger.valueOf(k - 1));
				debug("Base step refinement, graph size = " + graph.size());
			}
		} while (!graph.isTrivial() && result instanceof SatResult);

		solver.pop();
	}

	private Model getModel(Result result) {
		Model model = ((SatResult) result).getModel();
		model.setDefinitions(definitions);
		model.setDeclarations(declarations);
		return model;
	}

	private void assertBaseTransition(int i) {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(i))));
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
				Model model = getModel(result);
				BigInteger index = getN(model).add(BigInteger.valueOf(k));
				graph.refine(model, index);
				debug("Inductive step refinement, graph size = " + graph.size());
			}
		} while (!graph.isTrivial() && result instanceof SatResult);

		solver.pop();
		return graph;
	}

	private void assertInductiveTransition(int k) {
		solver.send(new Cons("assert", new Cons(Keywords.T, getInductiveIndex(k))));
	}

	private Sexp getInductiveIndex(int offset) {
		return new Cons("+", Keywords.N, Sexp.fromInt(offset));
	}

	private BigInteger getN(Model model) {
		NumericValue value = (NumericValue) model.getValue(Keywords.N);
		return new BigInteger(value.toString());
	}

	private Sexp getInductiveQuery(int k, Graph graph) {
		List<Sexp> hyps = new ArrayList<Sexp>();
		for (int i = 0; i < k; i++) {
			hyps.add(graph.toInvariant(getInductiveIndex(i)));
		}
		Sexp conc = graph.toInvariant(getInductiveIndex(k));

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
