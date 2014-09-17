package jkind.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.Program;
import jkind.lustre.TupleExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

/**
 * Split functions so each returns a single value.
 * 
 * Assumption: All node calls have been inlined.
 */
public class SplitFunctions extends AstMapVisitor {
	public static Program program(Program program) {
		return new SplitFunctions().visit(program);
	}

	private final Map<String, Function> originalFunctionTable = new HashMap<>();

	@Override
	public Program visit(Program program) {
		originalFunctionTable.putAll(Util.getFunctionTable(program.functions));
		return super.visit(program);
	}

	@Override
	protected List<Function> visitFunctions(List<Function> functions) {
		List<Function> splitFunctions = new ArrayList<>();
		for (Function function : functions) {
			for (VarDecl output : function.outputs) {
				splitFunctions.add(new Function(function.location, function.id + "." + output.id,
						function.inputs, Collections.singletonList(output)));
			}
		}
		return splitFunctions;
	}

	@Override
	public Expr visit(FunctionCallExpr e) {
		Function function = originalFunctionTable.get(e.name);
		List<Expr> args = visitExprs(e.args);
		List<Expr> exprs = new ArrayList<>();
		for (VarDecl output : function.outputs) {
			exprs.add(new FunctionCallExpr(e.name + "." + output.id, args));
		}
		return TupleExpr.compress(exprs);
	}
}
