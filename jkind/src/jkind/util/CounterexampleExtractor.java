package jkind.util;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.EnumType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.FunctionTable;
import jkind.results.FunctionTableRow;
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
				Value value = convert(spec.typeMap.get(si.getStream()), model.getValue(var));
				signal.putValue(si.getIndex(), value);
			}
		}

		for (FunctionTable table : model.getFunctionTables()) {
			List<VarDecl> inputs = table.getInputs();
			VarDecl output = table.getOutput();
			FunctionTable newTable = new FunctionTable(table.getName(), inputs, output);
			
			for (FunctionTableRow row : table.getRows()) {
				List<Value> newInputValues = new ArrayList<>();
				for (int i = 0; i < inputs.size(); i++) {
					newInputValues.add(convert(inputs.get(i).type, row.getInputs().get(i)));
				}
				Value newOutputValue = convert(output.type, row.getOutput());
				
				newTable.addRow(newInputValues, newOutputValue);
			}
			cex.addFunctionTable(newTable);
		}

		return cex;
	}

	private boolean isInternal(String stream) {
		return stream.startsWith("%");
	}

	private Value convert(Type type, Value value) {
		if (type instanceof EnumType && value != null) {
			EnumType et = (EnumType) type;
			IntegerValue iv = (IntegerValue) value;
			return new EnumValue(et.values.get(iv.value.intValue()));
		}
		return value;
	}
}
