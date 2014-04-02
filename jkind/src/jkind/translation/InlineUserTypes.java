package jkind.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Function;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class InlineUserTypes {
	public static Program program(Program program) {
		Map<String, Type> types = getTypeMap(program);
		List<TypeDef> emptyTypes = Collections.emptyList();

		List<Function> inlinedFunctions = new ArrayList<>();
		for (Function function : program.functions) {
			inlinedFunctions.add(function(function, types));
		}
		
		List<Node> inlinedNodes = new ArrayList<>();
		for (Node node : program.nodes) {
			inlinedNodes.add(node(node, types));
		}

		return new Program(program.location, emptyTypes, program.constants, inlinedFunctions,
				inlinedNodes, program.main);
	}

	public static Function function(Function function, Map<String, Type> types) {
		List<VarDecl> inputs = varDecls(function.inputs, types);
		List<VarDecl> outputs = varDecls(function.outputs, types);

		return new Function(function.location, function.id, inputs, outputs);
	}
	
	public static Node node(Node node, Map<String, Type> types) {
		List<VarDecl> inputs = varDecls(node.inputs, types);
		List<VarDecl> outputs = varDecls(node.outputs, types);
		List<VarDecl> locals = varDecls(node.locals, types);

		return new Node(node.location, node.id, inputs, outputs, locals, node.equations,
				node.properties, node.assertions);
	}

	private static List<VarDecl> varDecls(List<VarDecl> decls, Map<String, Type> types) {
		List<VarDecl> inlinedDecls = new ArrayList<>();
		for (VarDecl decl : decls) {
			Type base = Util.resolveType(decl.type, types);
			inlinedDecls.add(new VarDecl(decl.location, decl.id, base));
		}
		return inlinedDecls;
	}

	private static Map<String, Type> getTypeMap(Program program) {
		Map<String, Type> types = new HashMap<>();
		for (TypeDef def : program.types) {
			types.put(def.id, Util.resolveType(def.type, types));
		}
		return types;
	}
}
