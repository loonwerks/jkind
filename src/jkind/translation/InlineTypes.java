package jkind.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;

public class InlineTypes {
	public static Program program(Program program) {
		Map<String, Type> types = getTypeMap(program);
		List<TypeDef> emptyTypes = Collections.emptyList();
		List<Node> inlinedNodes = new ArrayList<Node>();

		for (Node node : program.nodes) {
			inlinedNodes.add(node(node, types));
		}

		return new Program(program.location, emptyTypes, program.constants, inlinedNodes);
	}

	public static Node node(Node node, Map<String, Type> types) {
		List<VarDecl> inputs = varDecls(node.inputs, types);
		List<VarDecl> outputs = varDecls(node.outputs, types);
		List<VarDecl> locals = varDecls(node.locals, types);

		return new Node(node.location, node.id, inputs, outputs, locals, node.equations,
				node.properties, node.assertions);
	}

	private static List<VarDecl> varDecls(List<VarDecl> decls, Map<String, Type> types) {
		List<VarDecl> inlinedDecls = new ArrayList<VarDecl>();
		for (VarDecl decl : decls) {
			Type base = getBuiltinType(decl.type, types);
			inlinedDecls.add(new VarDecl(decl.location, decl.id, base));
		}
		return inlinedDecls;
	}

	private static Type getBuiltinType(Type type, Map<String, Type> types) {
		Type curr = type;
		while (!curr.isBuiltin()) {
			curr = types.get(curr.name);
		}
		return curr;
	}

	private static Map<String, Type> getTypeMap(Program program) {
		Map<String, Type> types = new HashMap<String, Type>();
		for (TypeDef def : program.types) {
			types.put(def.id, def.type);
		}
		return types;
	}
}
