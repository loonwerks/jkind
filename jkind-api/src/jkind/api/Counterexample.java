package jkind.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;

public final class Counterexample {
	private final List<Signal<Value>> signals = new ArrayList<>();

	public void addSignal(Signal<Value> signal) {
		signals.add(signal);
	}

	public List<Signal<Value>> getSignals() {
		return signals;
	}

	public Signal<Value> getSignal(String name) {
		for (Signal<Value> signal : signals) {
			if (signal.getName().equals(name)) {
				return signal;
			}
		}
		return null;
	}

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
	
	public Signal<IntegerValue> getIntegerSignal(String name) {
		return getTypedSignal(name, IntegerValue.class);
	}
	
	public Signal<BooleanValue> getBooleanSignal(String name) {
		return getTypedSignal(name, BooleanValue.class);
	}
	
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
}
