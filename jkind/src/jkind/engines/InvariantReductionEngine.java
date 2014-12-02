package jkind.engines;

import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import jkind.lustre.Expr;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.yices.YicesSolver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.BiMap;
import jkind.util.SexpUtil;

public class InvariantReductionEngine extends SolverBasedEngine {
	private YicesSolver yicesSolver;
	private int nameCounter = 1;

	public InvariantReductionEngine(Specification spec, JKindSettings settings, Director director) {
		super("invariant-reduction", spec, settings, director);
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

	private void reduce(ValidMessage vm) {
		for (String property : vm.valid) {
			if (properties.remove(property)) {
				reduce(getInvariantByName(property, vm.invariants), vm);
			}
		}
	}

	private Expr getInvariantByName(String name, List<Expr> invariants) {
		for (Expr invariant : invariants) {
			if (invariant.toString().equals(name)) {
				return invariant;
			}
		}

		throw new JKindException("Unable to find property " + name + " during reduction");
	}

	private void reduce(Expr property, ValidMessage vm) {
		comment("Reducing: " + property);
		yicesSolver.push();

		Set<Expr> irreducible = new HashSet<>();
		irreducible.add(property);

		int k = 0;

		BiMap<String, Expr> labelling = new BiMap<>();

		createVariables(-1);
		createVariables(0);
		while (true) {
			Sexp query = getUnsatCoreQuery(k, irreducible);
			Result result = yicesSolver.query(query);

			if (result instanceof SatResult) {
				k++;
				assertInvariants(k - 1, vm.invariants, labelling);
				createVariables(k);
			} else if (result instanceof UnsatResult) {
				List<String> unsatCore = ((UnsatResult) result).getUnsatCore();
				minimizeUnsatCore(query, unsatCore, labelling.keySet());
				Set<Expr> coreInvariants = getInvariants(unsatCore, labelling);
				if (irreducible.containsAll(coreInvariants)) {
					break;
				} else {
					irreducible.addAll(coreInvariants);
				}
			} else if (result instanceof UnknownResult) {
				throw new JKindException("Unknown result in invariant reducer");
			}
		}

		yicesSolver.pop();

		irreducible.remove(property);
		sendValid(property.toString(), k, new ArrayList<>(irreducible), vm);
	}
	
	private String getFreshLabel() {
		return "fresh" + nameCounter++;
	}

	private void assertInvariants(int k, List<Expr> invariants,
			BiMap<String, Expr> labelling) {
		for (Expr invariant : invariants) {
			if (labelling.containsValue(invariant)) {
				yicesSolver.retract(labelling.inverse().remove(invariant));
			}
			String label = getFreshLabel();
			yicesSolver.assertSexp(getInvariantAssertion(invariant, k), label);
			labelling.put(label, invariant);
		}
	}

	private Sexp getInvariantAssertion(Expr invariant, int k) {
		List<Sexp> conjuncts = new ArrayList<>();
		for (int i = 0; i <= k; i++) {
			conjuncts.add(invariant.accept(new Lustre2Sexp(i)));
		}
		return SexpUtil.and(conjuncts);
	}

	private Set<Expr> getInvariants(List<String> unsatCore, BiMap<String, Expr> labelling) {
		return unsatCore.stream().map(labelling::get).collect(toSet());
	}

	private Sexp getUnsatCoreQuery(int k, Collection<Expr> irreducible) {
		List<Sexp> hyps = new ArrayList<>();
		for (int i = 0; i <= k; i++) {
			hyps.add(getInductiveTransition(i));
			if (i < k) {
				hyps.add(SexpUtil.conjoinInvariants(irreducible, i));
			}
		}

		Sexp conc = SexpUtil.conjoinInvariants(irreducible, k);
		return new Cons("=>", SexpUtil.and(hyps), conc);
	}

	protected Sexp getInductiveTransition(int k) {
		if (k == 0) {
			return getTransition(0, INIT);
		} else {
			return getTransition(k, Sexp.fromBoolean(false));
		}
	}

	private void minimizeUnsatCore(Sexp query, List<String> unsatCore, Set<String> allLabels) {
		yicesSolver.push();

		for (String label : allLabels) {
			if (!unsatCore.contains(label)) {
				yicesSolver.retract(label);
			}
		}

		Iterator<String> iterator = unsatCore.iterator();
		while (iterator.hasNext()) {
			String label = iterator.next();

			yicesSolver.push();
			yicesSolver.retract(label);
			Result result = yicesSolver.query(query);
			yicesSolver.pop();

			if (result instanceof UnsatResult) {
				iterator.remove();
				yicesSolver.retract(label);
			}
		}

		yicesSolver.pop();
	}

	private void sendValid(String valid, int k, List<Expr> reduced, ValidMessage vm) {
		comment("Sending " + valid + " at k = " + k + " with invariants: ");
		for (Expr invariant : reduced) {
			comment(invariant.toString());
		}

		Itinerary itinerary = vm.getNextItinerary();
		director.broadcast(new ValidMessage(vm.source, valid, k, reduced, itinerary));
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
		if (vm.getNextDestination() == EngineType.INVARIANT_REDUCTION) {
			reduce(vm);
		}
	}
}
