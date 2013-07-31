package jkind.processes.messages;

import java.util.List;

import jkind.invariant.Invariant;

public class ValidMessage extends Message {
	final public List<String> valid;
	final public int k;
	final public List<Invariant> invariants;
	final public boolean reduced;

	public ValidMessage(List<String> valid, int k, List<Invariant> invariants, boolean reduced) {
		this.valid = valid;
		this.k = k;
		this.invariants = invariants;
		this.reduced = reduced;
	}
	
	public ValidMessage(List<String> valid, int k, List<Invariant> invariants) {
		this(valid, k, invariants, false);
	}
}
