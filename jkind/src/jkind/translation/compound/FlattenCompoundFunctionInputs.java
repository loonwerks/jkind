package jkind.translation.compound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.CallExpr;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.util.Util;

/**
 * Flatten array and record outputs of functions
 */
public class FlattenCompoundFunctionInputs extends AstMapVisitor {
	public static Program program(Program program) {
		return new FlattenCompoundFunctionInputs().visit(program);
	}

	private final Map<String, Function> originalFunctionTable = new HashMap<>();

	@Override
	public Program visit(Program program) {
		originalFunctionTable.putAll(Util.getFunctionTable(program.functions));
		List<Function> functions = visitFunctions(program.functions);
		Node main = visit(program.getMainNode());
		return new Program(program.types, program.constants, functions, main);
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
