package jkind.api;

import java.util.ArrayList;
import java.util.List;

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
}
