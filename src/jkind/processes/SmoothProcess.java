package jkind.processes;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import jkind.JKindException;
import jkind.lustre.VarDecl;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.Message;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.translation.Keywords;
import jkind.translation.Specification;

public class SmoothProcess extends Process {
	public SmoothProcess(Specification spec, Director director) {
		super("Smoothing", spec, director);
	}

	@Override
	protected void initializeSolver() {
		super.initializeSolver();
	}

	@Override
	public void main() {
		waitForMessage();
	}

	private void waitForMessage() {
		try {
			while (true) {
				Message message = incoming.take();
				if (message instanceof CounterexampleMessage) {
					CounterexampleMessage cex = (CounterexampleMessage) message;
					for (String property : cex.invalid) {
						smooth(property, cex.k);
					}
				} else {
					throw new JKindException("Unknown message type in reduce invariants process: "
							+ message.getClass().getCanonicalName());
				}
			}
		} catch (InterruptedException e) {
			throw new JKindException("Interrupted while waiting for message", e);
		}
	}

	private void smooth(String property, int k) {
		debug("Smoothing: " + property);
		Set<String> relevant = spec.dependencyMap.get(property);
		
		solver.push();

		for (int i = 1; i <= k; i++) {
			assertTransition(i);
			if (i > 1) {
				assertDeltaCost(i, relevant);
			}
		}
		
		Result result = solver.maxsatQuery(new Cons("$" + property, Sexp.fromInt(k - 1)));
		if (!(result instanceof SatResult)) {
			throw new JKindException("Failed to recreate counterexample in smoother");
		}
		
		Model smoothModel = ((SatResult) result).getModel();
		solver.pop();
		sendCounterexample(property, k, smoothModel);
	}

	private void assertTransition(int k) {
		solver.send(new Cons("assert", new Cons(Keywords.T, Sexp.fromInt(k - 1))));
	}

	private void assertDeltaCost(int k, Set<String> relevant) {
		for (VarDecl input : spec.node.inputs) {
			if (relevant.contains(input.id)) {
				String id = "$" + input.id;
				Cons prev = new Cons(id, Sexp.fromInt(k - 2));
				Cons curr = new Cons(id, Sexp.fromInt(k - 1));
				solver.weightedAssert(new Cons("=", prev, curr), 1);
			}
		}
	}

	private void sendCounterexample(String property, int k, Model model) {
		debug("Sending " + property);
		List<String> invalid = Collections.singletonList(property);
		director.incoming.add(new CounterexampleMessage(invalid, k, model, true));
	}
}
