package jkind.results.layout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.util.Util;

/**
 * A layout for the inputs, ouputs, and locals of a Lustre node
 */
public class NodeLayout implements Layout {
	private final Map<String, String> map;
	
	private static final String INPUTS = "Inputs";
	private static final String OUTPUTS = "Outputs";
	private static final String LOCALS = "Locals";
	private static final String INLINED = "Inlined";
	private static final String[] CATEGORIES = { INPUTS, OUTPUTS, LOCALS, INLINED };

	public NodeLayout(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("Unable to create layout for null node");
		}
		
		this.map = new HashMap<>();
		for (String input : Util.getIds(node.inputs)) {
			map.put(input, INPUTS);
		}
		for (String input : Util.getIds(node.outputs)) {
			map.put(input, OUTPUTS);
		}
		for (String local : Util.getIds(node.locals)) {
			if (local.contains("~")) {
				map.put(local, INLINED);
			} else {
				map.put(local, LOCALS);
			}
		}
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
		return map.get(signal);
	}
}
