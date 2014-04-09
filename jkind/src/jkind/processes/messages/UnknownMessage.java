package jkind.processes.messages;

import java.util.List;

public class UnknownMessage extends Message {
	final public List<String> unknown;

	public UnknownMessage(List<String> unknown) {
		this.unknown = safeCopy(unknown);
	}
}
