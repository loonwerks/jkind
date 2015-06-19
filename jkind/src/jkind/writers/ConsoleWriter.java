package jkind.writers;

import java.util.List;
import java.util.Map;

import jkind.lustre.Expr;
import jkind.results.Counterexample;
import jkind.results.layout.Layout;

public class ConsoleWriter extends Writer {
	private final Layout layout;

	public ConsoleWriter(Layout layout) {
		super();
		this.layout = layout;
	}

	@Override
	public void begin() {
	}

	@Override
	public void end() {
	}

	private void writeLine() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void writeValid(List<String> props, String source, int k, double runtime,
			List<Expr> invariants) {
		writeLine();
		System.out.println("VALID PROPERTIES: " + props + " || " + source + " || K = " + k
				+ " || Time = " + runtime);
		if (!invariants.isEmpty()) {
			System.out.println("INVARIANTS:");
			for (Expr invariant : invariants) {
				System.out.println("  " + invariant);
			}
		}
		writeLine();
		System.out.println();
	}

	@Override
	public void writeInvalid(String prop, String source, Counterexample cex,
			List<String> conflicts, double runtime) {
		writeLine();
		System.out.println("INVALID PROPERTY: " + prop + " || " + source + " || K = "
				+ cex.getLength() + " || Time = " + runtime);
		System.out.println(cex.toString(layout));
		writeLine();
		System.out.println();
	}

	@Override
	public void writeUnknown(List<String> props, int trueFor,
			Map<String, Counterexample> inductiveCounterexamples, double runtime) {
		writeLine();
		System.out.println("UNKNOWN PROPERTIES: " + props + " || True for " + trueFor + " steps"
				+ " || Time = " + runtime);
		writeLine();
		System.out.println();
		for (String prop : props) {
			Counterexample cex = inductiveCounterexamples.get(prop);
			if (cex != null) {
				writeLine();
				System.out.println("INDUCTIVE COUNTEREXAMPLE: " + prop + " || K = "
						+ cex.getLength());
				System.out.println(cex.toString(layout));
				writeLine();
				System.out.println();
			}
		}
	}

	@Override
	public void writeBaseStep(List<String> props, int k) {
	}
}
