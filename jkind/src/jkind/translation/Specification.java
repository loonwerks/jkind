package jkind.translation;

import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.slicing.DependencyMap;
import jkind.util.Util;

public class Specification {
	final public String filename;
	final public Node node;
	final public DependencyMap dependencyMap;
	final public Map<String, Type> typeMap;
	final public TransitionRelation transitionRelation;

	public Specification(String filename, Node node, DependencyMap dependencyMap) {
		this.filename = filename;
		this.node = node;
		this.dependencyMap = dependencyMap;
		this.typeMap = Util.getTypeMap(node);
		this.transitionRelation = Lustre2Sexp.constructTransitionRelation(node);
	}
}
