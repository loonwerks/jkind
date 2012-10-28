package jkind.writers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jkind.lustre.Type;
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
	public void writeValid(List<String> props, int k, long elapsed) {
		out.println("  <Property name=\"" + spaces(props) + "\">");
		out.println("    <Runtime unit=\"sec\" timeout=\"false\">" + elapsed / 1000.0
				+ "</Runtime>");
		out.println("    <K>" + k + "</K>");
		out.println("    <Answer>valid</Answer>");
		out.println("  </Property>");
	}

	private String spaces(List<String> strings) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = strings.iterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	@Override
	public void writeInvalid(List<String> props, int k, Model model, long elapsed) {
		out.println("  <Property name=\"" + spaces(props) + "\">");
		out.println("    <Runtime unit=\"sec\" timeout=\"false\">" + elapsed / 1000.0
				+ "</Runtime>");
		out.println("    <K>" + k + "</K>");
		out.println("    <Answer>falsifiable</Answer>");
		writeCounterexample(k, model);
		out.println("  </Property>");
	}

	private void writeCounterexample(int k, Model model) {
		out.println("    <Counterexample>");
		for (String fn : sort(model.getFunctions())) {
			writeSignal(fn, k, model);
		}
		out.println("    </Counterexample>");
	}

	private void writeSignal(String fn, int k, Model model) {
		String name = fn.substring(1);
		Type type = types.get(name);
		out.println("      <Signal name=\"" + name + "\" type=\"" + type + "\">");
		Map<Integer, Value> fnMap = model.getFunction(fn);
		for (int i = 0; i < k; i++) {
			if (fnMap.containsKey(i)) {
				out.println("        <Value time=\"" + i + "\">" + formatValue(fnMap.get(i))
						+ "</Value>");
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

	public void writeUnknown(List<String> props) {
		out.println("  <Property name=\"" + spaces(props) + "\">");
		out.println("    <Answer>unknown</Answer>");
		out.println("  </Property>");
	}
}
