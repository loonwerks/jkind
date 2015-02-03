package jkind.realizability.writers;

import jkind.results.Counterexample;

public abstract class Writer {
	public abstract void begin();

	public abstract void end();

	public abstract void writeBaseStep(int k);

	public abstract void writeRealizable(int k, double runtime);

	public abstract void writeUnrealizable(Counterexample cex, double runtime);

	public abstract void writeUnknown(int trueFor, Counterexample cex, double runtime);
}
