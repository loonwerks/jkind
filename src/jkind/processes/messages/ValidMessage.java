package jkind.processes.messages;

import java.util.List;

public class ValidMessage extends Message {
	final public int k;
	final public List<String> valid;

	public ValidMessage(int k, List<String> valid) {
		this.valid = valid;
		this.k = k;
	}
}
