package jkind.processes.messages;

import java.util.List;

import jkind.sexp.Sexp;

public class InvariantMessage extends Message {
	public final List<Sexp> invariants;

	public InvariantMessage(List<Sexp> invariants) {
		this.invariants = invariants;
	}
}
