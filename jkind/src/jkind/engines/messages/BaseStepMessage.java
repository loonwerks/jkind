package jkind.engines.messages;

import java.util.List;


public class BaseStepMessage extends Message {
	public final int step;
	public final List<String> properties;

	public BaseStepMessage(int step, List<String> properties) {
		this.step = step;
		this.properties = properties;
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
