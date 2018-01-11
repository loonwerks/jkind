package jkind.engines;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindSettings;
import jkind.engines.invariant.InvariantSet;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.Message;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
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
	public static final String NAME = "k-induction";
	private int kCurrent = 0;
	private int kLimit = 0;
	private InvariantSet invariants = new InvariantSet();
	private Map<Integer, List<String>> baseStepValid = new HashMap<>();

	public KInductionEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
	}

	@Override
	public void main() {
		createVariables(-1);
		for (kCurrent = 0; kCurrent <= settings.n; kCurrent++) {
			comment("K = " + kCurrent);
			processMessagesAndWait();
			pruneUnknownProperties(kCurrent);
			createVariables(kCurrent);
			assertTransitionAndInvariants(kCurrent);
			checkProperties(kCurrent);
			if (properties.isEmpty()) {
				return;
			}
		}
		sendUnknown(properties);
	}

	private void processMessagesAndWait() {
		processMessagesAndWaitUntil(() -> kCurrent <= kLimit);
	}

	private void pruneUnknownProperties(int kCurrent) {
		List<String> bmcValid = baseStepValid.remove(kCurrent);
		if (bmcValid == null) {
			return;
		}

		List<String> unknown = difference(properties, bmcValid);
		properties.removeAll(unknown);
		if (!unknown.isEmpty()) {
			sendUnknown(unknown);
		}
	}

	private List<String> difference(List<String> list1, List<String> list2) {
		List<String> result = new ArrayList<>(list1);
		result.removeAll(list2);
		return result;
	}

	private void checkProperties(int k) {
		List<String> possiblyValid = new ArrayList<>(properties);

		while (!possiblyValid.isEmpty()) {
			Result result = solver.query(getInductiveQuery(k, possiblyValid));

			if (result instanceof SatResult || result instanceof UnknownResult) {
				Model model = getModel(result);
				if (model == null) {
					sendUnknown(properties);
					properties.clear();
					break;
				}

				List<String> bad = getFalseProperties(possiblyValid, k, model);
				possiblyValid.removeAll(bad);
				if (result instanceof UnknownResult) {
					sendUnknown(bad);
				}
				sendInductiveCounterexamples(bad, k + 1, model);
			} else if (result instanceof UnsatResult) {
				properties.removeAll(possiblyValid);
				addPropertiesAsInvariants(k, possiblyValid);
				sendValid(possiblyValid, k);
				return;
			}
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
		assertInvariants(invariants.getInvariants(), k);
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
		Message vm = new ValidMessage(getName(), valid, k, invariants.getInvariants(), null, itinerary);
		director.broadcast(vm);
	}

	private void sendInductiveCounterexamples(List<String> properties, int length, Model model) {
		if (settings.inductiveCounterexamples && properties.size() > 0) {
			director.broadcast(new InductiveCounterexampleMessage(properties, length, model));
		}
	}

	private void sendUnknown(List<String> unknown) {
		director.receiveMessage(new UnknownMessage(getName(), unknown));
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
		kLimit = bsm.step;
		baseStepValid.put(bsm.step, bsm.properties);
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
		List<Expr> supported = im.invariants.stream().filter(solver::supports).collect(toList());
		invariants.addAll(supported);
		assertNewInvariants(supported, kCurrent - 1);
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
