package jkind.engines.messages;

import java.util.List;

import jkind.util.Util;

public class Itinerary {
	private final List<EngineType> destinations;

	public Itinerary(List<EngineType> destinations) {
		this.destinations = Util.safeList(destinations);
	}

	public EngineType getNextDestination() {
		if (destinations.isEmpty()) {
			return null;
		} else {
			return destinations.get(0);
		}
	}

	public Itinerary getNextItinerary() {
		return new Itinerary(destinations.subList(1, destinations.size()));
	}
}
