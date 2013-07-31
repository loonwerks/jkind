package jkind.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.values.Value;

public class Counterexample {
	private final List<Signal> signals = new ArrayList<>();

	public void addSignal(Signal signal) {
		signals.add(signal);
	}

	public List<Signal> getSignals() {
		return signals;
	}
	
	public Signal getSignal(String name) {
		for (Signal signal : signals) {
			if (signal.getName().equals(name)) {
				return signal;
			}
		}
		return null;
	}
	
	public Map<String, Value> getStep(int step) {
		Map<String, Value> result = new HashMap<>();
		for (Signal signal : signals) {
			Value value = signal.getValue(step);
			if (value != null) {
				result.put(signal.getName(), value);
			}
		}
		return result;
	}
}
