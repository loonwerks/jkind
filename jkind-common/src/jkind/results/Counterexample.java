package jkind.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;

/**
 * A JKind counterexample
 */
public final class Counterexample {
	private final int length;
	private final List<Signal<Value>> signals = new ArrayList<>();

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
		signals.add(signal);
	}

	/**
	 * All signals in the counterexample
	 */
	public List<Signal<Value>> getSignals() {
		return Collections.unmodifiableList(signals);
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
		for (Signal<Value> signal : signals) {
			if (signal.getName().equals(name)) {
				return signal;
			}
		}
		return null;
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
		for (Signal<Value> signal : signals) {
			Value value = signal.getValue(step);
			if (value != null) {
				result.put(signal.getName(), value);
			}
		}
		return Collections.unmodifiableMap(result);
	}

	/**
	 * Get a specific integer signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Integer signal with the specified name, or <code>null</code>
	 *         if it cannot be found
	 */
	public Signal<IntegerValue> getIntegerSignal(String name) {
		return getTypedSignal(name, IntegerValue.class);
	}

	/**
	 * Get a specific boolean signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Boolean signal with the specified name, or <code>null</code>
	 *         if it cannot be found
	 */
	public Signal<BooleanValue> getBooleanSignal(String name) {
		return getTypedSignal(name, BooleanValue.class);
	}

	/**
	 * Get a specific real signal from the counterexample
	 * 
	 * @param name
	 *            Name of the signal to retrieve
	 * @return Real signal with the specified name, or <code>null</code> if
	 *         it cannot be found
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

	@Override
	public String toString() {
		String newline = System.getProperty("line.separator");
		StringBuilder text = new StringBuilder();

		text.append(String.format("%25s %6s ", "", "Step"));
		text.append(newline);
		text.append(String.format("%-25s ", "variable"));
		for (int i = 0; i < length; i++) {
			text.append(String.format("%6s ", i));
		}
		text.append(newline);
		text.append(newline);

		for (Signal<Value> signal : signals) {
			text.append(String.format("%-25s ", signal.getName()));
			for (int i = 0; i < length; i++) {
				Value value = signal.getValue(i);
				text.append(String.format("%6s ", value != null ? value : "-"));
			}
			text.append(newline);
		}

		return text.toString();
	}
}
