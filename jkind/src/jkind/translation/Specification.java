package jkind.translation;

import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.slicing.DependencyMap;
import jkind.util.Util;

public class Specification {
	public final Node node;
	public final DependencyMap dependencyMap;
	public final Map<String, Type> typeMap;
	public final Relation transitionRelation;

	public Specification(Node node, DependencyMap dependencyMap) {
		this.node = node;
		this.dependencyMap = dependencyMap;
		this.typeMap = Util.getTypeMap(node);
		this.transitionRelation = Lustre2Sexp.constructTransitionRelation(node);
	}
}
