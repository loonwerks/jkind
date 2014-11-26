package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.invariant.Invariant;

public class ValidMessage extends Message {
	public final String source;
	public final List<String> valid;
	public final int k;
	public final List<Invariant> invariants;
	private final Itinerary itinerary;

	public ValidMessage(String source, List<String> valid, int k, List<Invariant> invariants,
			Itinerary itinerary) {
		this.source = source;
		this.valid = safeCopy(valid);
		this.k = k;
		this.invariants = safeCopy(invariants);
		this.itinerary = itinerary;
	}

	public ValidMessage(String source, String valid, int k, List<Invariant> invariants,
			Itinerary itinerary) {
		this(source, Collections.singletonList(valid), k, invariants, itinerary);
	}

	public EngineType getNextDestination() {
		return itinerary.getNextDestination();
	}

	public Itinerary getNextItinerary() {
		return itinerary.getNextItinerary();
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
