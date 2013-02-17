package jkind.processes.messages;

import java.math.BigInteger;

import jkind.solvers.Model;

public class InductiveCounterexampleMessage extends Message {
	final public String property;
	final public BigInteger n;
	final public int k;
	final public Model model;

	public InductiveCounterexampleMessage(String property, BigInteger n, int k, Model model) {
		this.property = property;
		this.n = n;
		this.k = k;
		this.model = model;
	}
}
