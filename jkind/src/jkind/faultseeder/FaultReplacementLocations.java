package jkind.faultseeder;

import java.util.HashSet;

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

public class FaultReplacementLocations {
	public HashSet<Integer> arithmeticOp = new HashSet<>();
	public HashSet<Integer> incrementOp = new HashSet<>();
	public HashSet<Integer> relationalOp = new HashSet<>();
	public HashSet<Integer> equalityOp = new HashSet<>();
	public HashSet<Integer> booleanOp = new HashSet<>();
	public HashSet<Integer> negationOp = new HashSet<>();
	public HashSet<Integer> delayOp = new HashSet<>();
	public HashSet<Integer> constantOp = new HashSet<>();
	public HashSet<Integer> nodeCallParameter = new HashSet<>();
	public HashSet<Integer> variableReplacement = new HashSet<>();
	
	@Override
	public String toString() {
		return "arithmetic faults: " + arithmeticOp + 
				"\nincrement faults: " + incrementOp + 
				"\nrelational faults: " + relationalOp + 
				"\nequality faults: " + equalityOp + 
				"\nboolean faults: " + booleanOp + 
				"\nnegation faults: " + negationOp + 
				"\ndelay faults:" + delayOp + 
				"\nconstant faults: " + constantOp + 
				"\nnodeCallParameter faults: " + nodeCallParameter + 
				"\nvariableReplacement faults: " + variableReplacement;
	}
} 
