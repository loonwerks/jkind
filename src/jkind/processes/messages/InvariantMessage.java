package jkind.processes.messages;

import java.util.List;

import jkind.invariant.Invariant;

public class InvariantMessage extends Message {
	public final List<Invariant> invariants;

	public InvariantMessage(List<Invariant> invariants) {
		this.invariants = invariants;
	}
}
