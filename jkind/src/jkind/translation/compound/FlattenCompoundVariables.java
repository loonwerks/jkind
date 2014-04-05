package jkind.translation.compound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
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
	public static Node node(Node node) {
		return new FlattenCompoundVariables().visit(node);
	}

	private final Map<String, Type> originalTypes = new HashMap<>();

	@Override
	public Node visit(Node node) {
		addOriginalTypes(node.inputs);
		addOriginalTypes(node.outputs);
		addOriginalTypes(node.locals);

		List<VarDecl> inputs = flattenVarDecls(node.inputs);
		List<VarDecl> outputs = flattenVarDecls(node.outputs);
		List<VarDecl> locals = flattenVarDecls(node.locals);
		List<Equation> equations = flattenLeftHandSide(node.equations);
		Node flattened = new Node(node.id, inputs, outputs, locals, equations, node.properties,
				node.assertions);

		Map<String, Expr> map = createExpandedVariables(Util.getVarDecls(node));
		return new SubstitutionVisitor(map).visit(flattened);
	}

	private void addOriginalTypes(List<VarDecl> varDecls) {
		for (VarDecl varDecl : varDecls) {
			originalTypes.put(varDecl.id, varDecl.type);
		}
	}

	private List<VarDecl> flattenVarDecls(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl varDecl : varDecls) {
			IdExpr id = new IdExpr(varDecl.id);
			for (ExprType et : CompoundUtil.flattenExpr(id, varDecl.type)) {
				result.add(new VarDecl(et.expr.toString(), et.type));
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
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			List<Expr> elements = new ArrayList<>();
			for (int i = 0; i < arrayType.size; i++) {
				elements.add(expand(new ArrayAccessExpr(expr, i), arrayType.base));
			}
			return new ArrayExpr(elements);
		} else if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			Map<String, Expr> fields = new HashMap<>();
			for (Entry<String, Type> entry : recordType.fields.entrySet()) {
				String field = entry.getKey();
				Type fieldType = entry.getValue();
				fields.put(field, expand(new RecordAccessExpr(expr, field), fieldType));
			}
			return new RecordExpr(recordType.id, fields);
		} else {
			return new IdExpr(expr.toString());
		}
	}
}
