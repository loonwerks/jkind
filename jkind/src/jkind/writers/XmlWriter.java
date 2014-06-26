package jkind.writers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import jkind.interval.BoolInterval;
import jkind.interval.NumericInterval;
import jkind.invariant.Invariant;
import jkind.lustre.Type;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.util.Util;

public class XmlWriter extends Writer {
	private PrintWriter out;
	private Map<String, Type> types;

	public XmlWriter(String filename, Map<String, Type> types, boolean useStdout)
			throws FileNotFoundException {
		this.types = types;
		if (useStdout) {
			this.out = new PrintWriter(System.out, true);
		} else {
			this.out = new PrintWriter(new FileOutputStream(filename), true);
		}
	}

	@Override
	public void begin() {
		out.println("<?xml version=\"1.0\"?>");
		out.println("<Results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
	}

	@Override
	public void end() {
		out.println("</Results>");
	}

	@Override
	public void writeValid(List<String> props, int k, double runtime, List<Invariant> invariants) {
		for (String prop : props) {
			writeValid(prop, k, runtime, invariants);
		}
	}

	public void writeValid(String prop, int k, double runtime, List<Invariant> invariants) {
		out.println("  <Property name=\"" + prop + "\">");
		out.println("    <Runtime unit=\"sec\" timeout=\"false\">" + runtime + "</Runtime>");
		out.println("    <Answer>valid</Answer>");
		out.println("    <K>" + k + "</K>");
		for (Invariant invariant : invariants) {
			out.println("    <Invariant>" + escape(invariant) + "</Invariant>");
		}
		out.println("  </Property>");
	}

	private String escape(Invariant invariant) {
		return invariant.toString().replace("<", "&lt;").replace(">", "&gt;");
	}

	@Override
	public void writeInvalid(String prop, Counterexample cex, double runtime) {
		out.println("  <Property name=\"" + prop + "\">");
		out.println("    <Runtime unit=\"sec\" timeout=\"false\">" + runtime + "</Runtime>");
		out.println("    <Answer>falsifiable</Answer>");
		out.println("    <K>" + cex.getLength() + "</K>");
		writeCounterexample(cex);
		out.println("  </Property>");
	}

	private void writeCounterexample(Counterexample cex) {
		out.println("    <Counterexample>");
		for (Signal<Value> signal : cex.getSignals()) {
			writeSignal(cex.getLength(), signal);
		}
		out.println("    </Counterexample>");
	}

	private void writeSignal(int k, Signal<Value> signal) {
		String name = signal.getName();
		Type type = types.get(name);
		out.println("      <Signal name=\"" + name + "\" type=\"" + type + "\">");
		for (int i = 0; i < k; i++) {
			Value value = signal.getValue(i);
			if (!Util.isArbitrary(value)) {
				out.println("        <Value time=\"" + i + "\">" + formatValue(value) + "</Value>");
			}
		}
		out.println("      </Signal>");
	}

	/**
	 * pkind prints booleans as 0/1. We do the same for compatibility, but we
	 * should eventually switch to true/false
	 */
	private String formatValue(Value value) {
		if (value instanceof BooleanValue) {
			BooleanValue bv = (BooleanValue) value;
			return bv.value ? "1" : "0";
		}
		if (value instanceof NumericInterval) {
			NumericInterval ni = (NumericInterval) value;
			return "<Interval low=\"" + ni.getLow() + "\" high=\"" + ni.getHigh() + "\"/>";
		}
		if (value instanceof BoolInterval) {
			BoolInterval bi = (BoolInterval) value;
			return bi.isTrue() ? "1" : "0";
		} else {
			return value.toString();
		}
	}

	@Override
	public void writeUnknown(List<String> props,
			Map<String, Counterexample> inductiveCounterexamples) {
		for (String prop : props) {
			writeUnknown(prop, inductiveCounterexamples.get(prop));
		}
	}

	private void writeUnknown(String prop, Counterexample icm) {
		out.println("  <Property name=\"" + prop + "\">");
		out.println("    <Answer>unknown</Answer>");
		if (icm != null) {
			out.println("    <K>" + icm.getLength() + "</K>");
			writeCounterexample(icm);
		}
		out.println("  </Property>");
	}
}
