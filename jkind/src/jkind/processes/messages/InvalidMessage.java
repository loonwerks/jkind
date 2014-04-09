package jkind.processes.messages;

import java.util.Collections;
import java.util.List;

import jkind.solvers.Model;

public class InvalidMessage extends Message {
	final public List<String> invalid;
	final public int k;
	final public Model model;

	public InvalidMessage(List<String> invalid, int k, Model model) {
		this.invalid = safeCopy(invalid);
		this.k = k;
		this.model = model;
	}
	
	public InvalidMessage(String invalid, int k, Model model) {
		this(Collections.singletonList(invalid), k, model);
	}
}
