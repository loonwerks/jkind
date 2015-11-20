package jkind.engines.messages;

import java.util.List;

import jkind.util.Util;

public class BaseStepMessage extends Message {
	public final int step;
	public final List<String> properties;

	public BaseStepMessage(int step, List<String> properties) {
		this.step = step;
		this.properties = Util.safeList(properties);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
