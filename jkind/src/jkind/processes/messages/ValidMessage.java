package jkind.processes.messages;

import java.util.List;

import jkind.invariant.Invariant;

public class ValidMessage extends Message {
	final public List<String> valid;
	final public int k;
	final public List<Invariant> invariants;

	public ValidMessage(List<String> valid, int k, List<Invariant> invariants) {
		this.valid = safeCopy(valid);
		this.k = k;
		this.invariants = safeCopy(invariants);
	}
}
