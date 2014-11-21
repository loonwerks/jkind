package jkind.engines.messages;

import java.util.List;

import jkind.engine.EngineType;
import jkind.invariant.Invariant;

public class InvariantMessage extends Message {
	public final List<Invariant> invariants;

	public InvariantMessage(EngineType source, List<Invariant> invariants) {
		super(source);
		this.invariants = safeCopy(invariants);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
