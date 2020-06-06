package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.util.Util;

public class UnknownMessage extends Message {
	public final String source;
	public final List<String> unknown;

	public UnknownMessage(String source, List<String> unknown) {
		this.source = source;
		this.unknown = Util.safeList(unknown);
	}

	public UnknownMessage(String source, String unknown) {
		this.source = source;
		this.unknown = Collections.singletonList(unknown);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
