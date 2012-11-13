package jkind.translation;

import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.slicing.DependencyMap;

public class Specification {
	final public String filename;
	final public Node node;
	final public DependencyMap dependencyMap;
	final public Map<String, Type> typeMap;
	final public Lustre2Sexps translation;

	public Specification(String filename, Node node) {
		this.filename = filename;
		this.node = node;
		this.dependencyMap = new DependencyMap(node);
		this.typeMap = Util.getTypeMap(node);
		this.translation = new Lustre2Sexps(node);
	}
}
