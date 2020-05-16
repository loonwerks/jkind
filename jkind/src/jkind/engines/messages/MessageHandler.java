package jkind.engines.messages;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import jkind.JKindException;
import jkind.engines.StopException;

public abstract class MessageHandler {
	private BlockingQueue<Message> incoming = new LinkedBlockingQueue<>();

	public void receiveMessage(Message message) {
		if (incoming != null) {
			incoming.add(message);
		}
	}

	protected void stopReceivingMessages() {
		incoming = null;
	}

	protected void processMessages() {
		while (!incoming.isEmpty()) {
			handleMessage(incoming.poll());
		}
	}

	protected void handleMessage(Message message) {
		message.accept(this);
	}

	protected void processMessagesAndWaitUntil(Supplier<Boolean> stoppingCondition) {
		try {
			while (!incoming.isEmpty() || !stoppingCondition.get()) {
				Message message = incoming.poll(100, TimeUnit.MILLISECONDS);
				if (message != null) {
					handleMessage(message);
				}
			}
		} catch (InterruptedException e) {
			throw new JKindException("Interrupted while waiting for message", e);
		}
	}

	protected abstract void handleMessage(BaseStepMessage bsm);

	protected abstract void handleMessage(InductiveCounterexampleMessage icm);

	protected abstract void handleMessage(InvalidMessage im);

	protected abstract void handleMessage(InvariantMessage im);

	protected abstract void handleMessage(UnknownMessage um);

	protected abstract void handleMessage(ValidMessage vm);

	@SuppressWarnings("unused")
	protected void handleMessage(StopMessage sm) {
		throw new StopException();
	}
}
