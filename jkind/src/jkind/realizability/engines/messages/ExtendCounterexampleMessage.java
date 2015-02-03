package jkind.realizability.engines.messages;

import jkind.solvers.Model;

public class ExtendCounterexampleMessage extends Message {
	final public int k;
	final public Model model;

	public ExtendCounterexampleMessage(int k, Model model) {
		this.k = k;
		this.model = model;
	}
}
