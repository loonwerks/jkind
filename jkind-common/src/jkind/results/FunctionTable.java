package jkind.results;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jkind.lustre.VarDecl;
import jkind.lustre.values.Value;
import jkind.util.Util;

public class FunctionTable {
	private final List<VarDecl> inputs;
	private final List<VarDecl> outputs = new ArrayList<>();
	private final Map<List<Value>, List<Value>> map = new TreeMap<>(new ListValueComparator());

	public FunctionTable(List<VarDecl> inputs) {
		this.inputs = inputs;
	}

	public List<VarDecl> getInputs() {
		return inputs;
	}

	public List<VarDecl> getOutputs() {
		return outputs;
	}

	public List<String> getInputNames() {
		return Util.getIds(inputs);
	}

	public List<String> getOutputNames() {
		return Util.getIds(outputs);
	}

	public Map<List<Value>, List<Value>> getMap() {
		return map;
	}

	public void addOutput(VarDecl output) {
		outputs.add(output);
	}

	public void setOutput(List<Value> inputs, VarDecl output, Value outputValue) {
		List<Value> outputValues = map.get(inputs);
		if (outputValues == null) {
			Value[] empty = new Value[outputs.size()];
			outputValues = Arrays.asList(empty);
			map.put(inputs, outputValues);
		}
		outputValues.set(outputs.indexOf(output), outputValue);
	}

	public void setOutputs(List<Value> inputValues, List<Value> outputValues) {
		map.put(new ArrayList<>(inputValues), new ArrayList<>(outputValues));
	}
}
