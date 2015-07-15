package jkind.util;

import jkind.lustre.EnumType;
import jkind.lustre.Type;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.solvers.Model;
import jkind.translation.Specification;

public class CounterexampleExtractor {
	public static Counterexample extract(Specification spec, int k, Model model) {
		return new CounterexampleExtractor(spec).extractCounterexample(k, model);
	}
	
	private final Specification spec;

	private CounterexampleExtractor(Specification spec) {
		this.spec = spec;
	}

	private Counterexample extractCounterexample(int k, Model model) {
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
		Type type = spec.typeMap.get(base);
		if (type instanceof EnumType && value != null) {
			EnumType et = (EnumType) type;
			IntegerValue iv = (IntegerValue) value;
			return new EnumValue(et.values.get(iv.value.intValue()));
		}
		return value;
	}
}
