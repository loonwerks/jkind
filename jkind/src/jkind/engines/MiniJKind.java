package jkind.engines;
import jkind.ExitCodes; 
import jkind.JKindSettings; 
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage; 
import java.util.ArrayList; 
import java.util.List;
import java.util.Set; 

import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.lustre.Node;
import jkind.lustre.builders.NodeBuilder;
import jkind.translation.Specification;
import jkind.util.Util; 

public class MiniJKind extends Engine { 
	public static final String NAME = "mini-jkind";
    private  Director director;  
	public static final String UNKNOWN = "UNKNOWN";
	public static final String INVALID = "INVALID";
	public static final String VALID = "VALID";
	public static final String NOT_YET_CHECKED = "NOT_YET_CHECKED";
	 
	private List<String> ivc = new ArrayList<>();
	private String invalidModel = "";
	private List<String> invariants = new ArrayList<>();
	private String status = NOT_YET_CHECKED; 
    
	public MiniJKind(Specification spec, JKindSettings settings) {
		super(NAME, spec, settings, null); 
		if (settings.allAssigned){
			Node newNode = spec.node;
			newNode = setIvcArgs(spec.node, getAllAssigned(spec.node));
			this.director =  new Director(settings, new Specification(newNode, settings.noSlicing), 
										new Specification(newNode, settings.noSlicing), this);
		}else{
			this.director =  new Director(settings, spec, spec, this);
		}
		if (spec.node.properties.size() != 1) {
			throw new IllegalArgumentException("MiniJKind Expects exactly one property");
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
	
	public void getValid(Set<String> core, Set<String> inv) {
		ivc.addAll(core); 
		invariants.addAll(inv);
		status = VALID;
	}

	public void getInvalid(String invalidModel) {
		status = INVALID;
		this.invalidModel = invalidModel;
	}
	
	public String getInvalidModel() {
		return invalidModel;
	}

	public void getUnknown() {
		status = UNKNOWN;
	}
	
	public String getPropertyStatus() {
		return status;
	}
	
	public List<String> getPropertyIvc() {
		return ivc;
	}
	
	public List<String> getPropertyInvariants() {
		return invariants;
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
	 
	@Override
	protected void main() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
}
