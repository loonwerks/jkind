package jkind.engines;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet; 
import java.util.List; 
import java.util.Set;
import java.util.Map.Entry;

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
import jkind.lustre.LustreUtil;
import jkind.lustre.NamedType; 
import jkind.lustre.VarDecl;  
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.LinkedBiMap;
import jkind.util.SexpUtil;
import jkind.util.Tuple;
import jkind.util.Util;

public class AllIvcComputerEngine2 extends SolverBasedEngine {
	public static final String NAME = "all-ivc-slow-computer";
	private final LinkedBiMap<String, Symbol> ivcMap;
	private Z3Solver z3Solver;	 
	private static final Symbol MAP_NAME = new Symbol("ivcmap"); 
	Set<Tuple<Set<String>, List<String>>> allIvcs = new HashSet<>(); 

	public AllIvcComputerEngine2(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
		ivcMap = Lustre2Sexp.createIvcMap(spec.node.ivc); 
	}

	@Override
	protected void initializeSolver() {
		solver = getSolver();
		solver.initialize();
		z3Solver = (Z3Solver) solver; 

		for (Symbol e : ivcMap.values()) {
			z3Solver.define(new VarDecl(e.str, NamedType.BOOL));
		}
		z3Solver.define(spec.getIvcTransitionRelation());
		z3Solver.define(new VarDecl(INIT.str, NamedType.BOOL));
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}
	
	private void reduce(ValidMessage vm) { 
		for (String property : vm.valid) {
			if (properties.remove(property)) {
				computeAllIvcs(getInvariantByName(property, vm.invariants), vm);
			}
			
		}
	}
	
	private void computeAllIvcs(Expr property, ValidMessage vm) {
		Sexp map;
		Set<Symbol> seed = new HashSet<Symbol>();
		 
		List<String> resultOfIvcFinder = new ArrayList<>();
		List<String> inv = vm.invariants.stream().map(Object::toString).collect(toList()); 
		allIvcs.add(new Tuple<Set<String>, List<String>>(vm.ivc, inv));
		 
		seed.addAll(getIvcLiterals(new ArrayList<>(vm.ivc)));
		map = blockUp(seed);
		
		z3Solver.push();
		while(checkMapSatisfiability(map, seed)){
			resultOfIvcFinder.clear();
			if (ivcFinder(seed, resultOfIvcFinder, property.toString(), vm)){
				map = new Cons("and", map, blockUp(getIvcLiterals(resultOfIvcFinder)));
			}else{
				map = new Cons("and", map, blockDown(getIvcLiterals(resultOfIvcFinder))); 
			}
		}
		
		z3Solver.pop();
		sendValid(property.toString(), vm);
	}
	
	private boolean ivcFinder(Set<Symbol> seed, List<String> resultOfIvcFinder, String property, ValidMessage vm) {
		List <String> wantedElem = getIvcNames(new ArrayList<> (seed)); 
		List<String> deactivate = new ArrayList<>();
		deactivate.addAll(ivcMap.keyList());
		deactivate.removeAll(wantedElem);  
 
		IvcReduction ivcReducer = new IvcReduction(property, getIvcLiterals(deactivate), vm); 
		if(ivcReducer.getPropertyStatus() == IvcReduction.VALID){
			resultOfIvcFinder.addAll(ivcReducer.getIvc());
			Set<String> newIvc = new HashSet<>(resultOfIvcFinder);
			allIvcs.add(new Tuple<Set<String>, List<String>>(newIvc, ivcReducer.getInvariants()));
           
			return true;
		}
		else{
			resultOfIvcFinder.addAll(wantedElem); 
			return false;
		} 
	}

	private Sexp blockUp(Set<Symbol> set) {
		List<Sexp> ret = new ArrayList<>();
		for(Symbol literal : set){
			ret.add(new Cons("not", literal));
		}
		return SexpUtil.disjoin(ret);
	}
	
	private Sexp blockDown(Set<Symbol> set) {
		List<Sexp> ret = new ArrayList<>();
		for(Symbol literal : computeMCS(set)){
			ret.add(literal);
		}
		return SexpUtil.disjoin(ret);
	}

	private Set<Symbol> computeMCS(Set<Symbol> seed) {
		Set<Symbol> result = new HashSet<>();
		for(Symbol core : ivcMap.valueList()){
			if (!seed.contains(core)){
				result.add(core);
			}
		}
		return result;
	}

