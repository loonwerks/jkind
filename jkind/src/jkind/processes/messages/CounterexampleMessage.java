package jkind.processes.messages;

import jkind.results.Counterexample;

public class CounterexampleMessage extends Message {
	final public String invalid;
	final public Counterexample cex;

	public CounterexampleMessage(String invalid, Counterexample cex) {
		this.invalid = invalid;
		this.cex = cex;
	}
}
