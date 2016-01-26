package jkind.engines.messages;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.util.Util;

public class ValidMessage extends Message {
	public final String source;
	public final List<String> valid;
	public final Set<String> support;
	public final int k;
	public final List<Expr> invariants;
	public final double supportRuntime;
	private final Itinerary itinerary;

	public ValidMessage(String source, List<String> valid, int k, List<Expr> invariants,
			Set<String> support, double supportRuntime, Itinerary itinerary) {
		this.source = source;
		this.valid = Util.safeList(valid);
		this.k = k;
		this.invariants = Util.safeList(invariants);
		this.itinerary = itinerary;
		this.support = Util.safeSet(support);
		this.supportRuntime = supportRuntime;
	}

	public ValidMessage(String source, String valid, int k, List<Expr> invariants,
			Set<String> support, double supportRuntime, Itinerary itinerary) {
		this(source, Collections.singletonList(valid), k, invariants, support, supportRuntime,
				itinerary);
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
