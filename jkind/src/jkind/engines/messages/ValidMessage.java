package jkind.engines.messages;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import jkind.engines.ivcs.AllIVCs;
import jkind.lustre.Expr;
import jkind.util.Util;

public class ValidMessage extends Message {
	public final String source;
	public final List<String> valid;
	public final Set<String> ivc;
	public final List<AllIVCs> allIvcs;
	public final int k;
	public final double proofTime;
	public final List<Expr> invariants;
	private final Itinerary itinerary;
	public final boolean mivcTimedOut;

	public ValidMessage(String source, List<String> valid, int k, double proofTime, List<Expr> invariants,
			Set<String> ivc, Itinerary itinerary, Set<AllIVCs> allIvcs, boolean mivcTimedOut) {
		this.source = source;
		this.valid = Util.safeList(valid);
		this.k = k;
		this.invariants = Util.safeList(invariants);
		this.itinerary = itinerary;
		this.ivc = Util.safeSet(ivc);
		this.allIvcs = Util.safeList(allIvcs);
		this.proofTime = proofTime;
		this.mivcTimedOut = mivcTimedOut;
	}

	public ValidMessage(String source, String valid, int k, double proofTime, List<Expr> invariants, Set<String> ivc,
			Itinerary itinerary, Set<AllIVCs> allIvcs, boolean mivcTimedOut) {
		this(source, Collections.singletonList(valid), k, proofTime, invariants, ivc, itinerary, allIvcs, mivcTimedOut);
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
