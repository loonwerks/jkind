package jkind.results;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import jkind.lustre.VarDecl;
import jkind.lustre.values.Value;
import jkind.util.StringNaturalOrdering;
import jkind.util.Util;

public class FunctionTable implements Comparable<FunctionTable> {
	private final String name;
	private final List<VarDecl> inputs;
	private final VarDecl output;

	/**
	 * FunctionTableRows are hashed only based on inputs, so this map allows
	 * lookup to be efficient.
	 */
	private final SortedMap<FunctionTableRow, FunctionTableRow> rows = new TreeMap<>();

	public FunctionTable(String name, List<VarDecl> inputs, VarDecl output) {
		this.name = name;
		this.inputs = inputs;
		this.output = output;
	}

	public String getName() {
		return name;
	}

	public List<VarDecl> getInputs() {
		return inputs;
	}

	public VarDecl getOutput() {
		return output;
	}

	public void addRow(List<Value> inputValues, Value outputValue) {
		FunctionTableRow row = new FunctionTableRow(typeCorrectInputs(inputValues),
				Util.promoteIfNeeded(outputValue, output.type));
		rows.put(row, row);
	}

	private List<Value> typeCorrectInputs(List<Value> inputValues) {
		List<Value> result = new ArrayList<>();
		for (int i = 0; i < inputValues.size(); i++) {
			result.add(Util.promoteIfNeeded(inputValues.get(i), inputs.get(i).type));
		}
		return result;
	}

	public Set<FunctionTableRow> getRows() {
		return rows.keySet();
	}

	public Value lookup(List<Value> inputValues) {
		if (inputs.size() != inputValues.size()) {
			throw new IllegalArgumentException();
		}

		FunctionTableRow fakeRow = new FunctionTableRow(typeCorrectInputs(inputValues), null);
		FunctionTableRow realRow = rows.get(fakeRow);
		if (realRow != null) {
			return realRow.getOutput();
		} else {
			return null;
		}
	}

	@Override
	public int compareTo(FunctionTable other) {
		return new StringNaturalOrdering().compare(name, other.name);
	}
}
