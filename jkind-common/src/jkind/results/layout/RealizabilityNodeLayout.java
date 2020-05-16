package jkind.results.layout;

import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.util.Util;

/**
 * A layout for the actual inputs, outputs, and locals of a Lustre node used for
 * realizability checking
 */
public class RealizabilityNodeLayout implements Layout {
	private static final String REALIZABILITY_INPUTS = "Realizability Inputs";
	private static final String REALIZABILITY_OUTPUTS = "Realizability Outputs";
	private static final String NODE_OUTPUTS = "Node Outputs";
	private static final String NODE_LOCALS = "Node Locals";
	private static final String NODE_INLINED = "Node Inlined";
	private static final String[] CATEGORIES = { REALIZABILITY_INPUTS, REALIZABILITY_OUTPUTS, NODE_OUTPUTS, NODE_LOCALS,
			NODE_INLINED };

	private final Set<String> realizabilityInputs;
	private final Set<String> realizabilityOutputs;
	private final Set<String> nodeOutputs;
	private final Set<String> nodeLocals;

	public RealizabilityNodeLayout(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("Unable to create layout for null node");
		}

		if (node.realizabilityInputs == null) {
			throw new IllegalArgumentException(
					"Unable to create realizability-based layout for node without realizability query");
		}

		this.realizabilityInputs = getPrefix(node.realizabilityInputs);
		this.realizabilityOutputs = getPrefix(getRealizabilityOutputs(node));
		this.nodeOutputs = getPrefix(Util.getIds(node.outputs));
		this.nodeLocals = getPrefix(Util.getIds(node.locals));
	}

	private static List<String> getRealizabilityOutputs(Node node) {
		List<String> realizabilityOutputs = new ArrayList<>(Util.getIds(node.inputs));
		realizabilityOutputs.removeAll(node.realizabilityInputs);
		return realizabilityOutputs;
	}

	public RealizabilityNodeLayout(Program program) {
		this(program.getMainNode());
	}

	@Override
	public List<String> getCategories() {
		return Arrays.asList(CATEGORIES);
	}

	@Override
	public String getCategory(String signal) {
		String prefix = getPrefix(signal);
		if (prefix.contains("~")) {
			return NODE_INLINED;
		} else if (realizabilityInputs.contains(prefix)) {
			return REALIZABILITY_INPUTS;
		} else if (realizabilityOutputs.contains(prefix)) {
			return REALIZABILITY_OUTPUTS;
		} else if (nodeOutputs.contains(prefix)) {
			return NODE_OUTPUTS;
		} else if (nodeLocals.contains(prefix)) {
			return NODE_LOCALS;
		} else {
			return null;
		}
	}

	private String getPrefix(String signal) {
		return signal.split("\\.|\\[")[0];
	}

	private Set<String> getPrefix(List<String> signals) {
		return signals.stream().map(this::getPrefix).collect(toSet());
	}
}
