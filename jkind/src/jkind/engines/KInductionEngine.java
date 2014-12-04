package jkind.engines;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.values.BooleanValue;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.translation.Specification;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;

public class KInductionEngine extends SolverBasedEngine {
	private int kCurrent = 0;
	private int kLimit = 0;
	private List<Expr> invariants = new ArrayList<>();

	public KInductionEngine(Specification spec, JKindSettings settings, Director director) {
		super("k-induction", spec, settings, director);
	}

	@Override
	public void main() {
		createVariables(-1);
		for (kCurrent = 0; kCurrent <= settings.n; kCurrent++) {
			comment("K = " + kCurrent);
			processMessagesAndWait();
			createVariables(kCurrent);
			assertTransitionAndInvariants(kCurrent);
			checkProperties(kCurrent);
			if (properties.isEmpty()) {
				break;
			}
		}
	}

	private void processMessagesAndWait() {
		processMessagesAndWaitUntil(() -> kCurrent <= kLimit);
	}

	private void checkProperties(int k) {
		List<String> possiblyValid = new ArrayList<>(properties);

		while (!possiblyValid.isEmpty()) {
			Result result = solver.query(getInductiveQuery(k, possiblyValid));

			if (result instanceof SatResult || result instanceof UnknownResult) {
				Model model = getModel(result);
				Iterator<String> iterator = possiblyValid.iterator();
				while (iterator.hasNext()) {
					String p = iterator.next();
					StreamIndex si = new StreamIndex(p, k);
					BooleanValue v = (BooleanValue) model.getValue(si);
					if (!v.value) {
						sendInductiveCounterexample(p, k + 1, model);
						iterator.remove();
					}
				}
			} else if (result instanceof UnsatResult) {
				properties.removeAll(possiblyValid);
				addPropertiesAsInvariants(k, possiblyValid);
				sendValid(possiblyValid, k);
				return;
			}
		}
	}

	private Model getModel(Result result) {
		if (result instanceof SatResult) {
			return ((SatResult) result).getModel();
		} else if (result instanceof UnknownResult) {
			return ((UnknownResult) result).getModel();
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void addPropertiesAsInvariants(int k, List<String> valid) {
		List<Expr> newInvariants = valid.stream().map(IdExpr::new).collect(toList());
		invariants.addAll(newInvariants);
		assertNewInvariants(newInvariants, k);
	}

	private void assertNewInvariants(List<Expr> invariants, int limit) {
		for (int i = 0; i <= limit; i++) {
			assertInvariants(invariants, i);
		}
	}

	private void assertInvariants(List<Expr> invariants, int i) {
		solver.assertSexp(SexpUtil.conjoinInvariants(invariants, i));
	}

	private void assertTransitionAndInvariants(int k) {
		assertInductiveTransition(k);
		assertInvariants(invariants, k);
	}

	private Sexp getInductiveQuery(int k, List<String> possiblyValid) {
		List<Sexp> hyps = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			hyps.add(StreamIndex.conjoinEncodings(possiblyValid, i));
		}
		Sexp conc = StreamIndex.conjoinEncodings(possiblyValid, k);

		return new Cons("=>", SexpUtil.conjoin(hyps), conc);
	}

	private void sendValid(List<String> valid, int k) {
		Itinerary itinerary = director.getValidMessageItinerary();
		director.broadcast(new ValidMessage(getName(), valid, k, invariants, itinerary));
	}

	private void sendInductiveCounterexample(String p, int length, Model model) {
		if (settings.inductiveCounterexamples) {
			director.broadcast(new InductiveCounterexampleMessage(p, length, model));
		}
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
		kLimit = bsm.step;
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
		invariants.addAll(im.invariants);
		assertNewInvariants(im.invariants, kCurrent - 1);
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		properties.removeAll(um.unknown);
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		properties.removeAll(vm.valid);
		addPropertiesAsInvariants(kCurrent - 1, vm.valid);
	}
}
