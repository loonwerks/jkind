package jkind.lustre;

import java.util.Arrays;
import java.util.List;

import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class Program extends Ast {
	public final List<TypeDef> types;
	public final List<Constant> constants;
	public final List<Node> nodes;
	public final List<RecursiveFunction> recFuns;
	public final String main;

	public Program(Location location, List<TypeDef> types, List<Constant> constants,
			List<Node> nodes, String main, List<RecursiveFunction> recFuns) {
		super(location);
		this.types = Util.safeList(types);
		this.constants = Util.safeList(constants);
		this.nodes = Util.safeList(nodes);
		this.recFuns = Util.safeList(recFuns);
		if (main == null && nodes != null && nodes.size() > 0) {
			this.main = nodes.get(nodes.size() - 1).id;
		} else {
			this.main = main;
		}
	}
	
	public Program(Location location, List<TypeDef> types, List<Constant> constants,
            List<Node> nodes, String main) {
        this(location, types, constants, nodes, main, null);
    }
	
	public Program(List<TypeDef> types, List<Constant> constants, List<Node> nodes) {
		this(Location.NULL, types, constants, nodes, null, null);
	}
	
	public Program(List<TypeDef> types, List<Constant> constants, List<Node> nodes, String main) {
		this(Location.NULL, types, constants, nodes, main, null);
	}
	
	public Program(List<TypeDef> types, List<Constant> constants, List<Node> nodes, 
	        String main, List<RecursiveFunction> recFuns) {
        this(Location.NULL, types, constants, nodes, main, recFuns);
    }

	public Program(List<Node> nodes) {
		this(Location.NULL, null, null, nodes, null, null);
	}

	public Program(Node... nodes) {
		this(Location.NULL, null, null, Arrays.asList(nodes), null, null);
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