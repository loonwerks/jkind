package jkind.engines;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List; 
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
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.RealExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.results.Counterexample;
import jkind.translation.Specification;  
import jkind.util.Util;

public class ConsistencyChecker  extends Engine {
	public static final String NAME = "consistency-checker";
	private static final String EQUATION_NAME = "__addedEQforAsrInconsis_by_JKind__";  

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
				JKindSettings js = new JKindSettings(); 
				js.noSlicing = true; 
				js.miniJkind = true; 
				js.allAssigned = true;
				js.reduceIvc = true;
				Node normalNode = normalizeAssertions(property); 
				Node newNode = unassign(normalNode, property, property);  
				Specification newSpec = new Specification(newNode, js.noSlicing);  
				MiniJKind miniJkind = new MiniJKind (newSpec, js);
				miniJkind.verify();
				 
				if(miniJkind.getPropertyStatus() == MiniJKind.VALID){
					Output.println("---------------------------------");
					Output.println("  The model is NOT consistent.");
					Output.println("  Some inconsistent parts: ");
					for(String ivc : miniJkind.getPropertyIvc()){
						Output.println( "   - "+ findRightSide(ivc, normalNode));
					}
					Output.println("---------------------------------");
				}else{
					miniJkind = null;
					checkPropertyConsistency(normalNode, property, vm);
				}
				sendValid(property, vm);
			}
		}
	}
	
	private void checkPropertyConsistency(Node normalNode, String property, ValidMessage vm) {
		JKindSettings js = new JKindSettings(); 
		js.noSlicing = true; 
		js.allAssigned = true; 
		js.miniJkind = true; 
		js.reduceIvc = true; 
		
		Specification newSpec = new Specification(normalNode, js.noSlicing); 
	 
		MiniJKind jkind = new MiniJKind (newSpec, js);
		jkind.verify();  
		boolean detect = false;
		js.reduceIvc = false;
		js.allAssigned = false;
		for(String ivc : jkind.getPropertyIvc()){ 
			if(notBool(ivc, normalNode)){ 
				if (ifConstant(ivc, normalNode)){
					continue;
				}else{
					Node newNode = defineNewProperty(unassign(normalNode, ivc, property));
					newSpec = new Specification(newNode, js.noSlicing); 
					MiniJKind checker = new MiniJKind (newSpec, js);   
					checker.verify(); 
					if(checker.getPropertyStatus() == MiniJKind.INVALID){
						String testValue =  parseModel(normalNode, checker.getInvalidModel(), ivc);
						//if(testNumConsistency(normalNode, testValue, ivc, js)){
							Output.println("--------------------------------------------");
							//Output.println("    One inconsistency was found: ");
							//Output.println("      - " + findRightSide(ivc, normalNode));
							Output.println("    Model would be inconsistent if the following assignment could happen: ");
							Output.println("      - " + ivc + " = "+ testValue);
							Output.println("--------------------------------------------");
							detect = true;
						//}
					}
				}
				
			}else{
				Node newNode = negateIvc(ivc, normalNode); 
				newSpec = new Specification(newNode, js.noSlicing); 
				MiniJKind checker = new MiniJKind (newSpec, js);   
				checker.verify();
				if(checker.getPropertyStatus() == MiniJKind.VALID){
					Output.println("--------------------------------------------");
					Output.println("    One inconsistency was found: ");
					Output.println("      - " + findRightSide(ivc, normalNode));
					Output.println("--------------------------------------------");
					detect = true;
			}
		}

		}
		if(! detect){
			Output.println("------------------------------------------");
			Output.println("     No inconsistency was detected."); 
			Output.println("------------------------------------------");
		}
	}

	private boolean testNumConsistency(Node node, String testValue, String ivc, JKindSettings js) {
		List<Equation> equations = new ArrayList<>(node.equations);  
		List<VarDecl> locals = new ArrayList<>(node.locals); 
		String newVar = "__newPrpAddedByConsistencyChecker_testNumCons_by_JKind__";
		BinaryExpr newEq;  
		VarDecl ivcVar = null;
		for (VarDecl l : node.locals){
			if(ivc.equals(l.id)){
				ivcVar = l;
				break;
			}
		}
		if(ivcVar.type == NamedType.INT){
			newEq = new BinaryExpr(new IdExpr(ivc), 
										BinaryOp.EQUAL, new IntExpr(new BigInteger(testValue)));
		}else{ 
			newEq = new BinaryExpr(new IdExpr(ivc), 
										BinaryOp.EQUAL, new RealExpr(new BigDecimal(testValue)));
		}
		defineNewEquation(newEq, locals, equations, newVar);
		
		NodeBuilder builder = new NodeBuilder(node); 
		builder.clearLocals().addLocals(locals);
		builder.clearEquations().addEquations(equations); 
		System.out.println(defineNewProperty(builder.build()).toString());
		MiniJKind miniJkind = new MiniJKind (new Specification(defineNewProperty(builder.build()), js.noSlicing), js);   
		miniJkind.verify(); 
		if(miniJkind.getPropertyStatus() == MiniJKind.INVALID){ 
			return true;
		}
		else return false;
	}

	private String parseModel(Node node, String cex, String ivc) { 
		String ret = "";
		for(String line : cex.split("\n")){
			if (line.contains(ivc)){
				ret = line.substring(ivc.length() + 1);
				ret = ret.replaceAll("\\s","");
				return ret;
			}
		}
		return ret;
	}

	private boolean ifConstant(String ivc, Node normalNode) {
		for (Equation eq : normalNode.equations){
			if(ivc.equals(eq.lhs.get(0).id)){
				if(eq.expr instanceof IntExpr || eq.expr instanceof RealExpr)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	private Node defineNewProperty(Node node) {
		BinaryExpr prop = new BinaryExpr(new BoolExpr(true), BinaryOp.AND, new BoolExpr(true)); 
        
		for(Equation eq : node.equations){
			if(! notBool(eq.lhs.get(0).id, node)){
				prop = new BinaryExpr(prop, BinaryOp.AND, eq.lhs.get(0));
			}
		} 
		List<Equation> equations = new ArrayList<>(node.equations);  
		List<VarDecl> locals = new ArrayList<>(node.locals); 
		
		String newVar = "__newPrpAddedByConsistencyChecker_by_JKind__";
		defineNewEquation(prop, locals, equations, newVar);
		
		NodeBuilder builder = new NodeBuilder(node); 
		builder.clearLocals().addLocals(locals);
		builder.clearEquations().addEquations(equations);
		builder.clearProperties().addProperty(newVar);
		return builder.build(); 
	}

	private boolean notBool(String ivc, Node normalNode) {
		for(VarDecl var : normalNode.locals){
			if(ivc.equals(var.id)){
				if(var.type.equals(NamedType.BOOL)){
					return false;
				}
				else return true;
			}
		}
		for(VarDecl var : normalNode.outputs){
			if(ivc.equals(var.id)){
				if(var.type.equals(NamedType.BOOL)){
					return false;
				}
				else return true;
			}
		}
		return false;
	}

	private String findRightSide(String ivc, Node node) {
		for (Equation eq : node.equations){
			if(ivc.equals(eq.lhs.get(0).id)){
				if(ivc.contains(EQUATION_NAME)){
					return "assert (" + eq.expr.toString() +")";
				}else{
					return ivc + " = " + eq.expr.toString();
				}
			}
		}
		return null;
	}

	private Node negateIvc(String ivc, Node normalNode) {
		List<Equation> equations = new ArrayList<>(normalNode.equations); 
		Iterator<Equation> iter = equations.iterator();
		Equation negEq = null;
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (ivc.equals(eq.lhs.get(0).id)) {
				negEq = new Equation(eq.lhs.get(0), new UnaryExpr(UnaryOp.NOT, eq.expr)); 
				iter.remove(); 
				break;
			}
		} 
		equations.add(negEq);

		List<Expr> assertions = new ArrayList<>(normalNode.assertions); 
        if(! (normalNode.properties.contains(ivc))){
        	assertions.add(new IdExpr(normalNode.properties.get(0)));
        	Iterator<Expr> iter0 = assertions.iterator();
        	while (iter0.hasNext()) {
        		Expr asr = iter0.next();
        		if (ivc.equals(asr.toString())) {
        			iter0.remove();  
        			break;
        		}
        	} 
        }
		NodeBuilder builder = new NodeBuilder(normalNode); 
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		builder.clearProperties().addProperty(ivc);
		return builder.build();
	}
	
	private Node normalizeAssertions(String property) { 
		List<VarDecl> locals = new ArrayList<>(spec.node.locals); 
		List<Equation> equations = new ArrayList<>(spec.node.equations);
		List<Expr> assertions = new ArrayList<>(spec.node.assertions);
		 
		
		Iterator<Expr> iter = assertions.iterator();
		int id = 0;
		List<IdExpr> newAssertions = new ArrayList<>();
		
		while (iter.hasNext()) {
			Expr asr = iter.next();
			if (! (asr instanceof IdExpr)) {
				newAssertions.add(defineNewEquation(asr, locals, equations, EQUATION_NAME + id));
				id ++;
				iter.remove(); 
			}
		}
		assertions.addAll(newAssertions);

		NodeBuilder builder = new NodeBuilder(spec.node); 
		builder.clearLocals().addLocals(locals); 
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		builder.clearProperties().addProperty(property);
		return builder.build();
	}
	
	private IdExpr defineNewEquation(Expr rightSide, List<VarDecl> locals, List<Equation> equations, String newVar) {
		locals.add(new VarDecl(newVar, NamedType.BOOL));
		IdExpr ret = new IdExpr(newVar);
		equations.add(new Equation(ret, rightSide));
		 
		return ret;
	}

	private Node unassign(Node node, String eqId, String property) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		inputs.add(new VarDecl(eqId, Util.getTypeMap(node).get(eqId))); 
		List<VarDecl> locals = removeVariable(node.locals, eqId);
		List<VarDecl> outputs = removeVariable(node.outputs, eqId);
		List<Equation> equations = new ArrayList<>(node.equations);
		List<Expr> assertions = new ArrayList<>(node.assertions);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (eqId.equals(eq.lhs.get(0).id)) {
				iter.remove(); 
			}
		}
		Iterator<Expr> iter0 = assertions.iterator();
		while (iter0.hasNext()) {
			Expr asr = iter0.next();
			if (eqId.equals(asr.toString())) {
				iter0.remove(); 
			}
		}

		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		builder.clearProperties().addProperty(property);
		return builder.build();
	}

	private List<VarDecl> removeVariable(List<VarDecl> varDecls, String property) {
		List<VarDecl> result = new ArrayList<>(varDecls);
		Iterator<VarDecl> iter = result.iterator();
		while (iter.hasNext()) {
			if (property.equals(iter.next().id)) {
				iter.remove(); 
			}
		}
		return result;
	}
	

	private void sendValid(String valid, ValidMessage vm) {
		Itinerary itinerary = vm.getNextItinerary();
		director.broadcast(new ValidMessage(vm.source, valid, vm.k, vm.invariants, null , itinerary, null)); 
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
