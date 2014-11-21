package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.engine.EngineType;
import jkind.invariant.Invariant;

public class ValidMessage extends Message {
	public final List<String> valid;
	public final int k;
	public final List<Invariant> invariants;

	public ValidMessage(EngineType source, List<String> valid, int k, List<Invariant> invariants) {
		super(source);
		this.valid = safeCopy(valid);
		this.k = k;
		this.invariants = safeCopy(invariants);
	}

	public ValidMessage(EngineType source, String valid, int k, List<Invariant> invariants) {
		this(source, Collections.singletonList(valid), k, invariants);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
