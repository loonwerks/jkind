package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.solvers.Model;
import jkind.util.Util;

public class InvalidMessage extends Message {
	public final String source;
	public final List<String> invalid;
	public final int length;
	public final Model model;
	private final Itinerary itinerary;

	public InvalidMessage(String source, List<String> invalid, int length, Model model, Itinerary itinerary) {
		this.source = source;
		this.invalid = Util.safeList(invalid);
		this.length = length;
		this.model = model;
		this.itinerary = itinerary;
	}

	public InvalidMessage(String source, String invalid, int length, Model model, Itinerary itinerary) {
		this(source, Collections.singletonList(invalid), length, model, itinerary);
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
