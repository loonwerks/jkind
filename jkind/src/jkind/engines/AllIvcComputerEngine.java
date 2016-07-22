package jkind.engines;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList; 
import java.util.HashSet;
import java.util.Iterator;
import java.util.List; 
import java.util.Set; 
import jkind.JKindException;
import jkind.JKindSettings;
import jkind.Output;
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
	private Set<String> mustElements = new HashSet<>();
	private Set<String> mayElements = new HashSet<>();  
	
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
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}
	
	private void reduce(ValidMessage vm) { 
		
		for (String property : vm.valid) {
			mayElements.clear();
			mustElements.clear(); 
			if (spec.node.ivc.isEmpty()) {
				Output.warning(NAME + ": --%IVC option has no argument for property  "+ property.toString());
				sendValid(property.toString(), vm);
				return;
			}
			if (properties.remove(property)) {
				computeAllIvcs(getInvariantByName(property, vm.invariants), vm);
			}
			
		}
	}
	
	private void computeAllIvcs(Expr property, ValidMessage vm) {
		Sexp map;
		List<Symbol> seed = new ArrayList<Symbol>(); 
		Set<String> mustChckList = new HashSet<>(); 
		List<String> resultOfIvcFinder = new ArrayList<>();
		List<String> inv = vm.invariants.stream().map(Object::toString).collect(toList()); 
		
		allIvcs.add(new Tuple<Set<String>, List<String>>(trimNode(new ArrayList<>(vm.ivc)), inv));
		seed.addAll(getIvcLiterals(new ArrayList<>(vm.ivc)));
		map = blockUp(seed);
		  
		//mustElements.add(property.toString());
		//map = new Cons("and", map, ivcMap.get(property.toString()));  
		
		z3Solver.push();
		while(checkMapSatisfiability(map, seed, mustChckList)){
			resultOfIvcFinder.clear();
			if (ivcFinder(seed, resultOfIvcFinder, mustChckList, property.toString())){
				map = new Cons("and", map, blockUp(getIvcLiterals(resultOfIvcFinder)));
			}else{
				map = new Cons("and", map, blockDown(getIvcLiterals(resultOfIvcFinder))); 
			}
		}
		
		z3Solver.pop();
		processMustElements(mustChckList, vm.ivc, property.toString());
		sendValid(property.toString(), vm);
	}

	private boolean ivcFinder(List<Symbol> seed, List<String> resultOfIvcFinder, Set<String> mustChckList, String property) {
		JKindSettings js = new JKindSettings();
		js.reduceIvc = true; 
		
		// optional-- could be commented later:
		//js.scratch = true;
		js.noSlicing = settings.noSlicing;
		js.allAssigned = settings.allAssigned;
		js.pdrMax = settings.pdrMax;
		js.boundedModelChecking = settings.boundedModelChecking;
		js.miniJkind = true;
		
		List <String> wantedElem = getIvcNames(new ArrayList<> (seed)); 
		List<String> deactivate = new ArrayList<>();
		deactivate.addAll(ivcMap.keyList());
		deactivate.removeAll(wantedElem);
		
		Node nodeSpec = unassign(spec.node, wantedElem, deactivate, property);  
		Specification newSpec = new Specification(nodeSpec, js.noSlicing);   
		if (settings.scratch){
			comment("Sending a request for a new IVC while deactivating "+ getIvcLiterals(deactivate));
		}
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
		miniJkind.verify();
		if(miniJkind.getPropertyStatus() == MiniJKind.UNKNOWN){
			js.pdrMax = 0;
			return retryVerification(newSpec, js,resultOfIvcFinder, mustChckList, deactivate);
		}
		else if(miniJkind.getPropertyStatus() == MiniJKind.VALID){
			mayElements.addAll(deactivate);
			mustChckList.removeAll(deactivate);
			
			resultOfIvcFinder.addAll(miniJkind.getPropertyIvc());
			Set<String> newIvc = trimNode(resultOfIvcFinder);
			
			if (settings.scratch){
				comment("New IVC set found: "+ getIvcLiterals(resultOfIvcFinder));
			} 
			
			Set<Tuple<Set<String>, List<String>>> temp = new HashSet<>();
			 
			
			for(Tuple<Set<String>, List<String>> curr: allIvcs){  
				if (curr.firstElement().containsAll(newIvc)){
					temp.add(curr);  
				}
				// the else part can only happen 
				//         while processing mustChckList after finding all IVC sets
				//         if we have different instances of a node in the Lustre file
				else if (newIvc.containsAll(curr.firstElement())){
					return true;
				} 
			}
			
			if(temp.isEmpty()){
				allIvcs.add(new Tuple<Set<String>, List<String>>(newIvc, miniJkind.getPropertyInvariants()));
			}
			else{
				allIvcs.removeAll(temp);
				allIvcs.add(new Tuple<Set<String>, List<String>>(newIvc, miniJkind.getPropertyInvariants()));
			}
 
			return true;
		}
		else{
			resultOfIvcFinder.addAll(deactivate); 
			if (settings.scratch){
				comment("Property got violated. Adding back the elements");
			}
			
			if(deactivate.size() == 1){
				mustElements.addAll(deactivate);
				mustChckList.removeAll(deactivate);
				if (settings.scratch){
					comment("One MUST element was found: "+ getIvcLiterals(deactivate));
				}
			}
			else{
				deactivate.removeAll(mustElements);
				deactivate.removeAll(mayElements);
				if (settings.scratch){
					comment(getIvcLiterals(deactivate) + " could be MUST elements; added to the check list...");
				}
			 
				mustChckList.addAll(deactivate);
			}
			return false;
		} 
	}

	private boolean retryVerification(Specification newSpec, JKindSettings js, List<String> resultOfIvcFinder,
			Set<String> mustChckList, List<String> deactivate) {
		if (settings.scratch){
			comment("Result was UNKNOWN; Resend the request with pdrMax = 0 ...");
		}
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
		miniJkind.verify();
		if(miniJkind.getPropertyStatus() == MiniJKind.VALID){
			mayElements.addAll(deactivate);
			mustChckList.removeAll(deactivate);
			
			resultOfIvcFinder.addAll(miniJkind.getPropertyIvc());
			Set<String> newIvc = trimNode(resultOfIvcFinder);
			
			if (settings.scratch){
				comment("New IVC set found: "+ getIvcLiterals(resultOfIvcFinder));
			} 
			
			Set<Tuple<Set<String>, List<String>>> temp = new HashSet<>();
			for(Tuple<Set<String>, List<String>> curr: allIvcs){  
				if (curr.firstElement().containsAll(newIvc)){
					temp.add(curr);  
				} 
				else if (newIvc.containsAll(curr.firstElement())){
					return true;
				} 
			}
			
			if(temp.isEmpty()){
				allIvcs.add(new Tuple<Set<String>, List<String>>(newIvc, miniJkind.getPropertyInvariants()));
			}
			else{
				allIvcs.removeAll(temp);
				allIvcs.add(new Tuple<Set<String>, List<String>>(newIvc, miniJkind.getPropertyInvariants()));
			}
 
			return true;
		}
		else{
			resultOfIvcFinder.addAll(deactivate); 
			if (settings.scratch){
				comment("Property got violated. Adding back the elements");
			}
			
			if(deactivate.size() == 1){
				mustElements.addAll(deactivate);
				mustChckList.removeAll(deactivate);
				if (settings.scratch){
					comment("One MUST element was found: "+ getIvcLiterals(deactivate));
				}
			}
			else{
				deactivate.removeAll(mustElements);
				deactivate.removeAll(mayElements);
				if (settings.scratch){
					comment(getIvcLiterals(deactivate) + " could be MUST elements; added to the check list...");
				}
			 
				mustChckList.addAll(deactivate);
			}
			return false;
		} 
	}

	private Sexp blockUp(List<Symbol> list) {
		List<Sexp> ret = new ArrayList<>();
		for(Symbol literal : list){
			ret.add(new Cons("not", literal));
		}
		return SexpUtil.disjoin(ret);
	}
	
	private Sexp blockDown(List<Symbol> list) {
		List<Sexp> ret = new ArrayList<>();
		for(Symbol literal : list){
			ret.add(literal);
		}
		return SexpUtil.disjoin(ret);
	}

	private boolean checkMapSatisfiability(Sexp map, List<Symbol> seed, Set<String> mustChckList) { 
		z3Solver.push(); 
		solver.assertSexp(map);
		Result result = z3Solver.checkSat(new ArrayList<>(), true, false);
		if (result instanceof UnsatResult){
			return false;
		}
		else if (result instanceof UnknownResult){
			throw new JKindException("Unknown result in solving map");
		} 
		 
		seed.clear();
		seed.addAll(maximizeSat(((SatResult) result), mustChckList)); 
		z3Solver.pop();
	
		return true;
	}
	
	/**
	 * in case of sat result we would like to get a maximum sat subset of activation literals 
	 * @param  
	 **/
	private List<Symbol> maximizeSat(SatResult result, Set<String> mustChckList) { 
		Set<Symbol> seed = getActiveLiteralsFromModel(result.getModel(), "true");
		Set<Symbol> falseLiterals = getActiveLiteralsFromModel(result.getModel(), "false");
		Set<Symbol> temp = new HashSet<>();
		temp.addAll(ivcMap.valueList());
		List<Symbol> literalList = getIvcLiterals(new ArrayList<>(mustChckList));
		temp.removeAll(literalList);
		temp.removeAll(falseLiterals);
		temp.removeAll(seed);
		
		for(Symbol literal : literalList){ 
			if(! seed.contains(literal)){
				seed.add(literal); 
				if(z3Solver.quickCheckSat(new ArrayList<>(seed)) instanceof UnsatResult){
					seed.remove(literal);
				}
			}
		}
		for(Symbol literal : falseLiterals){ 
			if(! seed.contains(literal)){
				seed.add(literal); 
				if(z3Solver.quickCheckSat(new ArrayList<>(seed)) instanceof UnsatResult){
					seed.remove(literal);
				}
			}
		}
		for(Symbol literal : temp){
			seed.add(literal); 
			if(z3Solver.quickCheckSat(new ArrayList<>(seed)) instanceof UnsatResult){
				seed.remove(literal);
			}
		}
		return new ArrayList<>(seed); 
	}

	private Set<Symbol> getActiveLiteralsFromModel(Model model, String val) {
		Set<Symbol> seed = new HashSet<>();
		for (String var : model.getVariableNames()) { 
			if(model.getValue(var).toString() == val){
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
	
	private List<Symbol> getIvcLiterals(List<String> resultOfIvcFinder) {
		List<Symbol> result = new ArrayList<>();
		for (String ivc : resultOfIvcFinder) {
			result.add(ivcMap.get(ivc));
		}
		return result;
	}

	private void sendValid(String valid, ValidMessage vm) {
		Itinerary itinerary = vm.getNextItinerary();
		director.broadcast(new ValidMessage(vm.source, valid, 0, null, mustElements , itinerary, allIvcs)); 
	}
	
	private Set<String> trimNode(List<String> set) {
		Set<String> ivc = new HashSet<>();
		for (String e : set) {
			ivc.add(e.replaceAll("~[0-9]+", ""));
		}
		return ivc;
	}
	
	private Node unassign(Node node, List<String> vars, List<String> deactivate, String property) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		
		for(String v : deactivate){
			inputs.add(new VarDecl(v, Util.getTypeMap(node).get(v))); 
		}

		List<VarDecl> locals = removeVariable(node.locals, deactivate);
		List<VarDecl> outputs = removeVariable(node.outputs, deactivate);
		List<Equation> equations = new ArrayList<>(node.equations);
		List<Expr> assertions = new ArrayList<>(node.assertions);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (deactivate.contains(eq.lhs.get(0).id)) {
				iter.remove(); 
			}
		}
		Iterator<Expr> iter0 = assertions.iterator();
		while (iter0.hasNext()) {
			Expr asr = iter0.next();
			if (deactivate.contains(asr.toString())) {
				iter0.remove(); 
			}
		}

		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions);
		builder.clearIvc().addIvcs(vars);
		builder.clearProperties().addProperty(property);
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
	
	private void processMustElements(Set<String> mustChckList, Set<String> initialIvc, String prop) { 
		// if the algorithm is not complete, we need to process mucstChckList instead of the following
			Set<String> intersect = new HashSet<>();
			intersect.addAll(initialIvc);
			for(Tuple<Set<String>, List<String>> item : allIvcs){
				intersect.retainAll(item.firstElement());
			}
				
			if(!(intersect.contains(prop))){
				Output.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
				Output.println("WARNING: some inconsistent equations were found: ");
				Output.println("property "+ prop + " is vacuously valid in some IVCs");
				Output.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
			}
				
			if(mustElements.size() < intersect.size()){
				Output.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
				Output.println("WARNING: IVC sets might not be truly minimal!");
				Output.println("So far "+ allIvcs.size() + " IVC sets have been found.");
				Output.println("Trying to minimize the sets...");
				Set<String> diff = new HashSet<>();
				diff.addAll(intersect);
				diff.removeAll(mustElements);
				List<String> extra = new ArrayList<>();
					
				for(String item : diff){
					List<Symbol> seed = new ArrayList<>();
					seed.addAll(ivcMap.valueList());
					seed.remove(ivcMap.get(item)); 
					ivcFinder(seed, new ArrayList<>(), mustChckList, prop);
				}
				Output.println("Total #of IVC sets found: "+ allIvcs.size());
				if(extra.size() > 0){
					minimizeSets(extra);
				}
				Output.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
			}
			else if(mustElements.size() > intersect.size()){
				Output.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
				Output.println("WARNING: FAILED TO FIND ONE OR MORE IVCs!");
				Output.println("So far "+ allIvcs.size() + " IVC sets have been found.");
				Output.println("Processing must-check-list to try find more IVCs...");
					
				mustChckList.removeAll(mustElements); 
				List<Symbol> seed = new ArrayList<>();
				seed.addAll(ivcMap.valueList());
				seed.removeAll(getIvcLiterals(new ArrayList<>(mustChckList)));
				List<String> resultOfIvcFinder = new ArrayList<>();
				if(mustChckList.size() > 0){
					ivcFinder(seed, resultOfIvcFinder, mustChckList, prop); 				
					if(mustChckList.size() == 0){
						Output.println("ALL the must-check-list has been processed."); 
					}else{
						Output.println("must-check-list has been processed."); 
					}
				}else{
						Output.println("must-check-list is empty. terminating the process..."); 
				}
					
				Output.println("Total #of IVC sets found: "+ allIvcs.size());
				Output.println("-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
			}
			 
			if(mustChckList.size() > 0){
				Output.println("WARNING: must-check-llist is not empty. there might be more IVCs...");
			}
	}

	private void minimizeSets(List<String> extra) {
		Output.println("WARNING: to reconstruct the proof with output invariants extra elements must be added back");
		for(Tuple<Set<String>, List<String>> item : allIvcs){
			item.firstElement().removeAll(extra);
		}
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
