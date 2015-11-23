package jkind.engines.messages;

import java.util.List;

import jkind.solvers.Model;
import jkind.util.Util;

public class InductiveCounterexampleMessage extends Message {
	public final List<String> properties;
	public final int length;
	public final Model model;

	public InductiveCounterexampleMessage(List<String> properties, int length, Model model) {
		this.properties = Util.safeList(properties);
		this.length = length;
		this.model = model;
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
