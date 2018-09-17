package jkind.translation;

import java.util.List;
import java.util.Map;

import jkind.lustre.Function;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.util.Util;

public class Specification {
	public final Node node;
	public final List<Function> functions;
	public final DependencyMap dependencyMap;
	public final Map<String, Type> typeMap;
	private Relation transitionRelation;
	private Relation ivcTransitionRelation;

	public Specification(Program program, boolean slicing) {
		Node main = program.getMainNode();
		if (slicing) {
			this.dependencyMap = new DependencyMap(main, main.properties, program.functions);
		} else {
			this.dependencyMap = DependencyMap.full(main, program.functions);
		}
		this.node = LustreSlicer.slice(main, dependencyMap);
		this.functions = Util.safeList(program.functions);
		this.typeMap = Util.getTypeMap(node);
	}

	public Specification(Program program) {
		this(program, false);
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
