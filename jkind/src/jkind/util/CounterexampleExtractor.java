package jkind.util;

import java.util.Map;

import jkind.lustre.EnumType;
import jkind.lustre.Type;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.solvers.Model;

public class CounterexampleExtractor {
	private final Map<String, Type> typeMap;

	public CounterexampleExtractor(Map<String, Type> typeMap) {
		this.typeMap = typeMap;
	}
	
	public Counterexample extractCounterexample(int k, Model model) {
		Counterexample cex = new Counterexample(k);
		for (String var : model.getVariableNames()) {
			StreamIndex si = StreamIndex.decode(var);
			if (si != null && si.getIndex() >= 0 && !isInternal(si.getStream())) {
				Signal<Value> signal = cex.getOrCreateSignal(si.getStream());
				Value value = convert(si.getStream(), model.getValue(var));
				signal.putValue(si.getIndex(), value);
			}
		}
		return cex;
	}

	private boolean isInternal(String stream) {
		return stream.startsWith("%");
	}

	private Value convert(String base, Value value) {
		Type type = typeMap.get(base);
		if (type instanceof EnumType) {
			EnumType et = (EnumType) type;
			IntegerValue iv = (IntegerValue) value;
			return new EnumValue(et.values.get(iv.value.intValue()));
		}
		return value;
	}
}