	private boolean checkMapSatisfiability(Sexp map, Set<Symbol> seed) { 
		z3Solver.push();
		z3Solver.define(new VarDecl(MAP_NAME.str, NamedType.BOOL));
		z3Solver.assertSexp(map);
		Result result = z3Solver.checkSat(new ArrayList<>(), true);
		if (result instanceof UnsatResult){
			return false;
		}
		else if (result instanceof UnknownResult){
			throw new JKindException("Unknown result in solving map");
		} 
		 
		seed.clear();
		seed.addAll(maximizeSat(((SatResult) result).getModel()));
		z3Solver.pop();
	
		return true;
	}
	
	/**
	 * in case of sat result we would like to get a maximum sat subset of activation literals 
	 **/
	private List<Symbol> maximizeSat(Model model) {
		Set<Symbol> seed = getActiveLiteralsFromModel(model);
		Set<Symbol> temp = new HashSet<>();
		temp.addAll(seed); 
		for(Symbol literal : ivcMap.valueList()){ 
			temp.add(literal); 
			if(z3Solver.quickCheckSat(new ArrayList<>(temp)) instanceof SatResult){
				seed.add(literal);
			}else{
				temp.remove(literal);
			}
		}
		return new ArrayList<>(seed); 
	}

	private Set<Symbol> getActiveLiteralsFromModel(Model model) {
		Set<Symbol> seed = new HashSet<>();
		for (String var : model.getVariableNames()) { 
			if(model.getValue(var).toString() == "true"){
				seed.add(new Symbol(var));
			}
		}
		return seed;
	}

	private Expr getInvariantByName(String name, List<Expr> invariants) {
		for (Expr invariant : invariants) {
			if (invariant.toString().equals(name)) {
				return invariant;
			}
		}

		throw new JKindException("Unable to find property " + name + " during reduction");
	}

	private List<String> getIvcNames(List<Symbol> symbols) {
		List<String> result = new ArrayList<>();
		for (Symbol s : symbols) {
			result.add(ivcMap.inverse().get(s));
		}
		return result;
	}
	
	private Set<Symbol> getIvcLiterals(List<String> resultOfIvcFinder) {
		Set<Symbol> result = new HashSet<>();
		for (String ivc : resultOfIvcFinder) {
			result.add(ivcMap.get(ivc));
		}
		return result;
	}

	private void sendValid(String valid, ValidMessage vm) {
		MiniJKind.active = false;
		comment("Sending " + valid);
		List<Tuple<Set<String>, List<String>>> all = new ArrayList<>(); 
		for(Tuple<Set<String>, List<String>> item : allIvcs){
			all.add(new Tuple<>(trimNode(item.firstElement()), item.secondElement()));
		}
		
		Itinerary itinerary = vm.getNextItinerary();
		director.broadcast(new ValidMessage(vm.source, valid, 0, null, null , itinerary, all)); 
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
		if (vm.getNextDestination() == EngineType.IVC_REDUCTION_ALL_2) {
			reduce(vm);
		}
	}
	
	private class IvcReduction { 
		private final List<Expr> potentialInvariants;
		private final String property; 
		//private Z3Solver internalSolver;
 
		public static final String INVALID = "INVALID";
		public static final String VALID = "VALID";
		public static final String UNKNOWN = "UNKNOWN";
		public static final String NOT_YET_CHECKED = "NOT_YET_CHECKED";
		private String status = NOT_YET_CHECKED;
		private List<Symbol> ivcLiterals = new ArrayList<>();
		private Z3Solver internalSolver;
		
		public int k;
		public List<Expr> internalInvariants =  new ArrayList<>();
		public Set<String> internalIvc = new HashSet<>();
		
		public IvcReduction(String property, Set<Symbol> deactivate, ValidMessage vm) {  
			this.potentialInvariants = vm.invariants;
			this.property = property;  
			k = vm.k;
			ivcLiterals.addAll(ivcMap.valueList());
			ivcLiterals.removeAll(deactivate);
			 
			this.internalSolver = new Z3Solver("internal_reduction", true);
			internalSolver.initialize(); 
			
			for (Symbol e : ivcMap.values()) {
				internalSolver.define(new VarDecl(e.str, NamedType.BOOL));
			}
			
			internalSolver.define(spec.getIvcTransitionRelation());
			internalSolver.define(new VarDecl(INIT.str, NamedType.BOOL)); 
			reduce();
		}
		
		private void reduce() {
			reduceInvariants(getInvariantByName(property, potentialInvariants));
		} 
		
