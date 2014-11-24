package jkind.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.invariant.Invariant;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Label;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.yices.YicesSolver;
import jkind.translation.Specification;
import jkind.util.BiMap;
import jkind.util.SexpUtil;

public class InvariantReductionEngine extends SolverBasedEngine {
	private YicesSolver yicesSolver;

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
			reduce(getInvariantByName(property, vm.invariants), vm.invariants);
		}
	}

	private Invariant getInvariantByName(String name, List<Invariant> invariants) {
		for (Invariant invariant : invariants) {
			if (invariant.toString().equals(name)) {
				return invariant;
			}
		}

		throw new JKindException("Unable to find property " + name + " during reduction");
	}

	private void reduce(Invariant property, List<Invariant> invariants) {
		comment("Reducing: " + property);
		yicesSolver.push();

		Set<Invariant> irreducible = new HashSet<>();
		irreducible.add(property);

		int k = 0;

		BiMap<Label, Invariant> labelling = new BiMap<>();

		createVariables(-1);
		createVariables(0);
		while (true) {
			Sexp query = getUnsatCoreQuery(k, irreducible);
			Result result = yicesSolver.query(query);

			if (result instanceof SatResult) {
				k++;
				assertInvariants(k - 1, invariants, labelling);
				createVariables(k);
			} else if (result instanceof UnsatResult) {
				List<Label> unsatCore = ((UnsatResult) result).getUnsatCore();
				minimizeUnsatCore(query, unsatCore, labelling.keySet());
				Set<Invariant> coreInvariants = getInvariants(unsatCore, labelling);
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
		sendValid(property.toString(), k, new ArrayList<>(irreducible));
	}

	private void assertInvariants(int k, List<Invariant> invariants,
			BiMap<Label, Invariant> labelling) {
		for (Invariant invariant : invariants) {
			if (labelling.containsValue(invariant)) {
				yicesSolver.retract(labelling.inverse().remove(invariant));
			}
			Label label = yicesSolver.labelledAssert(getInvariantAssertion(invariant, k));
			labelling.put(label, invariant);
		}
	}

	private Sexp getInvariantAssertion(Invariant invariant, int k) {
		List<Sexp> conjuncts = new ArrayList<>();
		for (int i = 0; i <= k; i++) {
			conjuncts.add(invariant.instantiate(i));
		}
		return SexpUtil.conjoin(conjuncts);
	}

	private Set<Invariant> getInvariants(List<Label> unsatCore, BiMap<Label, Invariant> labelling) {
		Set<Invariant> result = new HashSet<>();
		for (Label label : unsatCore) {
			result.add(labelling.get(label));
		}
		return result;
	}

	private Sexp getUnsatCoreQuery(int k, Collection<Invariant> irreducible) {
		List<Sexp> hyps = new ArrayList<>();
		for (int i = 0; i <= k; i++) {
			hyps.add(getInductiveTransition(i));
			if (i < k) {
				hyps.add(SexpUtil.conjoinInvariants(irreducible, i));
			}
		}

		Sexp conc = SexpUtil.conjoinInvariants(irreducible, k);
		return new Cons("=>", SexpUtil.conjoin(hyps), conc);
	}

	protected Sexp getInductiveTransition(int k) {
		if (k == 0) {
			return getTransition(0, INIT);
		} else {
			return getTransition(k, Sexp.fromBoolean(false));
		}
	}

	private void minimizeUnsatCore(Sexp query, List<Label> unsatCore, Set<Label> allLabels) {
		yicesSolver.push();

		for (Label label : allLabels) {
			if (!unsatCore.contains(label)) {
				yicesSolver.retract(label);
			}
		}

		Iterator<Label> iterator = unsatCore.iterator();
		while (iterator.hasNext()) {
			Label label = iterator.next();

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

	private void sendValid(String valid, int k, List<Invariant> reduced) {
		comment("Sending " + valid + " at k = " + k + " with invariants: ");
		for (Invariant invariant : reduced) {
			comment(invariant.toString());
		}

		ValidMessage vm = new ValidMessage(EngineType.INVARIANT_REDUCTION, valid, k, reduced);
		director.broadcast(vm, this);
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
		if (shouldHandle(vm)) {
			reduce(vm);
		}
	}

	private boolean shouldHandle(ValidMessage vm) {
		return director.nextResponsible(vm) == EngineType.INVARIANT_REDUCTION;
	}
}
