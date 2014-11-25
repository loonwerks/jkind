package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.engines.EngineType;
import jkind.invariant.Invariant;

public class ValidMessage extends Message {
	public final String originalSource;
	public final List<String> valid;
	public final int k;
	public final List<Invariant> invariants;

	public ValidMessage(EngineType source, String originalSource, List<String> valid, int k,
			List<Invariant> invariants) {
		super(source);
		this.originalSource = originalSource;
		this.valid = safeCopy(valid);
		this.k = k;
		this.invariants = safeCopy(invariants);
	}

	public ValidMessage(EngineType source, String originalSource, String valid, int k,
			List<Invariant> invariants) {
		this(source, originalSource, Collections.singletonList(valid), k, invariants);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
