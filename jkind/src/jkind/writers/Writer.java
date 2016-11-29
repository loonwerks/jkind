package jkind.writers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.results.Counterexample;

public abstract class Writer {
	public abstract void begin();

	public abstract void end();

	public abstract void writeValid(List<String> props, String source, int k, double runtime,
			List<Expr> invariants, Set<String> ivc);

	public abstract void writeInvalid(String prop, String source, Counterexample cex,
			List<String> conflicts, double runtime);

	public abstract void writeUnknown(List<String> props, int trueFor,
			Map<String, Counterexample> inductiveCounterexamples, double runtime);

	public abstract void writeBaseStep(List<String> props, int k);

	// Used only by JRealiability
	public abstract void writeInconsistent(String prop, String source, int k, double runtime);
}
