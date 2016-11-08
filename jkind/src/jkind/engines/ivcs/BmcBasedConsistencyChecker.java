package jkind.engines.ivcs; 
import java.util.ArrayList; 
import java.util.HashSet; 
import java.util.List; 
import java.util.Set; 
import jkind.JKind; 
import jkind.JKindSettings; 
import jkind.StdErr;
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
import jkind.lustre.Equation;
import jkind.lustre.Expr; 
import jkind.lustre.LustreUtil;
import jkind.lustre.NamedType;
import jkind.lustre.Node; 
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.slicing.Dependency; 
import jkind.solvers.Result; 
import jkind.solvers.UnsatResult;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.LinkedBiMap;
import jkind.util.StreamIndex;
import jkind.util.Util;

public class BmcBasedConsistencyChecker  extends SolverBasedEngine {
	public static final String NAME = "bmc-consistency-checker";
	private Specification localSpec;   
	private Z3Solver z3Solver;
	private LinkedBiMap<String, Symbol> ivcMap;
	private boolean noDirector = false;
	
	public BmcBasedConsistencyChecker(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director); 
	}
	
	public BmcBasedConsistencyChecker(Specification spec, JKindSettings settings) {
		super(NAME, spec, settings, null); 
		noDirector = true;
		localSpec = spec;
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
				comment("Checking consistency for: " + property);
				solver.push();
				// create an over-approx of the model with IVCs 
				// we need this over-approx node because we might have several IVCs
				// do a BMC check to find a false transition
				Node main = overApproximateWithIvc(property, spec.node, vm.ivc, vm.invariants);
				main = setIvcArgs(main, getAllAssigned(main));   
				localSpec = new Specification(main, settings.slicing);  
				ivcMap = Lustre2Sexp.createIvcMap(localSpec.node.ivc); 
				
				for (Symbol e : ivcMap.values()) {
					solver.define(new VarDecl(e.str, NamedType.BOOL));
				}
				
				solver.define(localSpec.getIvcTransitionRelation());
				solver.define(new VarDecl(INIT.str, NamedType.BOOL));  
				checkConsistency(property, vm);
				solver.pop();
			}
		}
	}

	private Set<String> checkConsistency(String property, ValidMessage vm) {
		createVariables(-1);
		Set<String> ret = new HashSet<>();
		for (int k = 0; k < settings.n; k++) {
			comment("K = " + (k + 1));  
			createVariables(k);
			assertBaseTransition(k);
			Result result  = z3Solver.quickCheckSat(ivcMap.valueList());
			if (result instanceof UnsatResult){
				List<Symbol> unsatCore = ((UnsatResult) result).getUnsatCore();
				if(! noDirector){
					StdErr.println("---------------------------------------------------------------------------------");
					StdErr.println("  Model is inconsistent for property " + property + ", at K = " + k  + " with:"); 
				}
				for(Symbol s : unsatCore){
					String rs = findRightSide(ivcMap.getKey(s));
					if (! noDirector){StdErr.println("    - "+ rs);}
					ret.add(rs);
				}
				if (! noDirector){
					StdErr.println("---------------------------------------------------------------------------------");
					sendValid(property, vm);
				}
				return ret;
			} 
			assertProperty(property, k);
		}
		if(! noDirector){
			StdErr.println("---------------------------------------------------------------------------");
			StdErr.println("  No inconsistency was found for \n"+
						  "        property " + property + " to the depth of K = " +  settings.n);
			StdErr.println("----------------------------------------------------------------------------");
			sendValid(property, vm);
		}
		return null;
	}
	
	private void assertProperty(String prop, int k) {
		solver.assertSexp(new StreamIndex(prop, k).getEncoded());
	} 
	
	private String findRightSide(String ivc) {
		for (Equation eq : localSpec.node.equations){
			if(ivc.equals(eq.lhs.get(0).id)){
				if(ivc.contains(JKind.EQUATION_NAME)){
					return "assert (" + eq.expr.toString() +")";
				}else{
					return ivc ;
				}
			}
		}
		return null;
	}
	
	private Node overApproximateWithIvc(String prop, Node node, Set<String> ivc, List<Expr> invariants) { 
		List<Expr> assertions = removeAssertions(node.assertions, ivc);
		List<Equation> equations = removeEquations(node.equations, ivc); 
		List<VarDecl> locals = removeVariable(node.locals, equations);
		List<VarDecl> outputs = removeVariable(node.outputs, equations);
		NodeBuilder builder = new NodeBuilder(node);  
		builder.clearProperties().addProperty(prop);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		return builder.build();
	}

	private List<Expr> removeAssertions(List<Expr> assertions, Set<String> ivc) {
		List<Expr> ret = new ArrayList<>(); 
		for(Expr asr : assertions){
			if(ivc.contains(asr.toString())){
				ret.add(asr);
			}
		} 
		return ret;
	}

	private List<Equation> removeEquations(List<Equation> equations, Set<String> ivc) {
		List<Equation> ret = new ArrayList<>();
		Set<Dependency> map = new HashSet<>();
		Set<String> depName = new HashSet<>();
		for(String core : ivc){ 
			try{
				map.addAll(spec.dependencyMap.get(core).getSet());
			}catch(NullPointerException e){}
		} 
		for(Dependency d : map){
			depName.add(d.name);
		}
		for(Equation eq : equations){
			if(ivc.contains(eq.lhs.get(0).id)){
				ret.add(eq);
			}else if(depName.contains(eq.lhs.get(0).id)){
				ret.add(eq);
			}
		} 
		return ret; 
	}

	private List<VarDecl> removeVariable(List<VarDecl> vars, List<Equation> equations) {
		List<VarDecl> ret = new ArrayList<>();
		List<String> left = new ArrayList<>();
		for(Equation eq : equations){
			left.add(eq.lhs.get(0).id);
		}
		for(VarDecl var : vars){
			if(left.contains(var.id)){
				ret.add(var);
			}
		}
		return ret; 
	}
 
	private static List<String> getAllAssigned(Node node) {
		List<String> result = new ArrayList<>();
		result.addAll(Util.getIds(node.locals));
		result.addAll(Util.getIds(node.outputs));
		return result;
	}
	
	private static Node setIvcArgs(Node node, List<String> newSupport) {
		return new NodeBuilder(node).clearIvc().addIvcs(newSupport).build();
	}

	private void sendValid(String valid, ValidMessage vm) {
		Itinerary itinerary = vm.getNextItinerary();
		director.broadcast(new ValidMessage(vm.source, valid, vm.k, vm.proofTime, vm.invariants, vm.ivc , itinerary, null)); 
	}
	
	@Override
	protected void createVariables(int k) {
		for (VarDecl vd : getOffsetVarDecls(k)) {
			solver.define(vd);
		}

		for (VarDecl vd : Util.getVarDecls(localSpec.node)) {
			Expr constraint = LustreUtil.typeConstraint(vd.id, vd.type);
			if (constraint != null) {
				solver.assertSexp(constraint.accept(new Lustre2Sexp(k)));
			}
		}
	}

	@Override
	protected List<VarDecl> getOffsetVarDecls(int k) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl vd : Util.getVarDecls(localSpec.node)) {
			StreamIndex si = new StreamIndex(vd.id, k);
			result.add(new VarDecl(si.getEncoded().str, vd.type));
		}
		return result;
	}

	@Override
	protected Sexp getTransition(int k, Sexp init) {
		List<Sexp> args = new ArrayList<>();
		args.add(init);
		args.addAll(getSymbols(getOffsetVarDecls(k - 1)));
		args.addAll(getSymbols(getOffsetVarDecls(k)));
		return new Cons(localSpec.getTransitionRelation().getName(), args);
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
		if (vm.getNextDestination() == EngineType.BMC_BASED_CONSISTENCY_CHECKER) {  
			check(vm);
		}
		
	}

	public Set<String> acceptWithNoDirector(String property) {
		this.initializeSolver();
		comment("Checking consistency for: " + property);
		solver.push();      
		ivcMap = Lustre2Sexp.createIvcMap(localSpec.node.ivc); 
		
		for (Symbol e : ivcMap.values()) {
			solver.define(new VarDecl(e.str, NamedType.BOOL));
		}
		
		solver.define(localSpec.getIvcTransitionRelation());
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));  
		return checkConsistency(property, null);
		//solver.pop();
	}
}
