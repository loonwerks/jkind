package jkind.engines.ivcs;
import static java.util.stream.Collectors.toList;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList; 
import java.util.HashSet;
import java.util.Iterator;
import java.util.List; 
import java.util.Set; 
import jkind.ExitCodes; 
import jkind.JKindException;
import jkind.JKindSettings;
import jkind.Output;
import jkind.engines.Director;
import jkind.engines.MiniJKind;
import jkind.engines.SolverBasedEngine;
import jkind.engines.ivcs.messages.ConsistencyMessage;
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
	
	// this variable is only used for the experiments
	private double runtime;
	
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
		
		//----- for the experiments---------
		runtime = System.currentTimeMillis(); 
		//-----------------------------------
		
		for (String property : vm.valid) {
			mayElements.clear();
			mustElements.clear(); 
			if (spec.node.ivc.isEmpty()) {
				Output.warning(NAME + ": --%IVC option has no argument for property  "+ property.toString());
				sendValid(property.toString(), vm);
				return;
			}
			if (properties.remove(property)) {
				computeAllIvcs(IvcUtil.getInvariantByName(property, vm.invariants), vm);
			}
			
		}
	}
	
	private void computeAllIvcs(Expr property, ValidMessage vm) {
		Sexp map;
		List<Symbol> seed = new ArrayList<Symbol>(); 
		Set<String> mustChckList = new HashSet<>(); 
		List<String> resultOfIvcFinder = new ArrayList<>();
		List<String> inv = vm.invariants.stream().map(Object::toString).collect(toList()); 
		Set<String> initialIvc = IvcUtil.trimNode(vm.ivc);
		allIvcs.add(new Tuple<Set<String>, List<String>>(initialIvc, inv));
		seed.addAll(IvcUtil.getIvcLiterals(ivcMap, new ArrayList<>(vm.ivc)));
		map = blockUp(seed);
		  
		//mustElements.add(property.toString());
		//map = new Cons("and", map, ivcMap.get(property.toString()));  
		
		z3Solver.push();

		while(checkMapSatisfiability(map, seed, mustChckList)){
			resultOfIvcFinder.clear();
			if (ivcFinder(seed, resultOfIvcFinder, mustChckList, property.toString())){
				map = new Cons("and", map, blockUp(IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder)));
			}else{
				map = new Cons("and", map, blockDown(IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder))); 
			}
		} 
		
		z3Solver.pop();
		
		//--------- for the experiments --------------
		runtime = (System.currentTimeMillis() - runtime) / 1000.0;
		recordRuntime();
		//--------------------------------------------
		
		processMustElements(mustChckList, initialIvc, property.toString());
		sendValid(property.toString(), vm);
	}

	private boolean ivcFinder(List<Symbol> seed, List<String> resultOfIvcFinder, Set<String> mustChckList, String property) {
		JKindSettings js = new JKindSettings();
		js.reduceIvc = true; 
		js.timeout = 300;
		
		// optional-- could be commented later:
		//js.scratch = true;
		js.noSlicing = settings.noSlicing;
		js.allAssigned = settings.allAssigned;
		js.pdrMax = settings.pdrMax;
		js.boundedModelChecking = settings.boundedModelChecking;
		js.miniJkind = true;
		
		Set <String> wantedElem = IvcUtil.getIvcNames(ivcMap, new ArrayList<> (seed)); 
		List<String> deactivate = new ArrayList<>();
		deactivate.addAll(ivcMap.keyList());
		deactivate.removeAll(wantedElem);
		
		Node nodeSpec = IvcUtil.unassign(spec.node, wantedElem, deactivate, property);  
		Specification newSpec = new Specification(nodeSpec, js.noSlicing);   
		if (settings.scratch){
			comment("Sending a request for a new IVC while deactivating "+ IvcUtil.getIvcLiterals(ivcMap, deactivate));
		}
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
		miniJkind.verify();
		if(miniJkind.getPropertyStatus() == MiniJKind.NOT_YET_CHECKED){
			js.pdrMax = 0;
			return retryVerification(newSpec, property, js, resultOfIvcFinder, mustChckList, deactivate);
		}
		else if(miniJkind.getPropertyStatus() == MiniJKind.VALID){
			mayElements.addAll(deactivate);
			mustChckList.removeAll(deactivate);
			
			resultOfIvcFinder.addAll(miniJkind.getPropertyIvc());
			Set<String> newIvc = IvcUtil.trimNode(resultOfIvcFinder);
			
			if (settings.scratch){
				comment("New IVC set found: "+ IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder));
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
				director.handleConsistencyMessage(new ConsistencyMessage(miniJkind.getValidMessage()));
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
					comment("One MUST element was found: "+ IvcUtil.getIvcLiterals(ivcMap, deactivate));
				}
			}
			else{
				deactivate.removeAll(mustElements);
				deactivate.removeAll(mayElements);
				if (settings.scratch){
					comment(IvcUtil.getIvcLiterals(ivcMap, deactivate) + " could be MUST elements; added to the check list...");
				}
			 
				mustChckList.addAll(deactivate);
			}
			return false;
		} 
	}

	private boolean retryVerification(Specification newSpec, String prop, JKindSettings js, List<String> resultOfIvcFinder,
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
			Set<String> newIvc = IvcUtil.trimNode(resultOfIvcFinder);
			
			if (settings.scratch){
				comment("New IVC set found: "+ IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder));
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
				director.handleConsistencyMessage(new ConsistencyMessage(miniJkind.getValidMessage()));
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
					comment("One MUST element was found: "+ IvcUtil.getIvcLiterals(ivcMap, deactivate));
				}
			}
			else{
				deactivate.removeAll(mustElements);
				deactivate.removeAll(mayElements);
				if (settings.scratch){
					comment(IvcUtil.getIvcLiterals(ivcMap, deactivate) + " could be MUST elements; added to the check list...");
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
	 **/
	private List<Symbol> maximizeSat(SatResult result, Set<String> mustChckList) { 
		Set<Symbol> seed = getActiveLiteralsFromModel(result.getModel(), "true");
		Set<Symbol> falseLiterals = getActiveLiteralsFromModel(result.getModel(), "false");
		Set<Symbol> temp = new HashSet<>();
		temp.addAll(ivcMap.valueList());
		List<Symbol> literalList = IvcUtil.getIvcLiterals(ivcMap, new ArrayList<>(mustChckList));
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

	private void sendValid(String valid, ValidMessage vm) {
		Itinerary itinerary = vm.getNextItinerary();
		IvcUtil.findRightSide(mustElements, settings.allAssigned, spec.node.equations);
		for(Tuple<Set<String>, List<String>> pair : allIvcs){
			IvcUtil.findRightSide(pair.firstElement(), settings.allAssigned, spec.node.equations);
		}
		director.broadcast(new ValidMessage(vm.source, valid, 0, null, IvcUtil.trimNode(mustElements) , itinerary, allIvcs)); 
	}
	
	private void processMustElements (Set<String> mustChckList, Set<String> initialIvc, String prop) { 
		Set<String> smallestSet = initialIvc; 
		
		for(Tuple<Set<String>, List<String>> item : allIvcs){
			if(item.firstElement().size() < smallestSet.size()){
				smallestSet = item.firstElement();
			}
		}
		
		Set<String> candidates = new HashSet<>(smallestSet);
		Set<String> trimmedMustList = IvcUtil.trimNode(mustElements);
		candidates.removeAll(trimmedMustList); 
		MinimalIvcFinder minimalFinder = new MinimalIvcFinder(spec.node, settings.filename, prop);
		
		/*
		 * for now, we don't really use this part. The output only matters for the experiments
		 * if we're not running experiment the following line should be replaced with the next
		 * */   
		
		Set<String> minimalIvc = minimalFinder.reduce(candidates, trimmedMustList, true);
		//Set<String> minimalIvc = minimalFinder.reduce(candidates, mustElements, false); 
		//processIntersection(mustChckList, initialIvc, prop);
	}
	
	/** we don't have a well-formed idea for this method yet. 
	 * the following implementation puts just some pieces of code together that might be used later
	 */
	private void processIntersection(Set<String> mustChckList, Set<String> initialIvc, String prop) { 
		// if the algorithm is not complete, we need to process mucstChckList instead of the following
		Set<String> intersect = new HashSet<>();
		intersect.addAll(initialIvc); 
		
		for(Tuple<Set<String>, List<String>> item : allIvcs){
			intersect.retainAll(item.firstElement()); 
		}	
		Set<String> temp = new HashSet<>();
		for(Tuple<Set<String>, List<String>> item : allIvcs){
			temp.addAll(item.firstElement());
			temp.removeAll(intersect);
			mayElements.addAll(temp);
			mustChckList.removeAll(temp);
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
				seed.removeAll(IvcUtil.getIvcLiterals(ivcMap, new ArrayList<>(mustChckList)));
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
	
	// this method is used only in our experiments
	private void recordRuntime() {
		String xmlFilename = settings.filename + "_runtimeAllIvcs.xml";
		try (PrintWriter out = new PrintWriter(new FileOutputStream(xmlFilename))) {
			out.println("<?xml version=\"1.0\"?>");
			out.println("<Results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"); 
			out.println("  <Runtime unit=\"sec\">" + runtime + "</Runtime>");
			out.println("</Results>");
			out.flush();
			out.close();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
		
	}
}
