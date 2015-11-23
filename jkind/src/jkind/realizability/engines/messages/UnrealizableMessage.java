package jkind.realizability.engines.messages;

import java.util.List;

import jkind.solvers.Model;
import jkind.util.Util;

public class UnrealizableMessage extends Message {
	public final int k;
	public final Model model;
	public final List<String> properties;

	public UnrealizableMessage(int k, Model model, List<String> conflicts) {
		this.k = k;
		this.model = model;
		this.properties = Util.safeList(conflicts);
	}
}
