package jkind.realizability.engines.messages;

import jkind.solvers.Model;

public class ExtendCounterexampleMessage extends Message {
	public final int k;
	public final Model model;

	public ExtendCounterexampleMessage(int k, Model model) {
		this.k = k;
		this.model = model;
	}
}
