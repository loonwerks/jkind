package jkind.engines.messages;


public class BaseStepMessage extends Message {
	public final int step;

	public BaseStepMessage(int step) {
		this.step = step;
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
