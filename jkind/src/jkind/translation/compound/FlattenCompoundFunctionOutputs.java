package jkind.translation.compound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.CallExpr;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.IdExpr;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

/**
 * Flatten array and record outputs of functions
 */
public class FlattenCompoundFunctionOutputs extends AstMapVisitor {
	public static Program program(Program program) {
		return new FlattenCompoundFunctionOutputs().visit(program);
	}

	private final Map<String, Function> originalFunctionTable = new HashMap<>();

	@Override
	public Program visit(Program program) {
		originalFunctionTable.putAll(Util.getFunctionTable(program.functions));
		return super.visit(program);
	}
	
	@Override
	protected List<Function> visitFunctions(List<Function> functions) {
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
			String newFnId = Util.getBaseFunctionName(fn.id) + "." + newOutputId;
			result.add(new Function(newFnId, fn.inputs, new VarDecl(newOutputId, et.type)));
		}
		return result;
	}

	@Override
	public Expr visit(CallExpr e) {
		Function fn = originalFunctionTable.get(e.name);
		List<Expr> args = visitExprs(e.args);
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
