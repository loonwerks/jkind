package jkind.engines.ivcs;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.ExitCodes;
import jkind.JKindException;
import jkind.JKindSettings;
import jkind.SolverOption;
import jkind.StdErr;
import jkind.engines.Director;
import jkind.engines.MiniJKind;
import jkind.engines.SolverBasedEngine;
import jkind.engines.SolverUtil;
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
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.Solver;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.LinkedBiMap;
import jkind.util.SexpUtil;
import jkind.util.Tuple; 
import jkind.util.Util;

public class AllIvcsExtractorEngine extends SolverBasedEngine {
	public static final String NAME = "all-ivc-computer";
	private final LinkedBiMap<String, Symbol> ivcMap;
	private Sexp map;
	private List<List<Symbol>> shrinkingPool = new ArrayList<List<Symbol>>();
	private List<List<Symbol>> growingPool = new ArrayList<List<Symbol>>();
	
	private Z3Solver z3Solver;	   
	private Set<String> mustElements = new HashSet<>();
	private Set<String> mayElements = new HashSet<>();  
	Set<Tuple<Set<String>, List<String>>> allIvcs = new HashSet<>();
	private int TIMEOUT; 
	private int numOfGetIvcCalls = 1;
	private int numOfTimedOuts = 0;
	private ValidMessage gvm;
	// these variables are only used for the experiments
		private double runtime;  
		private int runId = 0;
		private int shrinks = 0;
		private int satChecks = 0;
		private int unsatChecks = 0;	
		private int mivcs = 0;		
	//--------------------------------------------------

