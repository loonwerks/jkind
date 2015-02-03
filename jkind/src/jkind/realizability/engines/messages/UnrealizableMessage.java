package jkind.realizability.engines.messages;

import jkind.solvers.Model;

public class UnrealizableMessage extends Message {
	public final int k;
	public final Model model;

	public UnrealizableMessage(int k, Model model) {
		this.k = k;
		this.model = model;
	}
}
