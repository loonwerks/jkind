package jkind.lustre;

import java.util.Collections;
import java.util.List;

public class Program extends AST {
	final public List<TypeDef> types;
	final public List<Constant> constants;
	final public List<Node> nodes;
	final public Node main;

	public Program(Location location, List<TypeDef> types, List<Constant> constants,
			List<Node> nodes) {
		super(location);
		this.types = Collections.unmodifiableList(types);
		this.constants = Collections.unmodifiableList(constants);
		this.nodes = Collections.unmodifiableList(nodes);
		if (nodes.size() > 0) {
			main = nodes.get(nodes.size() - 1);
		} else {
			main = null;
		}
	}
}
