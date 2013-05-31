package jkind.writers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jkind.invariant.Invariant;
import jkind.lustre.Type;
import jkind.processes.messages.InductiveCounterexampleMessage;
import jkind.solvers.BoolValue;
import jkind.solvers.Model;
import jkind.solvers.Value;

public class XmlWriter extends Writer {
	private PrintWriter out;
	private Map<String, Type> types;

	public XmlWriter(String filename, Map<String, Type> types) throws FileNotFoundException {
		this.types = types;
		this.out = new PrintWriter(new FileOutputStream(filename), true);
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
	public void writeValid(List<String> props, int k, long elapsed, List<Invariant> invariants) {
		for (String prop : props) {
			writeValid(prop, k, elapsed, invariants);
		}
	}

	public void writeValid(String prop, int k, long elapsed, Collection<Invariant> invariants) {
		out.println("  <Property name=\"" + prop + "\">");
		out.println("    <Runtime unit=\"sec\" timeout=\"false\">" + elapsed / 1000.0
				+ "</Runtime>");
		out.println("    <Answer>valid</Answer>");
		out.println("    <K>" + k + "</K>");
		for (Invariant invariant : invariants) {
			out.println("    <Invariant>" + invariant + "</Invariant>");
		}
		out.println("  </Property>");
	}

	@Override
	public void writeInvalid(String prop, int k, Model model, long elapsed) {
		out.println("  <Property name=\"" + prop + "\">");
		out.println("    <Runtime unit=\"sec\" timeout=\"false\">" + elapsed / 1000.0
				+ "</Runtime>");
		out.println("    <Answer>falsifiable</Answer>");
		out.println("    <K>" + k + "</K>");
		writeCounterexample(k, BigInteger.ZERO, model);
		out.println("  </Property>");
	}

	private void writeCounterexample(int k, BigInteger offset, Model model) {
		out.println("    <Counterexample>");
		for (String fn : getRelevantFunctions(model.getFunctions())) {
			writeSignal(fn, k, offset, model);
		}
		out.println("    </Counterexample>");
	}

	private void writeSignal(String fn, int k, BigInteger offset, Model model) {
		String name = fn.substring(1);
		Type type = types.get(name);
		out.println("      <Signal name=\"" + name + "\" type=\"" + type + "\">");
		for (int i = 0; i < k; i++) {
			BigInteger key = BigInteger.valueOf(i).add(offset);
			Value value = model.getFunctionValue(fn, key);
			if (value != null) {
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
		if (value instanceof BoolValue) {
			BoolValue boolValue = (BoolValue) value;
			return boolValue.getBool() ? "1" : "0";
		} else {
			return value.toString();
		}
	}

	@Override
	public void writeUnknown(List<String> props,
			Map<String, InductiveCounterexampleMessage> inductiveCounterexamples) {
		for (String prop : props) {
			writeUnknown(prop, inductiveCounterexamples.get(prop));
		}
	}

	private void writeUnknown(String prop, InductiveCounterexampleMessage icm) {
		out.println("  <Property name=\"" + prop + "\">");
		out.println("    <Answer>unknown</Answer>");
		if (icm != null) {
			out.println("    <K>" + icm.k + "</K>");
			writeCounterexample(icm.k, icm.n, icm.model);
		}
		out.println("  </Property>");
	}
}
