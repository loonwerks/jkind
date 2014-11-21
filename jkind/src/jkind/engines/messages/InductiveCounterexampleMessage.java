package jkind.engines.messages;

import jkind.engine.EngineType;
import jkind.solvers.Model;

public class InductiveCounterexampleMessage extends Message {
	final public String property;
	final public int length;
	final public Model model;

	public InductiveCounterexampleMessage(EngineType source, String property, int length, Model model) {
		super(source);
		this.property = property;
		this.length = length;
		this.model = model;
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
