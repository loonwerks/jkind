package jkind.writers;

import java.util.List;
import java.util.Map;

import jkind.invariant.Invariant;
import jkind.results.Counterexample;

public abstract class Writer {
	public abstract void begin();

	public abstract void end();

	public abstract void writeValid(List<String> props, String source, int k, double runtime,
			List<Invariant> invariants);

	public abstract void writeInvalid(String prop, String source, Counterexample cex, double runtime);

	public abstract void writeUnknown(List<String> props, int trueFor,
			Map<String, Counterexample> inductiveCounterexamples, double runtime);

	public abstract void writeBaseStep(List<String> props, int k);
}
