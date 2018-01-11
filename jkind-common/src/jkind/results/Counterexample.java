package jkind.results;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import jkind.excel.ExcelCounterexampleFormatter;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.results.layout.Layout;
import jkind.results.layout.SingletonLayout;
import jkind.util.CounterexampleFormatter;
import jkind.util.Util;

/**
 * A JKind counterexample
 */
public final class Counterexample {
	private final int length;
	private final Map<String, Signal<Value>> signals = new HashMap<>();
	private final SortedSet<FunctionTable> functionTables = new TreeSet<>();

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

	public void addFunctionTable(FunctionTable functionTable) {
		this.functionTables.add(functionTable);
	}

	public List<FunctionTable> getFunctionTables() {
		return Util.safeList(functionTables);
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
	 * All signals in the counterexample belonging to the given category in the
	 * given layout
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
			formatter.writeCounterexample("Counterexample", this, Collections.emptyList());
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

	@Override
	public String toString() {
		return toString(new SingletonLayout());
	}

	public String toString(Layout layout) {
		return new CounterexampleFormatter(this, layout).toString();
	}
}