		private void reduceInvariants(Expr property) { 
			internalSolver.push();
			
			internalSolver.assertSexp(SexpUtil.conjoin(ivcLiterals));

			LinkedBiMap<Symbol, Expr> candidates = createActivationLiterals(potentialInvariants, "inv");

			Set<Expr> irreducible = new HashSet<>();
			irreducible.add(property);
			candidates.inverse().remove(property);

			int st = 0;
			this.createVariables(-1);
			this.createVariables(0);
			this.assertInductiveTransition(0);

			while (true) {
				Sexp query = SexpUtil.conjoinInvariants(irreducible, st);
				Result result = internalSolver.unsatQuery(candidates.keyList(), query);
				if (st > (k + 30)){
					status = UNKNOWN;
					return;
				}
				if (result instanceof SatResult) { 
					for (Expr inv : irreducible) {
						internalSolver.assertSexp(inv.accept(new Lustre2Sexp(st)));
					}
					for (Entry<Symbol, Expr> entry : candidates.entrySet()) {
						internalSolver.assertSexp(createConditional(entry, st));
					}
					st++;
					this.createVariables(st);
					this.assertInductiveTransition(st);
				} else if (result instanceof UnsatResult) {

					List<Symbol> unsatCore = ((UnsatResult) result).getUnsatCore();
					if (unsatCore.isEmpty()) {
						break;
					}
					for (Symbol core : unsatCore) {
						irreducible.add(candidates.remove(core));
						internalSolver.assertSexp(core);
					}
				} else if (result instanceof UnknownResult) {
					 status = UNKNOWN; 
				}
			}

			internalSolver.pop();
			reduceIvc(property, st, new ArrayList<>(irreducible));
		}
		
		private void reduceIvc(Expr property, int k, List<Expr> invariants) {
			
			if (spec.node.ivc.isEmpty()) {
				getResult(k, invariants, Collections.emptySet()); 
				return;
			} 
			internalSolver.push();

			this.createVariables(-1);
			for (int i = 0; i <= k; i++) {
				this.createVariables(i);
			}
			this.assertInductiveTransition(0);
 
			Result result = internalSolver.unsatQuery(ivcLiterals, getIvcQuery(invariants, k));
			if (!(result instanceof UnsatResult)) {
				status = INVALID;
				return;
			}
			List<Symbol> unsatCore = ((UnsatResult) result).getUnsatCore();
			internalSolver.pop();

			getResult(k, invariants, new HashSet<>(getIvcNames(unsatCore)));
		}

		private Sexp getIvcQuery(List<Expr> properties, int k) {
			if (k == 0) {
				return SexpUtil.conjoinInvariants(properties, k);
			}

			Sexp base = getBaseIvcQuery(properties, k);
			Sexp inductiveStep = getStepIvcQuery(properties, k);
			return new Cons("and", base, inductiveStep);
		}
		
		private Sexp getBaseIvcQuery(List<Expr> properties, int k) {
			Sexp query = SexpUtil.conjoinInvariants(properties, k - 1);
			for (int i = k - 1; i > 0; i--) {
				query = new Cons("=>", getBaseTransition(i), query);
				query = new Cons("and", SexpUtil.conjoinInvariants(properties, i - 1), query);
			}
			return new Cons("=>", INIT, query);
		}

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
		
		private void getResult(int k, List<Expr> invariants, Set<String> ivc) {
			this.k = k;
	    	this.internalInvariants.addAll(invariants);
	    	this.internalIvc.addAll(trimNode(ivc)); 
		}
		
		public int getK() {
			return k;	
		}
		
		public List<String> getInvariants() {
			List<String> stringInvariants = internalInvariants.stream().map(Object::toString).collect(toList());
			return Util.safeNullableList(stringInvariants); 
						
		}
		
		public Set<String> getIvc() {
			return internalIvc;
		}
		
		public String getPropertyStatus() {
			return status;
		}
		
		private void assertInductiveTransition(int k) {
			internalSolver.assertSexp(getInductiveTransition(k));
		}
		
		private void createVariables(int k) {
			for (VarDecl vd : getOffsetVarDecls(k)) {
				internalSolver.define(vd);
			}

			for (VarDecl vd : Util.getVarDecls(spec.node)) {
				Expr constraint = LustreUtil.typeConstraint(vd.id, vd.type);
				if (constraint != null) {
					internalSolver.assertSexp(constraint.accept(new Lustre2Sexp(k)));
				}
			}
		}
		 
		private <T> LinkedBiMap<Symbol, T> createActivationLiterals(List<T> elements, String prefix) {
			LinkedBiMap<Symbol, T> map = new LinkedBiMap<>();
			int i = 0;
			for (T element : elements) {
				map.put(internalSolver.createActivationLiteral(prefix, i++), element);
			}
			return map;
		}
	}
}
