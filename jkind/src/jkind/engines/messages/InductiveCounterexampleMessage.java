package jkind.engines.messages;

import jkind.solvers.Model;

public class InductiveCounterexampleMessage extends Message {
	public final String property;
	public final int length;
	public final Model model;

	public InductiveCounterexampleMessage(String property, int length, Model model) {
		this.property = property;
		this.length = length;
		this.model = model;
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
