package jkind;

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
public class JFaultSeederSettings extends Settings {
	public int totalFaults = 10;
	public boolean faultPerFile = true;
	
	/*
	 * The types are: 
	 * 	Proportional: # of faults generated is proportional to the number of possible faults
	 *  Adjusted: reduce the number of variable replacement and delay faults, which can happen
	 *    at any expression.
	 *  Manual: generate mutants based on user input
	 */
	public enum Strategy {Proportional, Adjusted, Manual}; 
	public Strategy strategy = Strategy.Adjusted;
	
	// If true does not write information to stdout.
	public boolean quiet = false;
	
	public String directory = null;
	
	// emit statistics about the total number of mutants
	public boolean stats = true; 
	
	// slice model so that mutants are only found in relevant parts of model.
	public boolean slice = true;
	
	// only create linear mutants
	public boolean linearOnly = true;
}
