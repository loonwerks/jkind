package jkind.engines.pdr;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import jkind.JKindSettings;
import jkind.engines.Director;
import jkind.engines.Engine;
import jkind.engines.StopException;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.translation.Specification;

public class PdrEngine extends Engine {
	public static final String NAME = "pdr";
	private final ConcurrentMap<String, PdrSubengine> subengines = new ConcurrentHashMap<>();
	private int scratchCounter = 1;

	public PdrEngine(Specification spec, JKindSettings settings, Director director) {
		super(NAME, spec, settings, director);
	}

	@Override
	protected void main() {
		try {
			while (!done()) {
				processMessagesAndWaitUntil(() -> done() || canSpawnSubengine());
				if (canSpawnSubengine()) {
					spawnSubengine();
				}
			}
		} catch (StopException se) {
			subengines.forEach((name, subengine) -> subengine.cancel());
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
		PdrSubengine subengine = new PdrSubengine(prop, spec, scratch, this, director);
		subengines.put(prop, subengine);
		subengine.start();
	}

	public void reportUnknown(String prop) {
		subengines.remove(prop);
		director.receiveMessage(new UnknownMessage(getName(), prop));
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

	@Override
	public void stopEngine() {
		for (PdrSubengine subengine : subengines.values()) {
			subengine.cancel();
		}
		properties.clear();
	}
}
