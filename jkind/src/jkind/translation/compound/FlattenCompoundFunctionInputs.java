package jkind.translation.compound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.lustre.CallExpr;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.InlinedProgram;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprMapVisitor;
import jkind.util.Util;

/**
 * Flatten array and record outputs of functions
 */
public class FlattenCompoundFunctionInputs extends ExprMapVisitor {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		FlattenCompoundFunctionInputs flattener = new FlattenCompoundFunctionInputs(ip.functions);
		List<Function> functions = flattener.visitFunctions(ip.functions);
		Node node = flattener.visitNode(ip.node);
		return new InlinedProgram(functions, node);
	}

	private final Map<String, Function> originalFunctionTable;

	public FlattenCompoundFunctionInputs(List<Function> functions) {
		originalFunctionTable = Util.getFunctionTable(functions);
	}

	private List<Function> visitFunctions(List<Function> functions) {
		List<Function> result = new ArrayList<>();
		for (Function fn : functions) {
			List<VarDecl> inputs = CompoundUtil.flattenVarDecls(fn.inputs);
			result.add(new Function(fn.id, inputs, fn.outputs));
		}
		return result;
	}

	@Override
	public Expr visit(CallExpr e) {
		Function fn = originalFunctionTable.get(e.name);
		List<Expr> args = visitAll(e.args);
		List<Expr> flatArgs = new ArrayList<>();
		for (int i = 0; i < args.size(); i++) {
			flatArgs.addAll(flattenExpr(args.get(i), fn.inputs.get(i).type));
		}
		return new CallExpr(e.name, flatArgs);
	}

	private List<Expr> flattenExpr(Expr expr, Type type) {
		List<Expr> result = new ArrayList<>();
		for (ExprType et : CompoundUtil.flattenExpr(expr, type)) {
			result.add(et.expr);
		}
		return result;
	}
}
