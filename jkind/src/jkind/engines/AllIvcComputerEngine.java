package jkind.engines;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList; 
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
import jkind.lustre.Equation;
import jkind.lustre.Expr; 
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder; 
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

public class AllIvcComputerEngine extends SolverBasedEngine {
	public static final String NAME = "all-ivc-computer";
	private final LinkedBiMap<String, Symbol> ivcMap;
	private Z3Solver z3Solver;	 
	private static final Symbol MAP_NAME = new Symbol("ivcmap"); 
	Set<Tuple<Set<String>, List<String>>> allIvcs = new HashSet<>(); 

	public AllIvcComputerEngine(Specification spec, JKindSettings settings, Director director) {
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
			if (ivcFinder(seed, resultOfIvcFinder)){
				map = new Cons("and", map, blockUp(getIvcLiterals(resultOfIvcFinder)));
			}else{
				map = new Cons("and", map, blockDown(getIvcLiterals(resultOfIvcFinder))); 
			}
		}
		
		z3Solver.pop();
		sendValid(property.toString(), vm);
	}
	
	private boolean ivcFinder(Set<Symbol> seed, List<String> resultOfIvcFinder) {
		JKindSettings js = new JKindSettings();
		js.reduceIvc = true;  
		List <String> wantedElem = getIvcNames(new ArrayList<> (seed)); 
		Node nodeSpec = unassign(spec.node, wantedElem); 
		
		Specification newSpec = new Specification(nodeSpec);  
 
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
			miniJkind.verify();
		if(miniJkind.getPropertyStatus() == MiniJKind.VALID){
			resultOfIvcFinder.addAll(miniJkind.getPropertyIvc());
			Set<String> newIvc = new HashSet<>(resultOfIvcFinder);
			allIvcs.add(new Tuple<Set<String>, List<String>>(newIvc, miniJkind.getPropertyInvariants()));
 
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
		solver.assertSexp(map);
		Result result = z3Solver.checkSat(new ArrayList<>());
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
		comment("All IVC sets found: " + allIvcs.toString());
		Set<Tuple<Set<String>, List<String>>> all = new HashSet<>(); 
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
	
	private Node unassign(Node node, List<String> vars) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		List<String> deactivate = new ArrayList<>();
		deactivate.addAll(ivcMap.keyList());
		deactivate.removeAll(vars);
		for(String v : deactivate){
			inputs.add(new VarDecl(v, Util.getTypeMap(node).get(v))); 
		}

		List<VarDecl> locals = removeVariable(node.locals, deactivate);
		List<VarDecl> outputs = removeVariable(node.outputs, deactivate);
		List<Equation> equations = new ArrayList<>(node.equations);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (deactivate.contains(eq.lhs.get(0).id)) {
				iter.remove(); 
			}
		}

		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		return builder.build();
	}

	private List<VarDecl> removeVariable(List<VarDecl> varDecls, List<String> vars) {
		List<VarDecl> result = new ArrayList<>(varDecls);
		Iterator<VarDecl> iter = result.iterator();
		while (iter.hasNext()) {
			if (vars.contains(iter.next().id)) {
				iter.remove(); 
			}
		}
		return result;
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
		if (vm.getNextDestination() == EngineType.IVC_REDUCTION_ALL) {
			reduce(vm);
		}
	}
}
