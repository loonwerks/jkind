package jkind.processes.messages;

import jkind.solvers.Model;

public class InductiveCounterexampleMessage extends Message {
	final public String property;
	final public int k;
	final public Model model;

	public InductiveCounterexampleMessage(String property, int k, Model model) {
		this.property = property;
		this.k = k;
		this.model = model;
	}
}
