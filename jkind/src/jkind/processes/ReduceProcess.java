package jkind.processes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.invariant.Invariant;
import jkind.processes.messages.Message;
import jkind.processes.messages.StopMessage;
import jkind.processes.messages.ValidMessage;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Label;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.translation.Specification;
import jkind.util.BiMap;
import jkind.util.SexpUtil;

public class ReduceProcess extends Process {
	public ReduceProcess(Specification spec, JKindSettings settings, Director director) {
		super("Reduction", spec, settings, director);
	}

	@Override
	public void main() {
		waitForMessage();
	}

	private void waitForMessage() {
		try {
			while (true) {
				Message message = incoming.take();
				if (message instanceof ValidMessage) {
					ValidMessage vm = (ValidMessage) message;
					for (String property : vm.valid) {
						Invariant propertyInvariant = getInvariantByName(property, vm.invariants);
						reduce(propertyInvariant, vm.invariants);
					}
				} else if (message instanceof StopMessage) {
					return;
				} else {
					throw new JKindException("Unknown message type in reduce invariants process: "
							+ message.getClass().getCanonicalName());
				}
			}
		} catch (InterruptedException e) {
			throw new JKindException("Interrupted while waiting for message", e);
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
		debug("Reducing: " + property);
		solver.push();

		Set<Invariant> irreducible = new HashSet<>();
		irreducible.add(property);

		int k = 0;

		BiMap<Label, Invariant> labelling = new BiMap<>();

		createVariables(-1);
		createVariables(0);
		while (true) {
			Sexp query = getUnsatCoreQuery(k, irreducible);
			Result result = solver.query(query);

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

		solver.pop();
		
		irreducible.remove(property);
		sendValid(property.toString(), k, new ArrayList<>(irreducible));
	}

	private void assertInvariants(int k, List<Invariant> invariants, BiMap<Label, Invariant> labelling) {
		for (Invariant invariant : invariants) {
			if (labelling.containsValue(invariant)) {
				solver.retract(labelling.inverse().remove(invariant));
			}
			Label label = solver.labelledAssert(getInvariantAssertion(invariant, k));
			labelling.put(label, invariant);
		}
	}

	private Sexp getInvariantAssertion(Invariant invariant, int k) {
		List<Sexp> conjuncts = new ArrayList<>();
		for (int i = 0; i <= k; i++) {
			conjuncts.add(invariant.instantiate(i));
		}
		return new Cons("and", conjuncts);
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
		return new Cons("=>", new Cons("and", hyps), conc);
	}

	protected Sexp getInductiveTransition(int k) {
		if (k == 0) {
			return getTransition(0, INIT);
		} else {
			return getTransition(k, Sexp.fromBoolean(false));
		}
	}

	private void minimizeUnsatCore(Sexp query, List<Label> unsatCore, Set<Label> allLabels) {
		solver.push();
		
		for (Label label : allLabels) {
			if (!unsatCore.contains(label)) {
				solver.retract(label);
			}
		}
		
		Iterator<Label> iterator = unsatCore.iterator();
		while (iterator.hasNext()) {
			Label label = iterator.next();
			
			solver.push();
			solver.retract(label);
			Result result = solver.query(query);
			solver.pop();
			
			if (result instanceof UnsatResult) {
				iterator.remove();
				solver.retract(label);
			}
		}
		
		solver.pop();
	}

	private void sendValid(String valid, int k, List<Invariant> reduced) {
		debug("Sending " + valid + " at k = " + k + " with invariants: ");
		for (Invariant invariant : reduced) {
			debug(invariant.toString());
		}

		List<String> validList = Collections.singletonList(valid);
		director.incoming.add(new ValidMessage(validList, k, reduced));
	}
}
