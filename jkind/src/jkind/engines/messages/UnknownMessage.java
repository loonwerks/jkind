package jkind.engines.messages;

import java.util.List;

public class UnknownMessage extends Message {
	public final List<String> unknown;

	public UnknownMessage(List<String> unknown) {
		this.unknown = safeCopy(unknown);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
