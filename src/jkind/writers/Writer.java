package jkind.writers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import jkind.invariant.Invariant;
import jkind.processes.messages.InductiveCounterexampleMessage;
import jkind.solvers.Model;

public abstract class Writer {
	public abstract void begin();

	public abstract void end();

	public abstract void writeValid(List<String> props, int k, long elapsed,
			List<Invariant> invariants);

	public abstract void writeInvalid(String prop, int k, Model model, long elapsed);

	public abstract void writeUnknown(List<String> props,
			Map<String, InductiveCounterexampleMessage> inductiveCounterexamples);

	protected static SortedSet<String> getRelevantFunctions(Set<String> functions) {
		SortedSet<String> relevant = new TreeSet<String>();
		for (String fn : functions) {
			if (!fn.startsWith("$%")) {
				relevant.add(fn);
			}
		}
		return relevant;
	}

}
