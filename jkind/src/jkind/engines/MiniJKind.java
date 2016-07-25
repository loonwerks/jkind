package jkind.engines;
import jkind.ExitCodes; 
import jkind.JKindSettings; 
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.ConsistencyMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage; 
import static java.util.stream.Collectors.toList; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;  
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.results.Counterexample;
import jkind.translation.Specification; 
import jkind.util.Util; 

public class MiniJKind extends Engine { 
	public static final String NAME = "mini-jkind";
    private  Director director;  
    
    public static final String EQUATION_NAME = "__addedEQforAsrInconsis_by_MiniJKind__";  
	public static final String UNKNOWN = "UNKNOWN";
	public static final String INVALID = "INVALID";
	public static final String VALID = "VALID";
	public static final String NOT_YET_CHECKED = "NOT_YET_CHECKED";
	 
	private ValidMessage validMessage;
	private Counterexample invalidModel; 
	private double runtime;
	private String status = NOT_YET_CHECKED; 
    
	public MiniJKind(Specification spec, JKindSettings settings) {
		super(NAME, spec, settings, null); 
		
		if (spec.node.properties.size() != 1) {
			throw new IllegalArgumentException("MiniJKind Expects exactly one property");
		}
		
		if (settings.allAssigned){
			Node newNode = normalizeAssertions(spec.node);
			newNode = setIvcArgs(newNode, getAllAssigned(newNode));
			this.director =  new Director(settings, new Specification(newNode, settings.noSlicing), 
										new Specification(newNode, settings.noSlicing), this);
		}else{
			this.director =  new Director(settings, spec, spec, this);
		}
		
		settings.miniJkind = true;
	}
	
	public void verify() {
		try { 
			director.run();   
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
	
	public void setValidMessage(ValidMessage vm) {
		validMessage = new ValidMessage(vm.source, vm.valid, vm.k, vm.invariants, vm.ivc, null, null);
		status = VALID;
	}
	
	public ValidMessage getValidMessage() {
		return validMessage;
	}

	public void setInvalid(Counterexample cex) {
		status = INVALID;
		this.invalidModel = cex;
	}
	
	public Counterexample getInvalidModel() {
		return invalidModel;
	}
	
	public double getRuntime(){
		return runtime;
	}
	
	public void setRuntime(double rt){
		runtime = rt;
	}

	public void setUnknown() {
		status = UNKNOWN;
	}
	
	public String getPropertyStatus() {
		return status;
	}
	
	public List<String> getPropertyIvc() {
		return new ArrayList<String> (validMessage.ivc);
	}
	
	public List<String> getPropertyInvariants() {
		return validMessage.invariants.stream().map(Object::toString).collect(toList());
	}
	
	public int getK() {
		return validMessage.k;
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
	
	private Node normalizeAssertions(Node node) { 
		List<VarDecl> locals = new ArrayList<>(node.locals); 
		List<Equation> equations = new ArrayList<>(node.equations);
		List<Expr> assertions = new ArrayList<>(node.assertions);
		 
		
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

		NodeBuilder builder = new NodeBuilder(node); 
		builder.clearLocals().addLocals(locals); 
		builder.clearEquations().addEquations(equations);
		builder.clearAssertions().addAssertions(assertions);  
		return builder.build();
	}
	
	private IdExpr defineNewEquation(Expr rightSide, List<VarDecl> locals, List<Equation> equations, String newVar) {
		locals.add(new VarDecl(newVar, NamedType.BOOL));
		IdExpr ret = new IdExpr(newVar);
		equations.add(new Equation(ret, rightSide));
		 
		return ret;
	}
	
	 
	@Override
	protected void main() { 
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
	}

	@Override
	protected void handleMessage(ConsistencyMessage cm) { 
	}
}
