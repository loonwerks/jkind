package jkind.processes.messages;

import java.util.List;

import jkind.solvers.Model;

public class CounterexampleMessage extends Message {
	final public List<String> invalid;
	final public int length;
	final public Model model;

	public CounterexampleMessage(List<String> invalid, int length, Model model) {
		this.invalid = invalid;
		this.length = length;
		this.model = model;
	}
}
