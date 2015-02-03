package jkind.results.layout;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.util.Util;

/**
 * A layout for the inputs, outputs, and locals of a Lustre node
 */
public class NodeLayout implements Layout {
	private static final String INPUTS = "Inputs";
	private static final String OUTPUTS = "Outputs";
	private static final String LOCALS = "Locals";
	private static final String INLINED = "Inlined";
	private static final String[] CATEGORIES = { INPUTS, OUTPUTS, LOCALS, INLINED };

	private final Set<String> inputs;
	private final Set<String> outputs;
	private final Set<String> locals;

	public NodeLayout(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("Unable to create layout for null node");
		}

		this.inputs = getPrefix(Util.getIds(node.inputs));
		this.outputs = getPrefix(Util.getIds(node.outputs));
		this.locals = getPrefix(Util.getIds(node.locals));
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
		String prefix = getPrefix(signal);
		if (prefix.contains("~")) {
			return INLINED;
		} else if (inputs.contains(prefix)) {
			return INPUTS;
		} else if (outputs.contains(prefix)) {
			return OUTPUTS;
		} else if (locals.contains(prefix)) {
			return LOCALS;
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
