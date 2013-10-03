package jkind.writers;

import java.util.List;
import java.util.Map;

import jkind.invariant.Invariant;
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
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void writeValid(List<String> props, int k, double runtime, List<Invariant> invariants) {
		writeLine();
		System.out.println("VALID PROPERTIES: " + props + " || K = " + k + " || Time = " + runtime);
		if (!invariants.isEmpty()) {
			System.out.println("INVARIANTS:");
			for (Invariant invariant : invariants) {
				System.out.println("  " + invariant);
			}
		}
		writeLine();
		System.out.println();
	}

	@Override
	public void writeInvalid(String prop, Counterexample cex, double runtime) {
		writeLine();
		System.out.println("INVALID PROPERTY: " + prop + " || K = " + cex.getLength()
				+ " || Time = " + runtime);
		System.out.println(cex.toString(layout));
		writeLine();
		System.out.println();
	}

	@Override
	public void writeUnknown(List<String> props,
			Map<String, Counterexample> inductiveCounterexamples) {
		for (String prop : props) {
			Counterexample cex = inductiveCounterexamples.get(prop);
			if (cex != null) {
				writeLine();
				System.out.println("INDUCTIVE COUNTEREXAMPLE: " + prop + " || K = "
						+ cex.getLength());
				System.out.println(cex);
				writeLine();
				System.out.println();
			}
		}
	}
}
