package jkind.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jkind.lustre.CallExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.InlinedProgram;
import jkind.lustre.Node;
import jkind.lustre.TupleExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprMapVisitor;
import jkind.util.Util;

/**
 * Split functions so each returns a single value.
 * 
 * Assumption: All node calls have been inlined.
 */
public class SplitFunctions extends ExprMapVisitor {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		SplitFunctions splitter = new SplitFunctions(ip.functions);
		List<Function> functions = splitter.visitFunctions(ip.functions);
		Node node = splitter.visitNode(ip.node);
		return new InlinedProgram(functions, node);
	}

	private final Map<String, Function> originalFunctionTabe;

	public SplitFunctions(List<Function> functions) {
		originalFunctionTabe = Util.getFunctionTable(functions);
	}

	private List<Function> visitFunctions(List<Function> functions) {
		List<Function> splitFunctions = new ArrayList<>();
		for (Function function : functions) {
			for (VarDecl output : function.outputs) {
				splitFunctions.add(new Function(function.location, function.id + "." + output.id,
						function.inputs, Collections.singletonList(output)));
			}
		}
		return splitFunctions;
	}

	private Node visitNode(Node node) {
		List<Equation> equations = visitEquations(node.equations);
		List<Expr> assertions = visitAll(node.assertions);
		return new Node(node.location, node.id, node.inputs, node.outputs, node.locals, equations,
				node.properties, assertions);
	}

	private List<Equation> visitEquations(List<Equation> equations) {
		List<Equation> splitEquations = new ArrayList<>();
		for (Equation equation : equations) {
			Expr expr = equation.expr.accept(this);
			splitEquations.add(new Equation(equation.location, equation.lhs, expr));
		}
		return splitEquations;
	}

	@Override
	public Expr visit(CallExpr e) {
		Function function = originalFunctionTabe.get(e.name);
		List<Expr> args = visitAll(e.args);
		List<Expr> exprs = new ArrayList<>();
		for (VarDecl output : function.outputs) {
			exprs.add(new CallExpr(e.name + "." + output.id, args));
		}
		return TupleExpr.compress(exprs);
	}
}
