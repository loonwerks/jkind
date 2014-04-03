package jkind.translation.compound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.lustre.CallExpr;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.IdExpr;
import jkind.lustre.InlinedProgram;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprMapVisitor;
import jkind.util.Util;

/**
 * Flatten array and record outputs of functions
 */
public class FlattenCompoundFunctionOutputs extends ExprMapVisitor {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		FlattenCompoundFunctionOutputs flattener = new FlattenCompoundFunctionOutputs(ip.functions);
		List<Function> functions = flattener.visitFunctions(ip.functions);
		Node node = flattener.visitNode(ip.node);
		return new InlinedProgram(functions, node);
	}

	private final Map<String, Function> originalFunctionTable;

	public FlattenCompoundFunctionOutputs(List<Function> functions) {
		originalFunctionTable = Util.getFunctionTable(functions);
	}

	private List<Function> visitFunctions(List<Function> functions) {
		List<Function> result = new ArrayList<>();
		for (Function fn : functions) {
			result.addAll(visitFunction(fn));
		}
		return result;
	}

	private List<Function> visitFunction(Function fn) {
		List<Function> result = new ArrayList<>();
		VarDecl output = fn.outputs.get(0);
		Expr originalFnId = new IdExpr(output.id);
		for (ExprType et : CompoundUtil.flattenExpr(originalFnId, output.type)) {
			String newOutputId = et.expr.toString();
			String newFnId = Util.getBase(fn.id) + "." + newOutputId;
			result.add(new Function(newFnId, fn.inputs, new VarDecl(newOutputId, et.type)));
		}
		return result;
	}

	@Override
	public Expr visit(CallExpr e) {
		Function fn = originalFunctionTable.get(e.name);
		List<Expr> args = visitAll(e.args);
		return expand(new IdExpr(fn.id), fn.outputs.get(0).type, args);
	}

	private static Expr expand(Expr expr, Type type, final List<Expr> args) {
		return new Expander() {
			@Override
			protected Expr baseCase(Expr expr) {
				return new CallExpr(expr.toString(), args);
			}
		}.expand(expr, type);
	}
}
