package jkind.engines.messages;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

import jkind.JKindException;

public abstract class MessageHandler {
	public final BlockingQueue<Message> incoming = new LinkedBlockingQueue<>();

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
				handleMessage(incoming.take());
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
}
