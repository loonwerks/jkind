package jkind.engines.ivcs;  
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List; 
import java.util.Set;

import org.omg.CORBA.VM_ABSTRACT;

import jkind.JKind;
import jkind.JKindException;
import jkind.JKindSettings; 
import jkind.SolverOption;
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
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr; 
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr; 
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node; 
import jkind.lustre.RealExpr;  
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;  
import jkind.lustre.visitors.TypeAwareAstMapVisitor;
import jkind.results.Counterexample;
import jkind.results.Signal; 
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.slicing.Dependency;
import jkind.slicing.DependencySet;
import jkind.slicing.DependencyVisitor; 
import jkind.solvers.Result;
import jkind.solvers.SatResult; 
import jkind.solvers.z3.Z3Solver; 
import jkind.translation.Lustre2Sexp; 
import jkind.translation.Specification;
import jkind.util.Util; 

 
public class ConsistencyChecker  extends SolverBasedEngine {
	public static final String NAME = "consistency-checker";
	private Specification localSpec; 
	private Z3Solver z3Solver;
	private ConsistencyMessage message = null;
	private static final int BMC_STEPS = 3; 
	
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
		
		solver.define(localSpec.getIvcTransitionRelation());
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
		solver.push();
		
		solver.pop();
		
		sendValid();
	}
 
	private Node defineNewProperty() {
		Expr prop = new BoolExpr(true); 
		for(Equation eq : spec.node.equations){
			if(isAssertion(eq)){
				prop = new BinaryExpr(prop, BinaryOp.AND, eq.lhs.get(0));
			}
		}   
		String newVar = "__newPrpAddedByConsistencyChecker_by_JKind__";
		List<VarDecl> locals = new ArrayList<>(spec.node.locals); 
		List<Equation> equations = new ArrayList<>(spec.node.equations);
		IvcUtil.defineNewEquation(prop, locals, equations, newVar);
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
	
	private void assertAssertions(VarDecl i, List<Expr> assertions, List<Equation> equations) {
		Expr rs = new IdExpr("");
		for (Expr asr : assertions){
			rs = getEquation(asr.toString(), equations).expr; 
			if(containsVar(rs, i)){  
				solver.assertSexp(asr.accept(new Lustre2Sexp(1)));
			}
		}
	}

	 
  

	private void assertEquations(List<Equation> equations) { 
		for (Equation eq : equations) {
			z3Solver.assertSexp(getSexpEquation(eq));
		}
	}
	
	private Sexp getSexpEquation(Equation eq) {
		Lustre2Sexp visitor = new Lustre2Sexp(1); 
		Sexp body = eq.expr.accept(visitor);
		Sexp head = eq.lhs.get(0).accept(visitor); 
		return new Cons("=", head, body);
	}
	
	
	 
	
	private boolean containsVar(Expr rs, VarDecl input) {
		DependencySet ds = DependencyVisitor.get(rs);
		for(Dependency d : ds){
			if(d.name.equals(input.id)){
					return true;
			}
		}
		
		return false;
	}

	
	private Equation getEquation(String i, List<Equation> equations) {
		for(Equation eq : equations){
			if(eq.lhs.get(0).id.equals(i))
				return eq;
		}
		return null;
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
		}else if(name.contains("__newPrpAddedByConsistencyChecker_by_JKind__")){
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
