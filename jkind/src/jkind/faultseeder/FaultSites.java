package jkind.faultseeder;

/*
 * Fault types to consider: 
 * 		Arithmetic operator faults
 *  	Relational operator faults
 *  	Boolean operator faults
 *  	Negation faults
 *  	Delay faults
 *  	Constant faults (+1 / -1)
 *  	Node call parameter faults (misrouting parameters)
 *  	Variable replacement faults
 *  
 *   
 */

public class FaultSites {
	public int arithmeticOp = 0;
	public int incrementOp = 0;
	public int relationalOp = 0;
	public int equalityOp = 0;
	public int booleanOp = 0;
	public int negationOp = 0; 
	public int delayOp = 0;
	public int constantOp = 0;
	public int nodeCallParameter = 0;
	public int variableReplacement = 0;
	
	public FaultSites() {}

	public FaultSites(FaultSites other) {
		this.arithmeticOp = other.arithmeticOp;
		this.incrementOp = other.incrementOp;
		this.relationalOp = other.relationalOp;
		this.equalityOp = other.equalityOp;
		this.booleanOp = other.booleanOp;
		this.negationOp = other.negationOp;
		this.delayOp = other.delayOp;
		this.constantOp = other.constantOp;
		this.nodeCallParameter = other.nodeCallParameter;
		this.variableReplacement = other.variableReplacement;
	}

	public int totalFaults() {
		return arithmeticOp + incrementOp + relationalOp + equalityOp + booleanOp + negationOp + delayOp + constantOp + nodeCallParameter + variableReplacement; 
	}

}