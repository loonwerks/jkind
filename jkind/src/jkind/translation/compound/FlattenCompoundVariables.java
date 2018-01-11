package jkind.translation.compound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.translation.SubstitutionVisitor;
import jkind.util.Util;

/**
 * Flatten array and record variables to scalars variables
 * 
 * Assumption: All node calls have been inlined.
 * 
 * Assumption: All array indices are integer literals
 */
public class FlattenCompoundVariables extends AstMapVisitor {
	public static Program program(Program program) {
		return new FlattenCompoundVariables().visit(program);
	}

	private final Map<String, Type> originalTypes = new HashMap<>();

	@Override
	public Node visit(Node node) {
		addOriginalTypes(node.inputs);
		addOriginalTypes(node.outputs);
		addOriginalTypes(node.locals);

		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs().addInputs(CompoundUtil.flattenVarDecls(node.inputs));
		builder.clearOutputs().addOutputs(CompoundUtil.flattenVarDecls(node.outputs));
		builder.clearLocals().addLocals(CompoundUtil.flattenVarDecls(node.locals));
		builder.clearEquations().addEquations(flattenLeftHandSide(node.equations));
		if (node.realizabilityInputs != null) {
			builder.setRealizabilityInputs(flattenNames(node.realizabilityInputs));
		}
		builder.clearIvc().addIvcs(flattenNames(node.ivc));

		Map<String, Expr> map = createExpandedVariables(Util.getVarDecls(node));
		return new SubstitutionVisitor(map).visit(builder.build());
	}

	private void addOriginalTypes(List<VarDecl> varDecls) {
		for (VarDecl varDecl : varDecls) {
			originalTypes.put(varDecl.id, varDecl.type);
		}
	}

	private List<String> flattenNames(List<String> names) {
		List<String> result = new ArrayList<>();
		for (String name : names) {
			IdExpr id = new IdExpr(name);
			for (ExprType et : CompoundUtil.flattenExpr(id, originalTypes.get(name))) {
				result.add(et.expr.toString());
			}
		}
		return result;
	}

	private List<Equation> flattenLeftHandSide(List<Equation> equations) {
		List<Equation> result = new ArrayList<>();
		for (Equation eq : equations) {
			IdExpr lhs = eq.lhs.get(0);
			Type type = originalTypes.get(lhs.id);
			result.addAll(flattenLeftHandSide(lhs, eq.expr, type));
		}
		return result;
	}

	private static List<Equation> flattenLeftHandSide(Expr lhs, Expr rhs, Type type) {
		List<Equation> result = new ArrayList<>();
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			for (int i = 0; i < arrayType.size; i++) {
				Expr accessLhs = new ArrayAccessExpr(lhs, i);
				Expr accessRhs = new ArrayAccessExpr(rhs, i);
				result.addAll(flattenLeftHandSide(accessLhs, accessRhs, arrayType.base));
			}
		} else if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			for (Entry<String, Type> entry : recordType.fields.entrySet()) {
				Expr accessLhs = new RecordAccessExpr(lhs, entry.getKey());
				Expr accessRhs = new RecordAccessExpr(rhs, entry.getKey());
				result.addAll(flattenLeftHandSide(accessLhs, accessRhs, entry.getValue()));
			}
		} else {
			result.add(new Equation(new IdExpr(lhs.toString()), rhs));
		}
		return result;
	}

	private Map<String, Expr> createExpandedVariables(List<VarDecl> varDecls) {
		Map<String, Expr> map = new HashMap<>();
		for (VarDecl varDecl : varDecls) {
			IdExpr expr = new IdExpr(varDecl.id);
			Type type = originalTypes.get(varDecl.id);
			map.put(varDecl.id, expand(expr, type));
		}
		return map;
	}

	private Expr expand(Expr expr, Type type) {
		return new Expander() {
			@Override
			protected Expr baseCase(Expr expr) {
				return new IdExpr(expr.toString());
			}
		}.expand(expr, type);
	}
}
