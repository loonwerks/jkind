package jkind.processes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.invariant.Candidate;
import jkind.invariant.CandidateGenerator;
import jkind.invariant.Graph;
import jkind.invariant.Invariant;
import jkind.processes.messages.InvariantMessage;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.NumericValue;
import jkind.solvers.SolverResult;
import jkind.solvers.SolverResult.Result;
import jkind.translation.Keywords;
import jkind.translation.Specification;

public class InvariantProcess extends Process {
	private InductiveProcess inductiveProcess;

	public InvariantProcess(Specification spec) {
		super(spec, null);
		setScratch(spec.filename + ".yc_inv");
		this.incoming = null;
	}

	public void setInductiveProcess(InductiveProcess inductiveProcess) {
		this.inductiveProcess = inductiveProcess;
	}

	@Override
	protected void initializeSolver() {
		super.initializeSolver();
		solver.send(new Cons("define", Keywords.N, new Symbol("::"), new Symbol("nat")));
	}

	@Override
	public void main() {
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
			solver.send(candidate.getDeclaration());
			solver.send(candidate.getDefinition());
		}
	}

	private void refineBaseStep(int k, Graph graph) {
		solver.push();
		SolverResult result;
		
		for (int i = 0; i < k; i++) {
			assertBaseTransition(i);
		}

		do {
			result = solver.query(graph.toInvariant(Sexp.fromInt(k - 1)));

			if (result.getResult() == Result.SAT) {
				graph.refine(result.getModel(), BigInteger.valueOf(k - 1));
				debug("Base step refinement, graph size = " + graph.size());
			}
		} while (!graph.isTrivial() && result.getResult() == Result.SAT);
		
		solver.pop();
	}

	private void assertBaseTransition(int i) {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(i))));
	}

	private Graph refineInductiveStep(int k, Graph original) {
		solver.push();
		Graph graph = new Graph(original);
		SolverResult result;
		
		for (int i = 0; i <= k; i++) {
			assertInductiveTransition(i);
		}
		
		do {
			result = solver.query(getInductiveQuery(k, graph));

			if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				BigInteger index = getN(model).add(BigInteger.valueOf(k));
				graph.refine(model, index);
				debug("Inductive step refinement, graph size = " + graph.size());
			}
		} while (!graph.isTrivial() && result.getResult() != Result.UNSAT);
		
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
