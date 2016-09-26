package jkind.engines.ivcs;  
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet; 
import java.util.List; 
import java.util.Set; 
import jkind.JKind; 
import jkind.JKindSettings;  
import jkind.engines.Director; 
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
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr; 
import jkind.lustre.Equation;
import jkind.lustre.Expr; 
import jkind.lustre.NamedType;
import jkind.lustre.Node;  
import jkind.lustre.Type; 
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder; 
import jkind.lustre.values.Value;   
import jkind.results.Counterexample;
import jkind.results.Signal; 
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol; 
import jkind.slicing.ModelSlicer;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.SimpleModel;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.CounterexampleExtractor;
import jkind.util.ModelReconstructionEvaluator;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;
import jkind.util.Util; 

 
public class ConsistencyChecker  extends SolverBasedEngine {
	public static final String NAME = "consistency-checker";
	private Specification localSpec; 
	private Z3Solver z3Solver;
	private ConsistencyMessage message = null; 
	private static final String PROP_NAME = "__newPrpAddedByConsistencyChecker_by_JKind__";
	private static final String EF = "__ef__CTL_operator__JKind"; 
	
	public ConsistencyChecker(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);  
	}
	
	@Override
	protected void initializeSolver() {
		solver = getSolver();
		solver.initialize();
		z3Solver = (Z3Solver) solver;
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private void check(ValidMessage vm) {
		for (String property : vm.valid) {
			if (properties.remove(property)) {
				message = new ConsistencyMessage(vm);
				check(vm, property);
			}
		}
	}
	
	/**
	 * This method works based on a given set of IVCs
	 * It checks if an isolated part of the model by that set is inconsistent or not
	 **/
	private void check(ValidMessage vm, String prop) {
		        /*
				   create an over-approx of the model with IVCs
				   will make all the assertions regular equations
				   define a new property which is the same as "EF(not conjunction of the assertions)"
				   we need this over-approx node because 
			           1- we might have several IVCs
			           2- it would be cheaper to verify
				   if proof goes through ==> no inconsistency
				   otherwise ==> it will find an example to show the inconsistency 
				*/  
		Node mutant = defineNewProperty();
		localSpec = new Specification(
						IvcUtil.overApproximateWithIvc(mutant, 
								         vm.ivc, mutant.properties.get(0)), settings.noSlicing); 
		
		solver.define(localSpec.getTransitionRelation());
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
		solver.push();
		
		defineEF();
		
		Result result = z3Solver.checkSat(new ArrayList<>(), true, false);
		if (result instanceof SatResult){
			SimpleModel slicedModel = ModelSlicer.slice(((SatResult)result).getModel(),
					localSpec.dependencyMap.get(PROP_NAME));
			Counterexample cex = extractCounterexample(PROP_NAME, 1, slicedModel, true);
			message.setConsistencyMsgWithCex(renameSignals(cex));
		}else{
			message.setConsistencyMsgWithUc(findRightSide(new ArrayList<>(message.vm.ivc)));
		}
		
		solver.pop();
		
		sendValid();
	}
	
	public Symbol type(Type type) {
		return new Symbol(capitalize(Util.getName(type)));
	}
	
	private String capitalize(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
 
	private void defineEF() {
		z3Solver.declFun(EF, NamedType.BOOL, NamedType.BOOL);
		z3Solver.assertSexp(getEfFirstObl());
		z3Solver.assertSexp(getEfSecObl());
		z3Solver.assertSexp(getNegOfProp());
	}
	
	private Counterexample extractCounterexample(String property, int k, SimpleModel model,
			boolean concrete) {
		if (settings.inline) {
			ModelReconstructionEvaluator.reconstruct(localSpec, model, property, k, concrete);
		}
		return CounterexampleExtractor.extract(localSpec, k, model);
	}

	private Sexp getNegOfProp() { 
		Symbol var = new Symbol("a"); 
		return new Cons ("not", 
				new Cons("forall", new Cons(new Cons(var.str, type(NamedType.BOOL))), 
						new Cons("=>", INIT, new Cons(EF, var))));
	}
	
	private Sexp getEfFirstObl() {
		Symbol var = new Symbol("a"); 
		return new Cons("forall",  new Cons(new Cons(var.str, type(NamedType.BOOL))), 
				new Cons("=>", var, new Cons(EF, var)));
	}

	private Sexp getEfSecObl() {
		List<Sexp> vars = getVar(0);
		List<Sexp> varsNext = getVar(1);
		List<Sexp> noInit = new ArrayList<>(varsNext);
		noInit.remove(0);
		return new Cons("forall",  new Cons(vars), 
				new Cons("=>",
						new Cons("exists",  new Cons(noInit), 
								new Cons("and", 
										callTR()
										, new Cons(EF, getTimeStep(PROP_NAME, 1))))
						, new Cons(EF, getTimeStep(PROP_NAME, 0))));
	}

	private Symbol getTimeStep(String v, int i) { 
			return new Symbol(new StreamIndex(v, i).getEncoded().str); 
	}

	private List<Sexp> getVar(int i) {
		List <Sexp> args = new ArrayList<>();
		for(VarDecl v : localSpec.getTransitionRelation().getInputs()){
			args.add(new Cons(v.id, type(v.type)));
		} 
		return args;
	}

	private Sexp callTR() { 
		List <Symbol> args = new ArrayList<>();
		for(VarDecl v : localSpec.getTransitionRelation().getInputs()){
			args.add(new Symbol(v.id));
		} 
		return new Cons(localSpec.getTransitionRelation().getName(), args);
	}

	private Node defineNewProperty() {
		Expr prop = new BoolExpr(true); 
		for(Equation eq : spec.node.equations){
			if(isAssertion(eq)){
				prop = new BinaryExpr(prop, BinaryOp.AND, eq.lhs.get(0));
			}
		}   
		List<VarDecl> locals = new ArrayList<>(spec.node.locals); 
		List<Equation> equations = new ArrayList<>(spec.node.equations);
		IvcUtil.defineNewEquation(prop, locals, equations, PROP_NAME);
		NodeBuilder builder = new NodeBuilder(spec.node); 
		builder.clearLocals().addLocals(locals); 
		builder.clearEquations().addEquations(equations);  
		return builder.build();
	}

	private boolean isAssertion(Equation eq) {
		for(Expr asr : spec.node.assertions){
			if (asr.toString().equals(eq.lhs.get(0).id))
				return true;
		}
		return false;
	}
	
	private Set<String> findRightSide(Collection<String> propertyIvc) {
		Set<String> ret = new HashSet<>(); 
		for (String core : propertyIvc){
			String rn = findRightSide(core);
			if (rn != ""){
				ret.add(rn);
			}
		}
		return ret;
	}
	 	
	private Counterexample renameSignals(Counterexample cex) {
		Counterexample ret = new Counterexample(cex.getLength());
		for (Signal<Value> signal : cex.getSignals()) { 
			ret.addSignal(signal.rename(findRightSide(signal.getName())));  
		}   
		return ret;
	}
	 
	
	private String findRightSide(String name) { 
		if(name.contains(JKind.EQUATION_NAME)){
			for(Equation eq : spec.node.equations){
				if(name.equals(eq.lhs.get(0).id)){
					return ("assert "+ eq.expr.toString());
				}
			}
		}else if(name.contains(PROP_NAME)){
			return "";
		}
		return name;

	}

	private void sendValid() {
		Set<String> ivc = new HashSet<>();
		for(String core : message.vm.ivc){
			ivc.add(findRightSide(core));
		}
		message.vm = new ValidMessage (message.vm.source, message.vm.valid, message.vm.k, message.vm.proofTime,
		        message.vm.invariants, ivc, null, null);
		director.writeConsistencyCheckerResults(message); 
		if(! settings.allIvcs){ 
			Itinerary itinerary = message.vm.getNextItinerary();
			director.broadcast(new ValidMessage(message.vm.source, message.vm.valid, message.vm.k, message.vm.proofTime,
					        message.vm.invariants, ivc, itinerary, null));
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
	}

	@Override
	protected void handleMessage(InvariantMessage im) { 
	}

	@Override
	protected void handleMessage(UnknownMessage um) { 
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		if (vm.getNextDestination() == EngineType.CONSISTENCY_CHECKER) {
			check(vm);
		}
	}
	
	public void handleMessage(ConsistencyMessage cm) {
		this.message  = cm;
		check(cm.vm, cm.vm.valid.get(0));
	}
	
	 

}
