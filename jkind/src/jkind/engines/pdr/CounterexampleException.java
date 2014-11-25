package jkind.engines.pdr;

import jkind.solvers.Model;

public class CounterexampleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int length;
	private final Model model;

	public CounterexampleException(int length, Model model) {
		this.model = model;
		this.length = length;
	}

	public Model getModel() {
		return model;
	}

	public int getLength() {
		return length;
	}
}
