package jkind.engines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
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
	protected final LinkedBiMap<String, Symbol> ivcMap;

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
		solver.define(spec.getIvcTransitionRelation());
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	protected void reduce(ValidMessage vm) {
		for (String property : vm.valid) {
			if (properties.remove(property)) {
				reduce(getInvariantByName(property, vm.invariants), vm);
			}
		}
	}

	protected Expr getInvariantByName(String name, List<Expr> invariants) {
		for (Expr invariant : invariants) {
			if (invariant.toString().equals(name)) {
				return invariant;
			}
		}

		throw new JKindException("Unable to find property " + name + " during reduction");
	}
	
	protected void reduce (Expr property, ValidMessage vm){
		Set<Expr> irreducible = new HashSet<>(); 
		
		solver.push(); 

		irreducible.add(property); 
		
		int k = 0;
		createVariables(-1);
		createVariables(0);
		assertInductiveTransition(0); 
		for (Expr inv : vm.invariants) {
			solver.assertSexp(inv.accept(new Lustre2Sexp(-1)));
		}
		
		List<Symbol> unsatCore1;
		comment("INDUCTIVE STEP-- Reducing ivc for: " + property);
		
		while (true) {
			Sexp query = SexpUtil.conjoinInvariants(irreducible, k);
			Result result = solver.unsatQuery(ivcMap.valueList(), query);

			if (result instanceof SatResult) {
				/*
				 * We haven't yet found the minimal value of k, so assert the
				 * irreducible and conditional invariants and increase k
				 */

				for (Expr inv : irreducible) {
					solver.assertSexp(inv.accept(new Lustre2Sexp(k)));
				}
				for (Expr inv: vm.invariants) {
					solver.assertSexp(inv.accept(new Lustre2Sexp(k)));
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

				unsatCore1 = ((UnsatResult) result).getUnsatCore();
				break; 
				
			} else if (result instanceof UnknownResult) {
				throw new JKindException("Unknown result in invariant reducer");
			}
		}

		solver.pop();
		solver.push();
		comment("BASE CASE-- Reducing ivc for: " + property);
		createVariables(-1);
		for (int i = 0; i <= k; i++) {
			createVariables(i);
		}
		assertInductiveTransition(0);
		Sexp base = getBaseIvcQuery(new ArrayList<>(irreducible), k);
		Result result = solver.unsatQuery(ivcMap.valueList(), base);
		if (!(result instanceof UnsatResult)) {
			throw new JKindException("Trying to reduce IVC on falsifiable property");
		}
		List<Symbol> unsatCore2 = ((UnsatResult) result).getUnsatCore();
		solver.pop();
		
		Set<Symbol> unsatCore = new HashSet<>();
		unsatCore.addAll(unsatCore1);
		unsatCore.addAll(unsatCore2);
		sendValid(property.toString(), k, new ArrayList<>(irreducible),
				                            getIvcNames(new ArrayList<>(unsatCore)), vm);
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
	
	protected Sexp createConditional(Entry<Symbol, Expr> entry, int k) {
		Symbol actLit = entry.getKey();
		Sexp inv = entry.getValue().accept(new Lustre2Sexp(k));
		return new Cons("=>", actLit, inv);
	}

	protected <T> LinkedBiMap<Symbol, T> createActivationLiterals(List<T> elements, String prefix) {
		LinkedBiMap<Symbol, T> map = new LinkedBiMap<>();
		int i = 0;
		for (T element : elements) {
			map.put(solver.createActivationLiteral(prefix, i++), element);
		}
		return map;
	}

	protected Set<String> getIvcNames(List<Symbol> symbols) {
		Set<String> result = new HashSet<>();
		for (Symbol s : symbols) {
			result.add(ivcMap.inverse().get(s));
		}
		return result;
	}

	protected void sendValid(String valid, int k, List<Expr> invariants, Set<String> ivc,
			ValidMessage vm) {
		comment("Sending " + valid + " at k = " + k + " with invariants: ");
		for (Expr invariant : invariants) {
			comment(invariant.toString());
		}
		comment("IVC: " + ivc.toString());
		
		Itinerary itinerary = vm.getNextItinerary();
		
		director.broadcast(new ValidMessage(vm.source, valid, k, invariants, trimNode(ivc),
				itinerary, null));
		
	}

	private Set<String> trimNode(Set<String> arg) {
		Set<String> ivc = new HashSet<>();
		for (String e : arg) {
			ivc.add(e.replaceAll("~[0-9]+", ""));
		}
		return ivc;
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
