package jkind.engines;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindSettings;
import jkind.engines.messages.MessageHandler;
import jkind.engines.messages.StopMessage;
import jkind.translation.Specification;

public abstract class Engine extends MessageHandler implements Runnable {
	protected final String name;
	protected final Specification spec;
	protected final JKindSettings settings;
	protected final Director director;

	protected final List<String> properties;

	// The director process will read this from another thread,
	// so we make it volatile
	protected volatile Throwable throwable;

	public Engine(String name, Specification spec, JKindSettings settings, Director director) {
		this.name = name;
		this.spec = spec;
		this.settings = settings;
		this.director = director;
		this.properties = new ArrayList<>(spec.node.properties);
	}

	protected abstract void main();

	@Override
	public void run() {
		try {
			main();
		} catch (StopException se) {
		} catch (Throwable t) {
			throwable = t;
		} finally {
			stopReceivingMessages();
		}
	}

	public String getName() {
		return name;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void stopEngine() {
		receiveMessage(new StopMessage());
	}

	protected String getScratchBase() {
		if (settings.scratch) {
			return settings.filename + "." + name;
		} else {
			return null;
		}
	}
}
