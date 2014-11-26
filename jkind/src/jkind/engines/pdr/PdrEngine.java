package jkind.engines.pdr;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jkind.JKindSettings;
import jkind.engines.Director;
import jkind.engines.Engine;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.invariant.Invariant;
import jkind.solvers.Model;
import jkind.translation.Specification;

public class PdrEngine extends Engine {
	private final ConcurrentMap<String, PdrSubengine> subengines = new ConcurrentHashMap<>();
	private int scratchCounter = 1;

	public PdrEngine(Specification spec, JKindSettings settings, Director director) {
		super("pdr", spec, settings, director);
	}

	@Override
	protected void main() {
		while (!done()) {
			processMessagesAndWaitUntil(() -> done() || canSpawnSubengine());
			if (canSpawnSubengine()) {
				spawnSubengine();
			}
		}
	}

	private boolean done() {
		return throwable != null || (properties.isEmpty() && subengines.isEmpty());
	}

	private boolean canSpawnSubengine() {
		return subengines.size() < settings.pdrMax && !properties.isEmpty();
	}

	private void spawnSubengine() {
		String prop = properties.remove(0);
		String scratch = settings.scratch ? getScratchBase() + scratchCounter++ : null;
		PdrSubengine subengine = new PdrSubengine(prop, spec, scratch, this);
		subengines.put(prop, subengine);
		subengine.start();
	}

	public void reportValid(String valid, List<Invariant> invariants) {
		Itinerary itinerary = director.getValidMessageItinerary();
		director.broadcast(new ValidMessage(getName(), valid, 1, invariants, itinerary));
		subengines.remove(valid);
	}

	public void reportInvalid(String invalid, int length, Model model) {
		Itinerary itinerary = director.getInvalidMessageItinerary();
		director.broadcast(new InvalidMessage(getName(), invalid, length, model, itinerary));
		subengines.remove(invalid);
	}

	public void reportInvariant(Invariant invariant) {
		director.broadcast(new InvariantMessage(invariant));
	}

	public void reportInvariants(List<Invariant> invariants) {
		director.broadcast(new InvariantMessage(invariants));
	}

	public void reportThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		cancel(im.invalid);
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		cancel(um.unknown);
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		cancel(vm.valid);
	}

	private void cancel(List<String> cancel) {
		for (String prop : new HashSet<>(subengines.keySet())) {
			if (cancel.contains(prop)) {
				PdrSubengine subengine = subengines.remove(prop);
				if (subengine != null) {
					subengine.cancel();
				}
			}
		}
		properties.removeAll(cancel);
	}
}
