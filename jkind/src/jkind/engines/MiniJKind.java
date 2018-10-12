package jkind.engines;
import jkind.ExitCodes; 
import jkind.JKindSettings;
import jkind.engines.ivcs.IvcUtil;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage; 
import static java.util.stream.Collectors.toList;  
import java.util.List;
import java.util.Set;

import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage; 
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.results.Counterexample;
import jkind.translation.Specification;  

public class MiniJKind extends Engine { 
	public static final String NAME = "mini-jkind";
    private  Director director;  
	public static final String UNKNOWN = "UNKNOWN";
	public static final String UNKNOWN_WITH_EXCEPTION = "UNKNOW_WITH_EXCEPTION";
	public static final String INVALID = "INVALID";
	public static final String VALID = "VALID";
	public static final String NOT_YET_CHECKED = "NOT_YET_CHECKED";
	 
	private ValidMessage validMessage;
	private Counterexample invalidModel; 
	private double runtime;
	private String status = NOT_YET_CHECKED; 

    
	public MiniJKind(Program program, Specification spec, JKindSettings settings) {

		super(NAME, spec, settings, null); 
		
		if (spec.node.properties.size() != 1) {
			throw new IllegalArgumentException("MiniJKind Expects exactly one property");
		} 
		
		// make sure the caller set these variables correctly
				settings.xml = false;
				settings.xmlToStdout = false;
				settings.allIvcs = false;
				settings.allIvcsAlgorithm = 1;
				settings.allIvcsMaxGrows = 1000;
				settings.allIvcsJkindTimeout = -1;
				settings.excel = false; 
				settings.miniJkind = true;

		if (settings.allAssigned && settings.reduceIvc){ 
			program = IvcUtil.setIvcArgs(spec.node, IvcUtil.getAllAssigned(spec.node));
			this.director =  new Director(settings, new Specification(program, settings.slicing), 
										new Specification(program, settings.slicing), this);
		}else if (!settings.allAssigned && settings.reduceIvc){ 
			this.director =  new Director(settings, new Specification(program, settings.slicing), 
					new Specification(program, settings.slicing), this); 
		}else{
			this.director =  new Director(settings, spec, spec, this);
		}
		
		settings.miniJkind = true;
	}
	
	public void verify() {
		try { 
			int ret = director.run();   
			
			if(ret == ExitCodes.IVC_EXCEPTION)
			{ 
				status = UNKNOWN_WITH_EXCEPTION;
			} 
		} 
		catch (Throwable t) {  
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
	
	public void setValidMessage(ValidMessage vm) {
		status = VALID;
		validMessage = new ValidMessage(vm.source, vm.valid, vm.k, vm.proofTime, vm.invariants, vm.ivc, null, null);
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
	
	public Set<String> getPropertyIvc() { 
		return validMessage.ivc;
	}
	
	public List<String> getPropertyInvariants() {
		return validMessage.invariants.stream().map(Object::toString).collect(toList());
	}
	
	public int getK() {
		return validMessage.k;
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
}
