package jkind.processes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.invariant.Candidate;
import jkind.invariant.CandidateGenerator;
import jkind.invariant.Graph;
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
	private Graph graph;
	private Sexp i;

	public InvariantProcess(Specification spec) {
		super(spec, null);
		setScratch(spec.filename + ".yc_inv");
		this.incoming = null;
		this.i = new Symbol("i");
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
		createGraph();
		if (graph.isTrivial()) {
			debug("No invariants proposed");
			return;
		}

		int k = basePhase();
		if (graph.isTrivial()) {
			debug("No invariants survived base phase");
			return;
		} else {
			debug("Finished base phase at K = " + k + ", graph size = " + graph.size());
		}

		inductivePhase(k);
		if (graph.isTrivial()) {
			debug("No invariants survived inductive phase");
			return;
		}

		sendInvariants();
	}

	private void createGraph() {
		List<Candidate> candidates = new CandidateGenerator(spec.typeMap, i).generate();
		graph = new Graph(candidates);
		defineCandidates(candidates);
	}

	private void defineCandidates(List<Candidate> candidates) {
		for (Candidate candidate : candidates) {
			solver.send(candidate.getDeclaration());
			solver.send(candidate.getDefinition());
		}
	}

	private int basePhase() {
		int k = 0;

		solver.push();
		do {
			k = k + 1;
			assertBaseTransition(k);
			debug("K = " + k + ", graph size = " + graph.size());
		} while (!graph.isTrivial() && refineBase(k));
		solver.pop();

		return k;
	}

	private void assertBaseTransition(int k) {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(k - 1))));
	}

	private boolean refineBase(int k) {
		SolverResult result;
		boolean refined = false;

		do {
			result = solver.query(graph.toInvariant(Sexp.fromInt(k - 1)));

			if (result.getResult() == Result.SAT) {
				refined = true;
				graph.refine(result.getModel(), BigInteger.valueOf(k - 1));
				debug("Base step refinement, graph size = " + graph.size());
			}
		} while (!graph.isTrivial() && result.getResult() == Result.SAT);

		return refined;
	}

	private void inductivePhase(int k) {
		solver.push();
		for (int i = 0; i <= k; i++) {
			assertInductiveTransition(i);
		}
		refineInductiveInvariants(k);
		solver.pop();
	}

	private void assertInductiveTransition(int k) {
		solver.send(new Cons("assert", new Cons(Keywords.T, getInductiveIndex(k))));
	}

	private Sexp getInductiveIndex(int offset) {
		return new Cons("+", Keywords.N, Sexp.fromInt(offset));
	}

	private void refineInductiveInvariants(int k) {
		SolverResult result;
		do {
			result = solver.query(getInductiveQuery(k));

			if (result.getResult() == Result.SAT) {
				Model model = result.getModel();
				BigInteger index = getN(model).add(BigInteger.valueOf(k));
				graph.refine(model, index);
				debug("Inductive step refinement, graph size = " + graph.size());
			}
		} while (!graph.isTrivial() && result.getResult() != Result.UNSAT);
	}

	private BigInteger getN(Model model) {
		NumericValue value = (NumericValue) model.getValue(Keywords.N);
		return new BigInteger(value.toString());
	}

	private Sexp getInductiveQuery(int k) {
		List<Sexp> hyps = new ArrayList<Sexp>();
		for (int i = 0; i < k; i++) {
			hyps.add(graph.toInvariant(getInductiveIndex(i)));
		}
		Sexp conc = graph.toInvariant(getInductiveIndex(k));

		return new Cons("=>", new Cons("and", hyps), conc);
	}

	private void sendInvariants() {
		Sexp iType = new Cons(i, new Symbol("::"), new Symbol("nat"));
		Sexp inv = new Cons("lambda", iType, graph.toFinalInvariant(i));
		debug("Sending " + inv);
		inductiveProcess.incoming.add(new InvariantMessage(inv));
	}
}
