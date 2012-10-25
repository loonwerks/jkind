package jkind.processes.messages;

import java.util.List;

public class InvalidMessage extends Message {
	public List<String> invalid;

	public InvalidMessage(List<String> invalid) {
		this.invalid = invalid;
	}
}
