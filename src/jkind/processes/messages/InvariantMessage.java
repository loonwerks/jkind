package jkind.processes.messages;

import jkind.sexp.Sexp;

public class InvariantMessage extends Message {
	public final Sexp invariant;

	public InvariantMessage(Sexp invariant) {
		this.invariant = invariant;
	}
}
