package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class Program extends Ast {
	final public List<TypeDef> types;
	final public List<Constant> constants;
	final public List<Function> functions;
	final public List<Node> nodes;
	final public String main;

	public Program(Location location, List<TypeDef> types, List<Constant> constants,
			List<Function> functions, List<Node> nodes, String main) {
		super(location);
		this.types = Util.safeList(types);
		this.constants = Util.safeList(constants);
		this.functions =Util.safeList(functions);
		this.nodes = Util.safeList(nodes);
		if (main == null && nodes != null && nodes.size() > 0) {
			this.main = nodes.get(nodes.size() - 1).id;
		} else {
			this.main = main;
		}
	}

	public Program(List<TypeDef> types, List<Constant> constants, List<Function> functions,
			List<Node> nodes, String main) {
		this(Location.NULL, types, constants, functions, nodes, main);
	}

	public Node getMainNode() {
		for (Node node : nodes) {
			if (node.id.equals(main)) {
				return node;
			}
		}
		return null;
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}