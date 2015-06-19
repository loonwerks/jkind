package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.lustre.Expr;
import jkind.util.Util;

public class ValidMessage extends Message {
	public final String source;
	public final List<String> valid;
	public final int k;
	public final List<Expr> invariants;
	private final Itinerary itinerary;

	public ValidMessage(String source, List<String> valid, int k, List<Expr> invariants,
			Itinerary itinerary) {
		this.source = source;
		this.valid = Util.safeCopy(valid);
		this.k = k;
		this.invariants = Util.safeCopy(invariants);
		this.itinerary = itinerary;
	}

	public ValidMessage(String source, String valid, int k, List<Expr> invariants,
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
