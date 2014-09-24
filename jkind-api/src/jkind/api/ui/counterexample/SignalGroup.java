package jkind.api.ui.counterexample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jkind.lustre.values.Value;
import jkind.results.Signal;

public class SignalGroup {
	private final SignalGroup parent;
	private final String name;
	private final List<Signal<Value>> signals = new ArrayList<>();

	public SignalGroup(SignalGroup parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Signal<Value>> getSignals() {
		return Collections.unmodifiableList(signals);
	}
	
	public void addSignal(Signal<Value> signal) {
		signals.add(signal);
	}

	public boolean isSingleton() {
		return signals.size() == 1 && signals.get(0).getName().equals("");
	}
	
	public SignalGroup getParent() {
		return parent;
	}
}
