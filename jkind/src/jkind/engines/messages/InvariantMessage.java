package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.engines.EngineType;
import jkind.invariant.Invariant;

public class InvariantMessage extends Message {
	public final List<Invariant> invariants;

	public InvariantMessage(EngineType source, List<Invariant> invariants) {
		super(source);
		this.invariants = safeCopy(invariants);
	}
	
	public InvariantMessage(EngineType source, Invariant invariant) {
		this(source, Collections.singletonList(invariant));
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
