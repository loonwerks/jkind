package jkind.engines.messages;

public class StopMessage extends Message {
	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
