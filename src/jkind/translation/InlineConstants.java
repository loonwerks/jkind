package jkind.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class InlineConstants {
	public static Program program(Program program) {
		Map<String, Expr> constants = getConstantsMap(program);
		List<Constant> emptyConstants = Collections.emptyList();
		List<Node> inlinedNodes = new ArrayList<>();

		for (Node node : program.nodes) {
			inlinedNodes.add(node(node, constants));
		}

		return new Program(program.location, program.types, emptyConstants, inlinedNodes);
	}

	public static Node node(Node node, Map<String, Expr> constants) {
		removeShadowedConstants(constants, node);
		SubstitutionVisitor inliner = new SubstitutionVisitor(constants);

		List<Equation> equations = new ArrayList<>();
		for (Equation eq : node.equations) {
			equations.add(new Equation(eq.location, eq.lhs, eq.expr.accept(inliner)));
		}

		List<Expr> assertions = new ArrayList<>();
		for (Expr assertion : node.assertions) {
			assertions.add(assertion.accept(inliner));
		}

		return new Node(node.location, node.id, node.inputs, node.outputs, node.locals, equations,
				node.properties, assertions);
	}

	private static Map<String, Expr> getConstantsMap(Program program) {
		Map<String, Expr> constants = new HashMap<>();
		for (Constant c : program.constants) {
			SubstitutionVisitor inliner = new SubstitutionVisitor(constants);
			constants.put(c.id, c.expr.accept(inliner));
		}
		return constants;
	}

	private static void removeShadowedConstants(Map<String, Expr> constants, Node node) {
		for (VarDecl varDecl : Util.getVarDecls(node)) {
			constants.remove(varDecl.id);
		}
	}
}
