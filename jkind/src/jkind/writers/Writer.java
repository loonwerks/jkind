package jkind.writers;

import java.util.List;
import java.util.Map;

import jkind.invariant.Invariant;
import jkind.results.Counterexample;

public abstract class Writer {
	public abstract void begin();

	public abstract void end();

	public abstract void writeValid(List<String> props, int k, double runtime,
			List<Invariant> invariants);

	public abstract void writeInvalid(String prop, Counterexample cex, double runtime);

	public abstract void writeUnknown(List<String> props,
			Map<String, Counterexample> inductiveCounterexamples);
}
