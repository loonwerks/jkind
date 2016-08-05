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
	private Relation ivcTransitionRelation;

	public Specification(Node raw, boolean slicing) {
		this.dependencyMap = new DependencyMap(raw, raw.properties);
		this.node = slicing ? LustreSlicer.slice(raw, dependencyMap) : raw;
		this.typeMap = Util.getTypeMap(node);
	}

	public Specification(Node raw) {
		this(raw, true);
	}

	public Relation getTransitionRelation() {
		if (transitionRelation == null) {
			transitionRelation = Lustre2Sexp.constructTransitionRelation(node);
		}
		return transitionRelation;
	}

	public Relation getIvcTransitionRelation() {
		if (ivcTransitionRelation == null) {
			ivcTransitionRelation = Lustre2Sexp.constructIvcTransitionRelation(node);
		}
		return ivcTransitionRelation;
	}
}
