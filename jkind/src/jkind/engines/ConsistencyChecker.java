package jkind.engines;

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
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node; 
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder; 
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
				Node newNode = unassignProp(normalNode, property);  
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
		for(String ivc : jkind.getPropertyIvc()){ 
			if(notBool(ivc, normalNode)){
				continue;
			}
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
		if(! detect){
			Output.println("------------------------------------------");
			Output.println("     No inconsistency was detected."); 
			Output.println("------------------------------------------");
		}
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
		builder.clearInputs().addInputs(normalNode.inputs);
		builder.clearLocals().addLocals(normalNode.locals);
		builder.clearOutputs().addOutputs(normalNode.outputs);
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
		int counter = 0;
		List<IdExpr> newAssertions = new ArrayList<>();
		
		while (iter.hasNext()) {
			Expr asr = iter.next();
			if (! (asr instanceof IdExpr)) {
				newAssertions.add(defineNewEquation(asr, locals, equations, counter));
				counter ++;
				iter.remove(); 
			}
		}
		assertions.addAll(newAssertions);

		NodeBuilder builder = new NodeBuilder(spec.node);
		builder.clearInputs().addInputs(spec.node.inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(spec.node.outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
		builder.clearProperties().addProperty(property);
		return builder.build();
	}
	
	private IdExpr defineNewEquation(Expr asr, List<VarDecl> locals, List<Equation> equations, int id) {
		String newVar = EQUATION_NAME + id;
		locals.add(new VarDecl(newVar, NamedType.BOOL));
		IdExpr ret = new IdExpr(newVar);
		equations.add(new Equation(ret, asr));
		 
		return ret;
	}

	private Node unassignProp(Node node, String property) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		inputs.add(new VarDecl(property, Util.getTypeMap(node).get(property))); 
		List<VarDecl> locals = removeVariable(node.locals, property);
		List<VarDecl> outputs = removeVariable(node.outputs, property);
		List<Equation> equations = new ArrayList<>(node.equations);
		List<Expr> assertions = new ArrayList<>(node.assertions);
		Iterator<Equation> iter = equations.iterator();
		while (iter.hasNext()) {
			Equation eq = iter.next();
			if (property.equals(eq.lhs.get(0).id)) {
				iter.remove(); 
			}
		}
		Iterator<Expr> iter0 = assertions.iterator();
		while (iter0.hasNext()) {
			Expr asr = iter0.next();
			if (property.equals(asr.toString())) {
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
		director.broadcast(new ValidMessage(vm.source, valid, 0, null, null , itinerary, null)); 
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
