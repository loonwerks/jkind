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
import jkind.lustre.VarDecl;

public class InlineConstants {
	public static Node node(Node node) {
		Map<String, Expr> constants = getConstantsMap(node);
		removeShadowedConstants(constants, node);
		InlineVisitor inliner = new InlineVisitor(constants);

		List<Equation> equations = new ArrayList<Equation>();
		for (Equation eq : node.equations) {
			equations.add(new Equation(eq.location, eq.id, eq.expr.accept(inliner)));
		}
		
		return new Node(node.location, Collections.<Constant> emptyList(), node.inputs,
				node.outputs, node.locals, equations, node.properties);
	}

	private static Map<String, Expr> getConstantsMap(Node node) {
		Map<String, Expr> constants = new HashMap<String, Expr>();
		for (Constant c : node.constants) {
			constants.put(c.id, c.expr);
		}
		return constants;
	}

	private static void removeShadowedConstants(Map<String, Expr> constants, Node node) {
		for (VarDecl varDecl : Util.getVarDecls(node)) {
			constants.remove(varDecl.id);
		}
	}
}
