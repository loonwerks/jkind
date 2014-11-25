package jkind.engines.messages;

import java.util.List;

import jkind.engines.EngineType;

public class UnknownMessage extends Message {
	final public List<String> unknown;

	public UnknownMessage(EngineType source, List<String> unknown) {
		super(source);
		this.unknown = safeCopy(unknown);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
