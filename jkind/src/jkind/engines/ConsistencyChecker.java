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
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder; 
import jkind.translation.Specification;  
import jkind.util.Util;

public class ConsistencyChecker  extends Engine {
	public static final String NAME = "consistency-checker";  

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
				js.scratch = true;
				Node newNode = unassignProp(property); 
				Specification newSpec = new Specification(newNode, js.noSlicing); 
				//System.out.println(newNode.toString());
				MiniJKind miniJkind = new MiniJKind (newSpec, js);
				miniJkind.verify();
				 
				if(miniJkind.getPropertyStatus() == MiniJKind.VALID){
					Output.println("---------------------------------");
					Output.println("  The model is NOT consistent.");
					Output.println("---------------------------------");
				}else{
					Output.println("---------------------------------------------------------------------------");
					Output.println("  No inconsistency was detected. The model could still be inconsistent:");
					Output.println("  Run JKind with -all_ivcs and look into the IVC sets for inconsistencies");
					Output.println("  If ivc-reduction process failed, add    -pdr_max 0   option");
					Output.println("---------------------------------------------------------------------------");
				}
				sendValid(property, vm);
			}
		}
	}
	
	private void sendValid(String valid, ValidMessage vm) {
		Itinerary itinerary = vm.getNextItinerary();
		director.broadcast(new ValidMessage(vm.source, valid, 0, null, null , itinerary, null)); 
	}
	
	private Node unassignProp(String property) {
		List<VarDecl> inputs = new ArrayList<>(spec.node.inputs);
		inputs.add(new VarDecl(property, Util.getTypeMap(spec.node).get(property))); 
		List<VarDecl> locals = removeVariable(spec.node.locals, property);
		List<VarDecl> outputs = removeVariable(spec.node.outputs, property);
		List<Equation> equations = new ArrayList<>(spec.node.equations);
		List<Expr> assertions = new ArrayList<>(spec.node.assertions);
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

		NodeBuilder builder = new NodeBuilder(spec.node);
		builder.clearInputs().addInputs(inputs);
		builder.clearLocals().addLocals(locals);
		builder.clearOutputs().addOutputs(outputs);
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions); 
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
