package jkind.excel;

import java.util.Arrays;
import java.util.List;

import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.util.Util;

/**
 * A layout for the inputs, ouputs, and locals of a Lustre node
 */
public class NodeLayout implements Layout {
	private final List<String> nodeInputs;
	private final List<String> nodeOutputs;
	private final List<String> nodeLocals;
	
	private static final String INPUTS = "Inputs";
	private static final String OUTPUTS = "Outputs";
	private static final String LOCALS = "Locals";
	private static final String[] CATEGORIES = { INPUTS, OUTPUTS, LOCALS };

	public NodeLayout(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("Unable to create layout for null node");
		}
		
		this.nodeInputs = Util.getIds(node.inputs);
		this.nodeOutputs = Util.getIds(node.outputs);
		this.nodeLocals = Util.getIds(node.locals);
	}
	
	public NodeLayout(Program program) {
		this(program.getMainNode());
	}

	@Override
	public List<String> getCategories() {
		return Arrays.asList(CATEGORIES);
	}

	@Override
	public String getCategory(String signal) {
		if (nodeInputs.contains(signal)) {
			return INPUTS;
		} else if (nodeOutputs.contains(signal)) {
			return OUTPUTS;
		} else if (nodeLocals.contains(signal)) {
			return LOCALS;
		} else {
			return null;
		}
	}

}
