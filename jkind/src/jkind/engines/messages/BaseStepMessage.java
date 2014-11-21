package jkind.engines.messages;

import jkind.engine.EngineType;

public class BaseStepMessage extends Message {
	public int step;

	public BaseStepMessage(EngineType source, int step) {
		super(source);
		this.step = step;
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
