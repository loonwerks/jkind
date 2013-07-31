package jkind.lustre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Program extends Ast {
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

	public Program(List<TypeDef> types, List<Constant> constants, List<Node> nodes) {
		this(Location.NULL, types, constants, nodes);
	}

	public Program(List<Node> nodes) {
		this(Location.NULL, Collections.<TypeDef>emptyList(), Collections.<Constant>emptyList(), nodes);
	}

	public Program(Node... nodes) {
		this(Location.NULL, Collections.<TypeDef>emptyList(), Collections.<Constant>emptyList(), Arrays
				.asList(nodes));
	}

	@Override
	public <T> T accept(AstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}