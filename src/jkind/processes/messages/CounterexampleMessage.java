package jkind.processes.messages;

import java.util.List;

import jkind.solvers.Model;

public class CounterexampleMessage extends Message {
	final public List<String> invalid;
	final public int k;
	final public Model model;
	final public boolean smooth;

	public CounterexampleMessage(List<String> invalid, int k, Model model, boolean smooth) {
		this.invalid = invalid;
		this.k = k;
		this.model = model;
		this.smooth = smooth;
	}
	
	public CounterexampleMessage(List<String> invalid, int k, Model model) {
		this(invalid, k, model, false);
	}
}
