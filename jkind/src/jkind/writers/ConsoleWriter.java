package jkind.writers;

import java.util.List;
import java.util.Map;

import jkind.invariant.Invariant;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;

public class ConsoleWriter extends Writer {
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
		writeCounterexample(cex);
	}

	@Override
	public void writeUnknown(List<String> props,
			Map<String, Counterexample> inductiveCounterexamples) {
		for (String prop : props) {
			Counterexample icm = inductiveCounterexamples.get(prop);
			if (icm != null) {
				writeLine();
				System.out.println("INDUCTIVE COUNTEREXAMPLE: " + prop + " || K = "
						+ icm.getLength());
				writeCounterexample(icm);
			}
		}
	}

	private void writeCounterexample(Counterexample cex) {
		int length = cex.getLength();

		System.out.format("%25s %6s ", "", "Step");
		System.out.println();
		System.out.format("%-25s ", "variable");
		for (int i = 0; i < length; i++) {
			System.out.format("%6s ", i);
		}
		System.out.println();
		System.out.println();

		for (Signal<Value> signal : cex.getSignals()) {
			System.out.format("%-25s ", signal.getName());
			for (int i = 0; i < length; i++) {
				Value value = signal.getValue(i);
				System.out.format("%6s ", value != null ? value : "-");
			}
			System.out.println();
		}

		writeLine();
		System.out.println();
	}
}
