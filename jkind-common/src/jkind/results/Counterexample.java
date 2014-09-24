package jkind.results;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.excel.ExcelCounterexampleFormatter;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.layout.Layout;
import jkind.results.layout.SingletonLayout;
import jkind.util.Util;

/**
 * A JKind counterexample
 */
public final class Counterexample {
	private final int length;
	private final Map<String, Signal<Value>> signals = new HashMap<>();

	public Counterexample(int length) {
		this.length = length;
	}

	/**
	 * Length of the counterexample
	 */
	public int getLength() {
		return length;
	}

	public void addSignal(Signal<Value> signal) {
		signals.put(signal.getName(), signal);
	}

	/**
	 * All signals in the counterexample
	 */
	public List<Signal<Value>> getSignals() {
		List<Signal<Value>> result = new ArrayList<>(signals.values());
		Collections.sort(result, new SignalNaturalOrdering());
		return result;
	}
	
	/**
	 * All signals in the counterexample belonging to the given category in the given layout
	 */
	public List<Signal<Value>> getCategorySignals(Layout layout, String category) {
		List<Signal<Value>> result = new ArrayList<>();
		for (Signal<Value> signal : getSignals()) {
			if (category.equals(layout.getCategory(signal.getName()))) {
				result.add(signal);
			}
		}
		return result;
	}

	/**
	 * Get a specific signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Signal with the specified name, or <code>null</code> if it cannot
	 *         be found
	 */
	public Signal<Value> getSignal(String name) {
		return signals.get(name);
	}

	public Signal<Value> getOrCreateSignal(String name) {
		if (!signals.containsKey(name)) {
			signals.put(name, new Signal<>(name));
		}
		return signals.get(name);
	}

	/**
	 * Get a specific step of the counterexample
	 * 
	 * @param step
	 *            Step to retrieve
	 * @return Map from signal names to their values on the specified step
	 */
	public Map<String, Value> getStep(int step) {
		Map<String, Value> result = new HashMap<>();
		for (Signal<Value> signal : signals.values()) {
			Value value = signal.getValue(step);
			if (value != null) {
				result.put(signal.getName(), value);
			}
		}
		return result;
	}

	/**
	 * Get a specific integer signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Integer signal with the specified name, or <code>null</code> if
	 *         it cannot be found
	 */
	public Signal<IntegerValue> getIntegerSignal(String name) {
		return getTypedSignal(name, IntegerValue.class);
	}

	/**
	 * Get a specific boolean signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Boolean signal with the specified name, or <code>null</code> if
	 *         it cannot be found
	 */
	public Signal<BooleanValue> getBooleanSignal(String name) {
		return getTypedSignal(name, BooleanValue.class);
	}

	/**
	 * Get a specific enumerated value signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Enumerated value signal with the specified name, or
	 *         <code>null</code> if it cannot be found
	 */
	public Signal<EnumValue> getEnumSignal(String name) {
		return getTypedSignal(name, EnumValue.class);
	}

	/**
	 * Get a specific real signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Real signal with the specified name, or <code>null</code> if it
	 *         cannot be found
	 */
	public Signal<RealValue> getRealSignal(String name) {
		return getTypedSignal(name, RealValue.class);
	}

	private <T extends Value> Signal<T> getTypedSignal(String name, Class<T> klass) {
		Signal<Value> signal = getSignal(name);
		if (signal == null) {
			return null;
		}
		return signal.cast(klass);
	}

	/**
	 * Convert counterexample to an Excel spreadsheet
	 * 
	 * Using this requires the jxl.jar file in your classpath
	 * 
	 * @param file
	 *            File to write Excel spreadsheet to
	 * @param layout
	 *            Layout information for signals in counterexample
	 * @see Layout
	 * @throws jkind.JKindException
	 */
	public void toExcel(File file, Layout layout) {
		try (ExcelCounterexampleFormatter formatter = new ExcelCounterexampleFormatter(file, layout)) {
			formatter.writeCounterexample("Counterexample", this);
		}
	}

	/**
	 * Convert counterexample to an Excel spreadsheet using default layout
	 * 
	 * Using this requires the jxl.jar file in your classpath
	 * 
	 * @param file
	 *            File to write Excel spreadsheet to
	 * @throws jkind.JKindException
	 */
	public void toExcel(File file) {
		toExcel(file, new SingletonLayout("Signals"));
	}

	private static final String NEWLINE = System.getProperty("line.separator");

	@Override
	public String toString() {
		return toString(new SingletonLayout());
	}

	public String toString(Layout layout) {
		StringBuilder text = new StringBuilder();

		text.append(String.format("%25s %6s ", "", "Step"));
		text.append(NEWLINE);
		text.append(String.format("%-25s ", "variable"));
		for (int i = 0; i < length; i++) {
			text.append(String.format("%6s ", i));
		}
		text.append(NEWLINE);

		for (String category : layout.getCategories()) {
			List<Signal<Value>> signals = getCategorySignals(layout, category);
			appendSection(text, category, signals);
		}

		return text.toString();
	}

	private void appendSection(StringBuilder text, String category, List<Signal<Value>> signals) {
		if (!signals.isEmpty()) {
			text.append(NEWLINE);
			text.append(category.toUpperCase());
			text.append(NEWLINE);
			for (Signal<Value> signal : signals) {
				appendSignal(text, signal);
			}
		}
	}

	private void appendSignal(StringBuilder text, Signal<Value> signal) {
		text.append(String.format("%-25s ", signal.getName()));
		for (int i = 0; i < length; i++) {
			Value value = signal.getValue(i);
			text.append(String.format("%6s ", !Util.isArbitrary(value) ? value : "-"));
		}
		text.append(NEWLINE);
	}
}
