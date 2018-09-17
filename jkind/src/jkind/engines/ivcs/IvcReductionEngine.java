package jkind.engines.ivcs;  
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import jkind.JKindException;
import jkind.JKindSettings; 
import jkind.engines.Director; 
import jkind.engines.SolverBasedEngine; 
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.EngineType;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage; 
import jkind.lustre.Expr; 
import jkind.lustre.NamedType;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.LinkedBiMap;
import jkind.util.SexpUtil; 

public class IvcReductionEngine extends SolverBasedEngine {
	public static final String NAME = "ivc-reduction";
	private final LinkedBiMap<String, Symbol> ivcMap;
	private double runtime;

	public IvcReductionEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
		ivcMap = Lustre2Sexp.createIvcMap(spec.node.ivc);
	}

	@Override
	protected void initializeSolver() {
		solver = getSolver();
		solver.initialize();

		for (Symbol e : ivcMap.values()) {
			solver.define(new VarDecl(e.str, NamedType.BOOL));
		}
		solver.declare(spec.functions);
		solver.define(spec.getIvcTransitionRelation());
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private void reduce(ValidMessage vm) {
		for (String property : vm.valid) { 
			if (properties.remove(property)) { 
				runtime = System.currentTimeMillis();   
				reduceInvariants(IvcUtil.getInvariantByName(property, vm.invariants), vm);
			}
		}
	}

	private void reduceInvariants(Expr property, ValidMessage vm) {
		comment("Reducing invariants for: " + property);
		solver.push();
		solver.assertSexp(SexpUtil.conjoin(ivcMap.valueList()));

		LinkedBiMap<Symbol, Expr> candidates = createActivationLiterals(vm.invariants, "inv");

		Set<Expr> irreducible = new HashSet<>();
		irreducible.add(property);
		candidates.inverse().remove(property);

		int k = 0;
		createVariables(-1);
		createVariables(0);
		assertInductiveTransition(0);

		while (k <= vm.k) {
			Sexp query = SexpUtil.conjoinInvariants(irreducible, k);
			Result result = solver.unsatQuery(candidates.keyList(), query);

			if (result instanceof SatResult || result instanceof UnknownResult) {
				/*
				 * We haven't yet found the minimal value of k, so assert the
				 * irreducible and conditional invariants and increase k
				 */

				for (Expr inv : irreducible) {
					solver.assertSexp(inv.accept(new Lustre2Sexp(k)));
				}
				for (Entry<Symbol, Expr> entry : candidates.entrySet()) {
					solver.assertSexp(createConditional(entry, k));
				}
				k++;
				createVariables(k);
				assertInductiveTransition(k);
			} else if (result instanceof UnsatResult) {
				/*
				 * We found the minimal value of k, now we determine which
				 * candidate invariants are necessary to prove the irreducible
				 * invariants. If no more are necessary, then we are done.
				 * Otherwise we mark new irreducible invariants and start over
				 * to see if any additional invariants are needed to prove
				 * those.
				 */

				List<Symbol> unsatCore = ((UnsatResult) result).getUnsatCore();
				if (unsatCore.isEmpty()) {
					break;
				}
				for (Symbol core : unsatCore) {
					irreducible.add(candidates.remove(core));
					solver.assertSexp(core);
				}
			}
		}

		if (k == vm.k + 1) {
			/*
			 * Failed to find the right value of k, due to UnknownResult from
			 * solver. Give up and use what we started with.
			 */
			irreducible.addAll(vm.invariants);
			k = vm.k;
		}

		solver.pop();
		reduceIvc(property, k, new ArrayList<>(irreducible), vm);
	}

	private void reduceIvc(Expr property, int k, List<Expr> invariants, ValidMessage vm) {
		if (spec.node.ivc.isEmpty()) {
			sendValid(property.toString(), k, invariants, Collections.emptySet(), vm);
			return;
		}

		comment("Reducing ivc for: " + property);
		solver.push();

		createVariables(-1);
		for (int i = 0; i <= k; i++) {
			createVariables(i);
		}
		assertInductiveTransition(0);

		Result result = solver.unsatQuery(ivcMap.valueList(), getIvcQuery(invariants, k));
		if (!(result instanceof UnsatResult)) {
			throw new JKindException("Trying to reduce IVC on falsifiable property");
		}
		List<Symbol> unsatCore = ((UnsatResult) result).getUnsatCore();
		solver.pop();

		sendValid(property.toString(), k, invariants, getIvcNames(unsatCore), vm);
	}

	private Sexp getIvcQuery(List<Expr> properties, int k) {
		if (k == 0) {
			return SexpUtil.conjoinInvariants(properties, k);
		}

		Sexp base = getBaseIvcQuery(properties, k);
		Sexp inductiveStep = getStepIvcQuery(properties, k);
		return new Cons("and", base, inductiveStep);
	}

	/**
	 * Base step query for IVC reduction. Examples for k = 1, 2, 3:
	 * 
	 * %init => P(0)
	 * 
	 * %init => (P(0) and (T(0, 1) => P(1)))
	 *
	 * %init => (P(0) and (T(0, 1) => (P(1) and (T(1, 2) => P(2)))))
	 */
	private Sexp getBaseIvcQuery(List<Expr> properties, int k) {
		Sexp query = SexpUtil.conjoinInvariants(properties, k - 1);
		for (int i = k - 1; i > 0; i--) {
			query = new Cons("=>", getBaseTransition(i), query);
			query = new Cons("and", SexpUtil.conjoinInvariants(properties, i - 1), query);
		}
		return new Cons("=>", INIT, query);
	}

	/**
	 * Inductive step query for IVC reduction. Examples for k = 1, 2, 3:
	 * 
	 * (P(0) and T(0, 1)) => P(1)
	 * 
	 * (P(0) and T(0, 1) and P(1) and T(1, 2)) => P(2)
	 *
	 * (P(0) and T(0, 1) and P(1) and T(1, 2) and P(2) and T(2, 3)) => P(3)
	 */
	private Sexp getStepIvcQuery(List<Expr> properties, int k) {
		List<Sexp> hyps = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			hyps.add(SexpUtil.conjoinInvariants(properties, i));
			hyps.add(getInductiveTransition(i + 1));
		}
		return new Cons("=>", SexpUtil.conjoin(hyps), SexpUtil.conjoinInvariants(properties, k));
	}

	private Sexp createConditional(Entry<Symbol, Expr> entry, int k) {
		Symbol actLit = entry.getKey();
		Sexp inv = entry.getValue().accept(new Lustre2Sexp(k));
		return new Cons("=>", actLit, inv);
	}

	private <T> LinkedBiMap<Symbol, T> createActivationLiterals(List<T> elements, String prefix) {
		LinkedBiMap<Symbol, T> map = new LinkedBiMap<>();
		int i = 0;
		for (T element : elements) {
			map.put(solver.createActivationLiteral(prefix, i++), element);
		}
		return map;
	}

	private Set<String> getIvcNames(List<Symbol> symbols) {
		Set<String> result = new HashSet<>();
		for (Symbol s : symbols) {
			result.add(ivcMap.inverse().get(s));
		}
		return result;
	}

	private void sendValid(String valid, int k, List<Expr> invariants, Set<String> ivc, ValidMessage vm) {
		runtime = (System.currentTimeMillis() - runtime) / 1000.0; 
		comment("Sending " + valid + " at k = " + k + " with invariants: ");
		for (Expr invariant : invariants) {
			comment(invariant.toString());
		}
		comment("IVC: " + ivc.toString());
		Itinerary itinerary = vm.getNextItinerary(); 
		ValidMessage nvm = new ValidMessage(vm.source, valid, k, vm.proofTime + runtime, invariants, ivc, itinerary, null);
		director.broadcast(nvm); 
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
		if (vm.getNextDestination() == EngineType.IVC_REDUCTION) {
			reduce(vm);
		}
	}
}