	public AllIvcsExtractorEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director); 
		ivcMap = Lustre2Sexp.createIvcMap(spec.node.ivc); 
	}

	@Override
	protected Solver getSolver() {
		return SolverUtil.getSolver(SolverOption.Z3, getScratchBase(), spec.node);
	}

	protected void initializeSolver() {
		solver = getSolver();
		solver.initialize();
		z3Solver = (Z3Solver) solver; 

		for (Symbol e : ivcMap.values()) {
			solver.define(new VarDecl(e.str, NamedType.BOOL));
		}
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}
	
	private void reduce(ValidMessage vm) { 
		
		//----- for the experiments---------
				runtime = System.currentTimeMillis(); 
				// dummy
				gvm = vm;
				//
		//-----------------------------------
				
		for (String property : vm.valid) {
			mayElements.clear();
			mustElements.clear(); 
			if (spec.node.ivc.isEmpty()) {
				StdErr.warning(NAME + ": --%IVC option has no argument for property  "+ property.toString());
				sendValid(property.toString(), vm);
				return;
			}
			
			if (properties.remove(property)) {				
				switch(settings.allIvcsAlgorithm) {
					case 1: //offline MIVC enumeration	
						System.out.println("Starting MIVC enumeration using the offline enumeration algorithm.");
						computeAllIvcs(IvcUtil.getInvariantByName(property, vm.invariants), vm);
						break;
					case 2: //online MIVC enumeration
						System.out.println("Starting MIVC enumeration using the online enumeration algorithm.");
						computeAllIvcsMapBased(IvcUtil.getInvariantByName(property, vm.invariants), vm);
						break;
				} 
				allIvcs.clear();
			}
				
		}
	}
	
	//JB, offline MIVC enumeration algorithm as described in the FMCAD 2017 paper
	private void computeAllIvcs(Expr property, ValidMessage vm) { 			
		TIMEOUT = 30 + (int)(vm.proofTime * 5);		
		List<Symbol> seed = new ArrayList<Symbol>(); 
		Set<String> mustChckList = new HashSet<>(); 
		Set<String> resultOfIvcFinder = new HashSet<>();
		List<String> inv = vm.invariants.stream().map(Object::toString).collect(toList());  
		allIvcs.add(new Tuple<Set<String>, List<String>>(vm.ivc, inv));		
		seed.addAll(IvcUtil.getIvcLiterals(ivcMap, new ArrayList<>(vm.ivc)));
		map = blockUp(seed);  
		
		mustElements.add(property.toString());
		if (ivcMap.containsKey(property.toString())){
			map = new Cons("and", map, ivcMap.get(property.toString())); 
		} 
		z3Solver.push();
		while(checkMapSatisfiability(seed, mustChckList, true)){ 	
			resultOfIvcFinder.clear(); 
			if (ivcFinder(seed, resultOfIvcFinder, mustChckList, property.toString())){				
				map = new Cons("and", map, blockUp(IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder)));
				//markMIVC(IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder));				
			}else{				
				map = new Cons("and", map, blockDown(IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder))); 
			}
		} 		
		z3Solver.pop(); 
		
		//--------- for the experiments --------------
				//runtime = (System.currentTimeMillis() - runtime) / 1000.0;
				//recordRuntime(false);
		//--------------------------------------------
				
		sendValid(property.toString(), vm);
	}

	//JB, online MIVC enumeration algorithm as described in the SEFM 2018 paper
	private void computeAllIvcsMapBased(Expr property, ValidMessage vm) { 		
		TIMEOUT = 30 + (int)(vm.proofTime * 5);		
		List<Symbol> seed = new ArrayList<Symbol>(); 
		Set<String> mustChckList = new HashSet<>(); 
		Set<String> resultOfIvcFinder = new HashSet<>();
		
		seed.addAll(IvcUtil.getIvcLiterals(ivcMap, new ArrayList<>(vm.ivc)));
		map = blockUp(seed);  
		shrinkingPool.add(new ArrayList<Symbol>(seed));
		
		mustElements.add(property.toString());
		if (ivcMap.containsKey(property.toString())){
			map = new Cons("and", map, ivcMap.get(property.toString())); 
		} 
		z3Solver.push();
		while(checkMapSatisfiability(seed, mustChckList, true)){ 			
			resultOfIvcFinder.clear(); 			
			if (ivcFinderSimple(seed, resultOfIvcFinder, property.toString())){				
				seed = IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder);				
				mapShrink(seed, property.toString());				
			}else{
				map = new Cons("and", map, blockDownComplement(seed)); 
			}
			while(!shrinkingPool.isEmpty()) {
				List<Symbol> ivc = shrinkingPool.get(0);
				shrinkingPool.remove(0);				
				mapShrink(ivc, property.toString());
			}						
		}
		
		z3Solver.pop(); 
		
		//--------- for the experiments --------------
				//runtime = (System.currentTimeMillis() - runtime) / 1000.0;
				//recordRuntime(false);
		//--------------------------------------------
				
		sendValid(property.toString(), vm);
	}
			
	private boolean ivcFinder(List<Symbol> seed, Set<String> resultOfIvcFinder, Set<String> mustChckList, String property) {
		JKindSettings js = new JKindSettings();
		js.reduceIvc = true; 
		js.timeout = TIMEOUT; 
		// optional-- could be commented later:
		//js.scratch = true;
		js.solver = settings.solver;
		js.slicing = true; 
		js.pdrMax = settings.pdrMax;
		js.boundedModelChecking = settings.boundedModelChecking;
        js.miniJkind = true;
        js.readAdvice = js.writeAdvice;
        numOfGetIvcCalls ++;
		Set <String> wantedElem = IvcUtil.getIvcNames(ivcMap, new ArrayList<> (seed)); 
		List<String> deactivate = new ArrayList<>();
		deactivate.addAll(ivcMap.keyList());
		deactivate.removeAll(wantedElem);
		
		Node nodeSpec = IvcUtil.unassign(spec.node, deactivate, property);  
		Specification newSpec = new Specification(nodeSpec, js.slicing);  
		
		if (settings.scratch){
			comment("Sending a request for a new IVC while deactivating "+ IvcUtil.getIvcLiterals(ivcMap, deactivate));
		}
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
		miniJkind.verify();
		runId++;
		
		//--------- for the experiments --------------
        	//writeToXmlAllIvcRuns(miniJkind.getPropertyStatus(), miniJkind.getRuntime()); 
        //--------------------------------------------
        
        if(miniJkind.getPropertyStatus().equals(MiniJKind.UNKNOW_WITH_EXCEPTION)){
			js.pdrMax = 0;
			return retryVerification(newSpec, property, js, resultOfIvcFinder, mustChckList, deactivate);
		}
		else if(miniJkind.getPropertyStatus().equals(MiniJKind.VALID)){			
			mayElements.addAll(deactivate); //JB why? the deactivate elements are not contained in the ivc
			mustChckList.removeAll(deactivate);
			
			resultOfIvcFinder.addAll(miniJkind.getPropertyIvc());
			Set<String> newIvc = IvcUtil.trimNode(resultOfIvcFinder);			
			if (settings.scratch){
				comment("New IVC set found: "+ IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder));
			} 
			
			Set<Tuple<Set<String>, List<String>>> temp = new HashSet<>();
			
			for(Tuple<Set<String>, List<String>> curr: allIvcs){  				
				Set<String> trimmed = IvcUtil.trimNode(curr.firstElement());				
				if (trimmed.containsAll(newIvc)){
					temp.add(curr);  
				}				
				// the else part can only happen 
				//         while processing mustChckList after finding all IVC sets
				//         if we have different instances of a node in the Lustre file
				if (newIvc.containsAll(trimmed)){					
					throw new Error("Elaheh says this will never happen!!!");
				} 
			} 			
			if(temp.isEmpty()){ 				
				//director.handleConsistencyMessage(new ConsistencyMessage(miniJkind.getValidMessage()));
				allIvcs.add(new Tuple<Set<String>, List<String>>(miniJkind.getPropertyIvc(), miniJkind.getPropertyInvariants()));
				
				//---------------------for experiments ------------------
				writeToXmlAllIvcs(newIvc, miniJkind.getPropertyIvc(), miniJkind.getRuntime(), true) ;
				//--------------------------------------------------------
			}
			else{ 				
				allIvcs.removeAll(temp);
				allIvcs.add(new Tuple<Set<String>, List<String>>(miniJkind.getPropertyIvc(), miniJkind.getPropertyInvariants()));
			
				//---------------------for experiments ------------------
				writeToXmlAllIvcs(newIvc, miniJkind.getPropertyIvc(), miniJkind.getRuntime(),false) ;
				//--------------------------------------------------------
			}
			unsatChecks++;
			return true;
		}
		else{				
			if(miniJkind.getPropertyStatus().equals(MiniJKind.UNKNOWN)){
				numOfTimedOuts++;
				if(numOfTimedOuts == 1) {
					System.out.println("A timeout occured during checking a property. Thus, the produced MIVCs might not be minimal.");
				}
			}
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
			satChecks++;
			return false;
		} 
	}
	
	//JB
	private boolean ivcFinderSimple(List<Symbol> seed, Set<String> resultOfIvcFinder, String property) {
		JKindSettings js = new JKindSettings();
		js.reduceIvc = true; 
		js.timeout = TIMEOUT; 
		// optional-- could be commented later:
		//js.scratch = true;
		js.solver = settings.solver;
		js.slicing = true; 
		js.pdrMax = settings.pdrMax;
		js.boundedModelChecking = settings.boundedModelChecking;
        js.miniJkind = true;
        js.readAdvice = js.writeAdvice;
        numOfGetIvcCalls ++;
		Set <String> wantedElem = IvcUtil.getIvcNames(ivcMap, new ArrayList<> (seed)); 
		List<String> deactivate = new ArrayList<>();
		deactivate.addAll(ivcMap.keyList());
		deactivate.removeAll(wantedElem);
		
		Node nodeSpec = IvcUtil.unassign(spec.node, deactivate, property);  
		Specification newSpec = new Specification(nodeSpec, js.slicing);  
				
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
		miniJkind.verify();
		runId++;
		
		//--------- for the experiments --------------
			//writeToXmlAllIvcRuns(miniJkind.getPropertyStatus(), miniJkind.getRuntime());
		//--------------------------------------------
		
		if(miniJkind.getPropertyStatus().equals(MiniJKind.UNKNOW_WITH_EXCEPTION)){			
			js.pdrMax = 0;		
			return retryVerification(newSpec, property, js, resultOfIvcFinder, new HashSet<>(), deactivate);
		}
		else if(miniJkind.getPropertyStatus().equals(MiniJKind.VALID)){											
			resultOfIvcFinder.addAll(miniJkind.getPropertyIvc());	
			unsatChecks++;
			return true;
		}
		else{				
			if(miniJkind.getPropertyStatus().equals(MiniJKind.UNKNOWN)){
				numOfTimedOuts ++;
				if(numOfTimedOuts == 1) {
					System.out.println("A timeout occured during checking a property. Thus, the produced MIVCs might not be minimal.");
				}
			}
			if(deactivate.size() == 1){
				mustElements.addAll(deactivate);
			}
			satChecks++;
			return false;
		} 
	}
	
	//JB
	private boolean check(List<Symbol> seed, String property) {
		JKindSettings js = new JKindSettings();
		js.reduceIvc = false; 
		js.timeout = TIMEOUT; 
		// optional-- could be commented later:
		//js.scratch = true;
		js.solver = settings.solver;
		js.slicing = true; 
		js.pdrMax = settings.pdrMax;
		js.boundedModelChecking = settings.boundedModelChecking;
        js.miniJkind = true;
        js.readAdvice = js.writeAdvice;
        numOfGetIvcCalls ++;
		Set <String> wantedElem = IvcUtil.getIvcNames(ivcMap, new ArrayList<> (seed)); 
		List<String> deactivate = new ArrayList<>();
		deactivate.addAll(ivcMap.keyList());
		deactivate.removeAll(wantedElem);
		
		Node nodeSpec = IvcUtil.unassign(spec.node, deactivate, property);  
		Specification newSpec = new Specification(nodeSpec, js.slicing);  
		
		if (settings.scratch){
			comment("Sending a request for a new IVC while deactivating "+ IvcUtil.getIvcLiterals(ivcMap, deactivate));
		}
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
		miniJkind.verify();
		runId++;
		
		//--------- for the experiments --------------
			//writeToXmlAllIvcRuns(miniJkind.getPropertyStatus() , miniJkind.getRuntime());
		//--------------------------------------------
		
		if(miniJkind.getPropertyStatus().equals(MiniJKind.UNKNOW_WITH_EXCEPTION)){
			//	js.pdrMax = 0;
		//	return retryVerification(newSpec, property, js, resultOfIvcFinder, mustChckList, deactivate);
		}
		//else 
		if(miniJkind.getPropertyStatus().equals(MiniJKind.VALID)){			
    		unsatChecks++;
			return false;			
		}
		else{				
			if(miniJkind.getPropertyStatus().equals(MiniJKind.UNKNOWN)){
				numOfTimedOuts++;
				if(numOfTimedOuts == 1) {
					System.out.println("A timeout occured during checking a property. Thus, the produced MIVCs might not be minimal.");
				}
			}			
			satChecks++;
			return true;
		} 
	}
		
	//JB
	private boolean GrowByElimination(List<Symbol> seed, String property) {
		List<Symbol> top = getTopUnex(seed);
		List<Symbol> added = new ArrayList<Symbol>(top);
		added.removeAll(seed);
		
		Set<String> resultOfIvcFinder = new HashSet<>();
		while (ivcFinderSimple(top, resultOfIvcFinder, property.toString())){		
			List<Symbol> approx = new ArrayList<Symbol>(IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder));
			
			List<List<Symbol>> toRemove = new ArrayList<List<Symbol>>();
			for(List<Symbol> s: shrinkingPool) {
				if(s.containsAll(approx)) {
					toRemove.add(s);
				}				
			}
			shrinkingPool.removeAll(toRemove);
			
			map = new Cons("and", map, blockUp(approx));
			shrinkingPool.add(approx);

			boolean removed = false;
			for(Symbol s: approx) {
				if(added.contains(s)) {
					added.remove(s);
					top.remove(s);
					removed = true;
					break;
				}
			}							
			if(!removed) //seed is a MSS
				break;
			resultOfIvcFinder.clear();
		}		
		map = new Cons("and", map, blockDownComplement(top));		
		return true;		
	}
					
	//JB
	private boolean checkMap(List<Symbol> seed) {
		z3Solver.push();  
		List<Symbol> positiveLits = new ArrayList<Symbol>(seed); 		
		List<Symbol> negatedLits = new ArrayList<>(ivcMap.valueList());		
		negatedLits.removeAll(seed);
		
		for(Symbol s: negatedLits) {			
			if(mustElements.contains(s.toString())) {
				return false;
			}
		}
					
		solver.assertSexp(map);
		
		Result result = z3Solver.checkValuation(positiveLits, negatedLits, false);
		z3Solver.pop();
		if (result instanceof UnsatResult){
			return false;
		}
		else if (result instanceof UnknownResult){
			throw new JKindException("Unknown result in solving map");
		} 
		 			
		return true;
	}
	
	//JB
	private boolean mapShrink(List<Symbol> seed, String property) {
		shrinks++;
				
		List<Symbol> candidates = new ArrayList<Symbol>(seed);		 
		for(Symbol c : candidates) {			
			seed.remove(c);
			if(!checkMap(seed) || mustElements.contains(c.toString())) { 
				seed.add(c);				
				continue;
			}
			if(check(seed, property)) {			
				ArrayList<Symbol> copy = new ArrayList<Symbol>(seed);				
				growingPool.add(copy);																
				seed.add(c);				
			}			
		}		
		map = new Cons("and", map, blockUp(seed));			
		markMIVC(seed);

		//update the shrinking pool (some seeds might be supersets of the seed)
		List<List<Symbol>> toRemove = new ArrayList<List<Symbol>>();
		for(List<Symbol> s: shrinkingPool) {
			if(s.containsAll(seed)) {
				toRemove.add(s);					
			}							
		}
		shrinkingPool.removeAll(toRemove);
		
		int remainingGrows = settings.allIvcsMaxGrows;
		while(!growingPool.isEmpty()) {			
			List<Symbol> is = growingPool.get(0);
			growingPool.remove(0);			

			if(remainingGrows > 0) {
				remainingGrows--;
				GrowByElimination(is, property.toString());
			}
			else {
				map = new Cons("and", map, blockDownComplement(is));
			}															
		}		
		return true;
	}
	
	//JB
	private void markMIVC(List<Symbol> mivc) {	
		mivcs++;
		Set<String> mivc_set = IvcUtil.getIvcNames(ivcMap, mivc);
				
		allIvcs.add(new Tuple<Set<String>, List<String>>(mivc_set, new ArrayList<String>() ));
		
		double time = (System.currentTimeMillis() - runtime) / 1000.0;	
		
		writeToXmlAllIvcs(new HashSet<String>(), mivc_set, time, true) ;
		System.out.printf("%d MIVC found, size: %d, time: %f, total SAT checks: %d, total UNSAT checks: %d %n", mivcs, mivc.size(), time, satChecks, unsatChecks);
	}

	
	
	private boolean retryVerification(Specification newSpec, String prop, JKindSettings js, Set<String> resultOfIvcFinder,
			Set<String> mustChckList, List<String> deactivate) {
		if (settings.scratch){
			comment("Result was UNKNOWN; Resend the request with pdrMax = 0 ...");
		}
		MiniJKind miniJkind = new MiniJKind (newSpec, js);
		miniJkind.verify();
		if(miniJkind.getPropertyStatus().equals(MiniJKind.VALID)){
			mayElements.addAll(deactivate);
			mustChckList.removeAll(deactivate);
			
			resultOfIvcFinder.addAll(miniJkind.getPropertyIvc());
			Set<String> newIvc = IvcUtil.trimNode(resultOfIvcFinder);
			
			if (settings.scratch){
				comment("New IVC set found: "+ IvcUtil.getIvcLiterals(ivcMap, resultOfIvcFinder));
			} 
			
			Set<Tuple<Set<String>, List<String>>> temp = new HashSet<>();
			for(Tuple<Set<String>, List<String>> curr: allIvcs){  
				Set<String> trimmed = IvcUtil.trimNode(curr.firstElement());
				if (trimmed.containsAll(newIvc)){
					temp.add(curr);  
				} 
				else if (newIvc.containsAll(trimmed)){
					return true;
				} 
			}
			
			if(temp.isEmpty()){ 
				//director.handleConsistencyMessage(new ConsistencyMessage(miniJkind.getValidMessage()));
				allIvcs.add(new Tuple<Set<String>, List<String>>(miniJkind.getPropertyIvc(), miniJkind.getPropertyInvariants()));
			}
			else{
				allIvcs.removeAll(temp);
				allIvcs.add(new Tuple<Set<String>, List<String>>(miniJkind.getPropertyIvc(), miniJkind.getPropertyInvariants()));
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

	private Sexp blockUp(Collection<Symbol> list) {
		List<Sexp> ret = new ArrayList<>();
		for(Symbol literal : list){
			ret.add(new Cons("not", literal));
		}
		return SexpUtil.disjoin(ret);
	}
	
	//JB -- this is not blockDown!!! It should be implemented as the function blockDownComplement
	private Sexp blockDown(Collection<Symbol> list) {
		List<Sexp> ret = new ArrayList<>();
		for(Symbol literal : list){
			ret.add(literal);
		}
		return SexpUtil.disjoin(ret);
	}
	
	//JB
	private Sexp blockDownComplement(Collection<Symbol> list) {
		List<Sexp> temp = new ArrayList<>(ivcMap.valueList());		
		temp.removeAll(list);
		
		return SexpUtil.disjoin(temp);
	}

	private boolean checkMapSatisfiability(List<Symbol> seed, Set<String> mustChckList, boolean maximal) { 
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
		if(maximal)
			seed.addAll(maximizeSat(((SatResult) result), mustChckList)); 
		else
			seed.addAll(minimizeSat(((SatResult) result), mustChckList));
		z3Solver.pop();
		return true;
	}
	
	//JB
	private List<Symbol> getTopUnex(List<Symbol> seed){			
		solver.push();
		solver.assertSexp(new Cons("and", map, SexpUtil.conjoin(seed)));
		Result result = z3Solver.checkMaximal();
		solver.pop();
		
		if(result instanceof SatResult) {
			SatResult sat = (SatResult) result;
			Set<Symbol> top = getActiveLiteralsFromModel(sat.getModel(), "true");
			return new ArrayList<Symbol>(top);
		}
		return seed;
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

	/**
	 * in case of sat result we would like to get a minimum sat subset of activation literals 
	 **/
	private List<Symbol> minimizeSat(SatResult result, Set<String> mustChckList) { 
		Set<Symbol> seed = getActiveLiteralsFromModel(result.getModel(), "true");		
		List<Symbol> minimal = new ArrayList<Symbol>(seed);
		List<Symbol> toRemove = new ArrayList<Symbol>(seed);
		for(String m: mustElements) {
			toRemove.remove(ivcMap.get(m)); // ensure that must elements are not removed, especially the property must remain
		}		
		
		for(Symbol s: toRemove) {
			minimal.remove(s);
			if(!checkMap(minimal)) {
				minimal.add(s);
			}				
		}
		return minimal;
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
		director.broadcast(new ValidMessage(vm.source, valid, vm.k, vm.proofTime, null, mustElements, itinerary, allIvcs)); 
	}
	
	public  void getValid(){
		//--------- for the experiments --------------
			//runtime = (System.currentTimeMillis() - runtime) / 1000.0;
			//recordRuntime(true);
		//--------------------------------------------
		sendValid("OK", gvm);
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
		private void recordRuntime(boolean timedout) {
			String xmlFilename = settings.filename + "_alg" + settings.allIvcsAlgorithm + "_runtimeAllIvcs.xml";
			try (PrintWriter out = new PrintWriter(new FileOutputStream(xmlFilename))) {
				out.println("<?xml version=\"1.0\"?>");
				out.println("<Results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"); 
				out.println("  <AllIvcRuntime unit=\"sec\">" + runtime + "</AllIvcRuntime>");
				out.println("  <NumOfGetIvcCalls>" + numOfGetIvcCalls + "</NumOfGetIvcCalls>");
				out.println("  <NumOfTimedOuts>" + numOfTimedOuts + "</NumOfTimedOuts>");
				out.println("  <Timedout>" + timedout + "</Timedout>");
				out.println("</Results>");
				out.flush();
				out.close();
			} catch (Throwable t) {
				t.printStackTrace();
				System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
			}
			
	}
		
		//recording (minimal) IVCs found during the computation
		private void writeToXmlAllIvcs(Set<String> trimmed, Set<String> untrimmed, double d, boolean isNew) {
	 		String xmlFilename = settings.filename + "_alg" + settings.allIvcsAlgorithm + "_all_uc_minijkind.xml";  
 
			try (PrintWriter out = new PrintWriter(new FileOutputStream(new File(xmlFilename), true))) { 
				out.println("<Timeouts>" + numOfTimedOuts + "</Timeouts>");
				out.println("<Results>");
				out.println("   <RunID>" + runId + "</RunID>"); 
				out.println("   <NewSet>" + isNew + "</NewSet>"); 
				out.println("   <Time unit=\"sec\">" + d + "</Time>");
				out.println("   <SatChecks>" + satChecks + "</SatChecks>");
				out.println("   <UnsatChecks>" + unsatChecks + "</UnsatChecks>");
				out.println("   <ID>" + mivcs + "</ID>");					
				for (String s : untrimmed) {
					out.println("   <IVC>" + s + "</IVC>");
				}
				for (String s : trimmed) {
					out.println("   <TRIVC>" + s + "</TRIVC>");
				}
				out.println("</Results>");
				out.flush(); 
				out.close(); 
			} catch (Throwable t) { 
				t.printStackTrace();
				System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
			}
			
		}	

		// recording intermediate results for the experiments
		private void writeToXmlAllShrinks(int initial_size, int final_size, int sat_checks, int unsat_checks, double shrink_runtime) {
	 		String xmlFilename = settings.filename + "_alg" + settings.allIvcsAlgorithm + "_all_shrinks.xml";  
 
			try (PrintWriter out = new PrintWriter(new FileOutputStream(new File(xmlFilename), true))) { 
				out.println("<Result>");
				out.println("   <ShrinkID>" + shrinks + "</ShrinkID>"); 				
				out.println("   <InitialSize>" + initial_size + "</InitialSize>"); 
				out.println("   <FinalSize>" + final_size + "</FinalSize>");
				out.println("   <SatChecks>" + sat_checks + "</SatChecks>");
				out.println("   <UnsatChecks>" + unsat_checks + "</UnsatChecks>");				
				out.println("   <Runtime unit=\"sec\">" + shrink_runtime + "</Runtime>");
				out.println("</Result>");
				out.flush(); 
				out.close(); 
			} catch (Throwable t) { 
				t.printStackTrace();
				System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
			}
			
		}
		
		// recording intermediate results for the experiments	 
		private void writeToXmlAllIvcRuns(String res, double d) { 
			String xmlFilename = settings.filename + "_alg" + settings.allIvcsAlgorithm + "_allivcs_inter_loop_runs.xml";  
 
			try (PrintWriter out = new PrintWriter(new FileOutputStream(new File(xmlFilename), true))) { 
				out.println("<Run>");
				out.println("   <RunID>" + runId + "</RunID>");  
				out.println("   <Result>" + res + "</Result>");  
				out.println("   <Time unit=\"sec\">" + d + "</Time>");  
				out.println("</Run>");
				out.flush(); 
				out.close(); 
			} catch (Throwable t) { 
				t.printStackTrace();
				System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
			}
			
		}
	
}
