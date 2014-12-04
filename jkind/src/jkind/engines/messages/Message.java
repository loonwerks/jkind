package jkind.engines.messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Message {
	protected <T> List<T> safeCopy(List<T> list) {
		return Collections.unmodifiableList(new ArrayList<>(list));
	}
	
	public abstract void accept(MessageHandler handler);
}
