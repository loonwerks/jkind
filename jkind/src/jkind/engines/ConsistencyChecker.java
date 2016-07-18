package jkind.engines; 
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jkind.JKind; 
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
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr; 
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr; 
import jkind.lustre.NamedType;
import jkind.lustre.Node; 
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder; 
import jkind.translation.Specification; 
import jkind.util.Util;

public class ConsistencyChecker  extends Engine {
	public static final String NAME = "consistency-checker";
	private Specification localSpec; 
	
	public ConsistencyChecker(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director); 
	}

	@Override
	public void main() {
		processMessagesAndWaitUntil(() -> properties.isEmpty());
	}

	private void check(ValidMessage vm) {
		for (String property : vm.valid) {
			if (properties.remove(property)) {
				// create an over-approx of the model with IVCs
				// define a new property which is the same as boolean expressions in the transition system
				// we need this over-approx node because we might have several IVCs
				// if proof goes through ==> no inconsistency
				// otherwise ==> it will find an example to show the inconsistency
				Node main = overApproximateWithIvc(property, spec.node, vm.ivc, vm.invariants);
				main = setIvcArgs(main, getAllAssigned(main)); 
				localSpec = new Specification(main, settings.noSlicing);  
				System.out.println(main.toString());
			}
		}
	}
	
	 
 
	private Node overApproximateWithIvc(String prop, Node node, Set<String> ivc, List<Expr> invariants) { 
		List<VarDecl> locals = removeVariable(node.locals, ivc);
		List<VarDecl> outputs = removeVariable(node.outputs, ivc);
		List<Expr> assertions = removeAssertions(node.assertions, ivc, node.inputs);
		List<Equation> equations = removeEquations(node.equations, ivc, assertions);
		//assertions.add(new IdExpr(prop));
		NodeBuilder builder = new NodeBuilder(node);  
		builder.clearProperties().addProperty(defineNewPropertyForT(equations, locals, outputs));
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		return builder.build();
	}

	private String defineNewPropertyForT(List<Equation> equations, List<VarDecl> locals, List<VarDecl> outputs) {
		BinaryExpr prop = new BinaryExpr(new BoolExpr(true), BinaryOp.AND, new BoolExpr(true)); 
		for(Equation eq : equations){
			if(boolExpr(eq.lhs.get(0).id, locals, outputs)){
				prop = new BinaryExpr(prop, BinaryOp.AND, eq.lhs.get(0));
			}
		}  
		
		String newVar = "__newPrpAddedByConsistencyChecker_by_JKind__";
		defineNewEquation(prop, locals, equations, newVar);
		return newVar;
	}
	
	private void defineNewEquation(Expr rightSide, List<VarDecl> locals, List<Equation> equations, String newVar) {
		locals.add(new VarDecl(newVar, NamedType.BOOL)); 
		equations.add(new Equation(new IdExpr(newVar), rightSide));
	}

	private boolean boolExpr(String ivc, List<VarDecl> locals, List<VarDecl> outputs) {
		for(VarDecl var : locals){
			if(ivc.equals(var.id)){
				if(var.type.equals(NamedType.BOOL)){
					return true;
				}
				else return false;
			}
		}
		for(VarDecl var : outputs){
			if(ivc.equals(var.id)){
				if(var.type.equals(NamedType.BOOL)){
					return true;
				}
				else return false;
			}
		}
		return false;
	}
	private List<Expr> removeAssertions(List<Expr> assertions, Set<String> ivc, List<VarDecl> inputs) {
		List<Expr> ret = new ArrayList<>();
		List<String> strIn = new ArrayList<>();
		for(VarDecl in : inputs){
			strIn.add(in.id);
		}
		for(Expr asr : assertions){
			if (ivc.contains(asr.toString())){
				ret.add(asr);
			}
			else if (strIn.contains(asr.toString())){
				ret.add(asr);
			}
		}
		return ret;
	}

	private List<Equation> removeEquations(List<Equation> equations, Set<String> ivc, List<Expr> assertions) {
		List<Equation> ret = new ArrayList<>(equations);
		Iterator<Equation> iter = ret.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (! ivc.contains(eq.lhs.get(0).id)) {
				if(! assertions.contains(eq.lhs.get(0))){
					iter.remove(); 
				}
			}
		}
		return ret; 
	}

	private List<VarDecl> removeVariable(List<VarDecl> vars, Set<String> ivc) {
		List<VarDecl> ret = new ArrayList<>(vars);
		/*Iterator<VarDecl> iter = ret.iterator();
		while (iter.hasNext()) {
			VarDecl var = iter.next();
			if (ivc.contains(var.id)) {
				iter.remove(); 
			}
		}*/
		return ret; 
	}

	private void checkConsistency(String property, ValidMessage vm) {
		if (spec.node.ivc.isEmpty()) {
			sendValid(property, vm);
			return;
		}

		JKindSettings js = new JKindSettings(); 
		js.noSlicing = true; 
		js.miniJkind = true; 
		
		MiniJKind miniJkind = new MiniJKind (localSpec, js);
        miniJkind.verify();   
		if (miniJkind.getPropertyStatus() == MiniJKind.VALID) {
			Output.println("-------------------------------");
			Output.println("  No inconsistency was found!");
			Output.println("-------------------------------");
		}
		else{  
			Output.println("----------------------------------------------------------------------");
			Output.println("  Model is inconsistent:"); 
			String model = miniJkind.getInvalidModel();
			for(String ivc : vm.ivc){
				Output.println("     "+ parseModel(model, ivc)); 
			}
			Output.println("----------------------------------------------------------------------");
		}
		sendValid(property, vm);
	}
	 
	private String parseModel(String cex, String ivc) { 
		String ret = "";
		for(String line : cex.split("\n")){
			if (line.contains(ivc)){ 
				String temp = line.substring(ivc.length() + 2);
				temp = temp.trim();
				if(ivc.contains(JKind.EQUATION_NAME)){
					ret = findRightSide(ivc) + ":\n                      ";
				}else{
					ret = ivc + ":\n                      ";
				}
				return ret + temp;
			}
		}
		return ret;
	}
	
	private String findRightSide(String ivc) {
		for (Equation eq : localSpec.node.equations){
			if(ivc.equals(eq.lhs.get(0).id)){
				if(ivc.contains(MiniJKind.EQUATION_NAME ) || ivc.contains(JKind.EQUATION_NAME)){
					return "assert (" + eq.expr.toString() +")";
				}else{
					return ivc ;
				}
			}
		}
		return null;
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
		director.broadcast(new ValidMessage(vm.source, valid, vm.k, vm.invariants, vm.ivc , itinerary, null)); 
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		if (vm.getNextDestination() == EngineType.CONSISTENCY_CHECKER) {
			check(vm);
		}
		
	}

}
