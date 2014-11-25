package jkind.engines.messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.engines.EngineType;

public abstract class Message {
	protected final EngineType source;
	
	protected Message(EngineType source) {
		this.source = source;
	}
	
	public EngineType getSource() {
		return source;
	}
	
	protected <T> List<T> safeCopy(List<T> list) {
		return Collections.unmodifiableList(new ArrayList<>(list));
	}
	
	public abstract void accept(MessageHandler handler);
}
