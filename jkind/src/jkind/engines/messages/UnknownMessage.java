package jkind.engines.messages;

import java.util.List;

import jkind.util.Util;

public class UnknownMessage extends Message {
	public final List<String> unknown;

	public UnknownMessage(List<String> unknown) {
		this.unknown = Util.safeCopy(unknown);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
