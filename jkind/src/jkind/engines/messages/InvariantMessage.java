package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.invariant.Invariant;

public class InvariantMessage extends Message {
	public final List<Invariant> invariants;

	public InvariantMessage(List<Invariant> invariants) {
		this.invariants = safeCopy(invariants);
	}

	public InvariantMessage(Invariant invariant) {
		this(Collections.singletonList(invariant));
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
