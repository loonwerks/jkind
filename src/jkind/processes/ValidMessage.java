package jkind.processes;

import java.util.List;

public class ValidMessage extends Message {
	public int k;
	public List<String> valid;

	public ValidMessage(int k, List<String> valid) {
		this.valid = valid;
		this.k = k;
	}
}
