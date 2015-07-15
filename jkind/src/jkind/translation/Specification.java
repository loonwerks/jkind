package jkind.translation;

import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.util.Util;

public class Specification {
	public final Node node;
	public final DependencyMap dependencyMap;
	public final Map<String, Type> typeMap;
	private Relation transitionRelation;

	public Specification(Node raw) {
		this.dependencyMap = new DependencyMap(raw, raw.properties);
		this.node = LustreSlicer.slice(raw, dependencyMap);
		this.typeMap = Util.getTypeMap(node);
	}

	public Relation getTransitionRelation() {
		if (transitionRelation == null) {
			transitionRelation = Lustre2Sexp.constructTransitionRelation(node);
		}
		return transitionRelation;
	}
}
