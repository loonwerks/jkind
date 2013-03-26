package jkind.processes.messages;

import java.util.List;

import jkind.invariant.Invariant;

public class ValidMessage extends Message {
	final public List<String> valid;
	final public int k;
	final public List<Invariant> invariants;
	final public Status status;

	public ValidMessage(List<String> valid, int k, List<Invariant> invariants, Status status) {
		this.valid = valid;
		this.k = k;
		this.invariants = invariants;
		this.status = status;
	}
	
	public static enum Status {
		REDUCED, UNREDUCED;
	}
}
