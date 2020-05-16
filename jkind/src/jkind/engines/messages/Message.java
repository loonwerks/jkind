package jkind.engines.messages;

public abstract class Message {
	public abstract void accept(MessageHandler handler);
}
