package jkind.engines.messages;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.util.Tuple;
import jkind.util.Util;

public class ValidMessage extends Message {
	public final String source;
	public final List<String> valid;
	public final Set<String> ivc;
	public final List<Tuple<Set<String>, List<String>>> allIvcs;
	public final int k;
	public final List<Expr> invariants;
	private final Itinerary itinerary;

	public ValidMessage(String source, List<String> valid, int k, List<Expr> invariants,
			Set<String> ivc, Itinerary itinerary, List<Tuple<Set<String>, List<String>>> allIvcs) {
		this.source = source;
		this.valid = Util.safeList(valid);
		this.k = k;
		this.invariants = Util.safeList(invariants);
		this.itinerary = itinerary;
		this.ivc = Util.safeSet(ivc);
		this.allIvcs = Util.safeList(allIvcs);
	}

	public ValidMessage(String source, String valid, int k, List<Expr> invariants,
			Set<String> ivc, Itinerary itinerary, List<Tuple<Set<String>, List<String>>> allIvcs) {
		this(source, Collections.singletonList(valid), k, invariants, ivc, itinerary, allIvcs);
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
