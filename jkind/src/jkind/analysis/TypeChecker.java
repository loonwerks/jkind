package jkind.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.Ast;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.ProjectionExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.util.Util;

public class TypeChecker implements ExprVisitor<Type> {
	private Map<String, Type> typeTable;
	private Map<String, Type> constantTable;
	private Map<String, Type> variableTable;
	private Map<String, Node> nodeTable;
	private boolean passed;

	public TypeChecker() {
		this.typeTable = new HashMap<>();
		this.constantTable = new HashMap<>();
		this.variableTable = new HashMap<>();
		this.nodeTable = new HashMap<>();
		this.passed = true;
	}

	public static boolean check(Program program) {
		return new TypeChecker().visitProgram(program);
	}

	public boolean visitProgram(Program program) {
		populateTypeTable(program.types);
		populateConstantTable(program.constants);
		nodeTable = Util.getNodeTable(program.nodes);

		for (Node node : program.nodes) {
			visitNode(node);
		}

		return passed;
	}

	private void populateTypeTable(List<TypeDef> typeDefs) {
		for (TypeDef def : typeDefs) {
			Type type = lookupBaseType(def.type);
			if (type == null) {
				error(def, "unknown type " + def.type);
			}
			typeTable.put(def.id, type);
		}
	}

	private void populateConstantTable(List<Constant> constants) {
		for (Constant c : constants) {
			Type actual = c.expr.accept(this);
			if (c.type == null) {
				constantTable.put(c.id, actual);
			} else {
				Type expected = lookupBaseType(c.type);
				compareTypes(c.expr, expected, actual);
				constantTable.put(c.id, expected);
			}
		}
	}

	public boolean visitNode(Node node) {
		repopulateVariableTable(node);

		for (Equation eq : node.equations) {
			if (eq.lhs.size() == 1) {
				checkSingleAssignment(eq);
			} else {
				checkNodeCallAssignment(eq);
			}
		}

		return passed;
	}

	public void repopulateVariableTable(Node node) {
		variableTable.clear();
		for (VarDecl v : Util.getVarDecls(node)) {
			Type type = lookupBaseType(v.type);
			if (type == null) {
				error(v, "unknown type " + v.type);
			}
			variableTable.put(v.id, type);
		}
	}

	private Type lookupBaseType(Type type) {
		Type resolved = Util.resolveType(type, typeTable);
		if (resolved instanceof SubrangeIntType) {
			return NamedType.INT;
		} else {
			return resolved;
		}
	}

	private void checkSingleAssignment(Equation eq) {
		Type expected = eq.lhs.get(0).accept(this);
		Type actual = eq.expr.accept(this);
		compareTypes(eq, expected, actual);
	}

	private void checkNodeCallAssignment(Equation eq) {
		if (eq.expr instanceof NodeCallExpr) {
			NodeCallExpr call = (NodeCallExpr) eq.expr;

			List<Type> expected = new ArrayList<>();
			for (IdExpr idExpr : eq.lhs) {
				expected.add(idExpr.accept(this));
			}

			List<Type> actual = visitNodeCallExpr(call);
			if (actual == null) {
				return;
			}

			if (expected.size() != actual.size()) {
				error(eq, "expected " + expected.size() + " values but found " + actual.size());
				return;
			}

			for (int i = 0; i < expected.size(); i++) {
				compareTypes(eq.lhs.get(i), expected.get(i), actual.get(i));
			}
		} else {
			error(eq.expr, "expected node call for multiple value assignment");
			return;
		}
	}

	@Override
	public Type visit(BinaryExpr e) {
		Type left = e.left.accept(this);
		Type right = e.right.accept(this);
		if (left == null || right == null) {
			return null;
		}

		switch (e.op) {
		case PLUS:
		case MINUS:
		case MULTIPLY:
			if (left == NamedType.REAL && right == NamedType.REAL) {
				return NamedType.REAL;
			}
			if (left == NamedType.INT && right == NamedType.INT) {
				return NamedType.INT;
			}
			break;

		case DIVIDE:
			if (left == NamedType.REAL && right == NamedType.REAL) {
				return NamedType.REAL;
			}
			break;

		case INT_DIVIDE:
			if (left == NamedType.INT && right == NamedType.INT) {
				return NamedType.INT;
			}
			break;

		case EQUAL:
		case NOTEQUAL:
			if (left.equals(right)) {
				return NamedType.BOOL;
			}
			break;

		case GREATER:
		case LESS:
		case GREATEREQUAL:
		case LESSEQUAL:
			if (left == NamedType.REAL && right == NamedType.REAL) {
				return NamedType.BOOL;
			}
			if (left == NamedType.INT && right == NamedType.INT) {
				return NamedType.BOOL;
			}
			break;

		case OR:
		case AND:
		case XOR:
		case IMPLIES:
			if (left == NamedType.BOOL && right == NamedType.BOOL) {
				return NamedType.BOOL;
			}
			break;

		case ARROW:
			if (left.equals(right)) {
				return left;
			}
			break;
		}

		error(e, "operator '" + e.op + "' not defined on types " + left + ", " + right);
		return null;
	}

	@Override
	public Type visit(BoolExpr e) {
		return NamedType.BOOL;
	}

	@Override
	public Type visit(IdExpr e) {
		if (variableTable.containsKey(e.id)) {
			return variableTable.get(e.id);
		} else if (constantTable.containsKey(e.id)) {
			return constantTable.get(e.id);
		} else {
			error(e, "unknown variable " + e.id);
			return null;
		}
	}

	@Override
	public Type visit(IfThenElseExpr e) {
		Type condType = e.cond.accept(this);
		Type thenType = e.thenExpr.accept(this);
		Type elseType = e.elseExpr.accept(this);

		compareTypes(e.cond, NamedType.BOOL, condType);
		compareTypes(e.elseExpr, thenType, elseType);

		return thenType;
	}

	@Override
	public Type visit(IntExpr e) {
		return NamedType.INT;
	}

	@Override
	public Type visit(NodeCallExpr e) {
		List<Type> result = visitNodeCallExpr(e);

		if (result == null) {
			return null;
		} else if (result.size() == 1) {
			return result.get(0);
		} else {
			error(e, "node returns multiple values");
			return null;
		}
	}

	public List<Type> visitNodeCallExpr(NodeCallExpr e) {
		Node node = nodeTable.get(e.node);
		if (node == null) {
			error(e, "unknown node " + e.node);
			return null;
		}

		List<Type> actual = new ArrayList<>();
		for (Expr arg : e.args) {
			actual.add(arg.accept(this));
		}

		List<Type> expected = new ArrayList<>();
		for (VarDecl input : node.inputs) {
			expected.add(lookupBaseType(input.type));
		}

		if (actual.size() != expected.size()) {
			error(e, "expected " + expected.size() + " arguments, but found " + actual.size());
			return null;
		}

		for (int i = 0; i < expected.size(); i++) {
			compareTypes(e.args.get(i), expected.get(i), actual.get(i));
		}

		List<Type> result = new ArrayList<>();
		for (VarDecl decl : node.outputs) {
			result.add(lookupBaseType(decl.type));
		}
		return result;
	}

	@Override
	public Type visit(ProjectionExpr e) {
		Type type = e.expr.accept(this);
		if (type == null) {
			return null;
		}

		if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			if (recordType.fields.containsKey(e.field)) {
				return recordType.fields.get(e.field);
			}
		}

		error(e, "expected record type with field " + e.field + " but found " + type);
		return null;
	}

	@Override
	public Type visit(RealExpr e) {
		return NamedType.REAL;
	}

	@Override
	public Type visit(RecordExpr e) {
		Map<String, Type> fields = new HashMap<>();
		for (Entry<String, Expr> entry : e.fields.entrySet()) {
			fields.put(entry.getKey(), entry.getValue().accept(this));
		}

		Type expectedType = typeTable.get(e.id);
		if (!(expectedType instanceof RecordType)) {
			error(e, "unknown record type " + e.id);
			return null;
		}
		RecordType expectedRecordType = (RecordType) expectedType;

		for (Entry<String, Type> entry : expectedRecordType.fields.entrySet()) {
			String expectedField = entry.getKey();
			Type expectedFieldType = entry.getValue();
			if (!fields.containsKey(expectedField)) {
				error(e, "record of type " + e.id + " is missing field " + expectedField);
			} else {
				Type actualFieldType = fields.get(expectedField);
				compareTypes(e.fields.get(expectedField), expectedFieldType, actualFieldType);
			}
		}

		for (String actualField : fields.keySet()) {
			if (!expectedRecordType.fields.keySet().contains(actualField)) {
				error(e.fields.get(actualField), "record of type " + e.id + " has extra field "
						+ actualField);
			}
		}

		return new RecordType(e.location, e.id, fields);
	}

	@Override
	public Type visit(UnaryExpr e) {
		Type type = e.expr.accept(this);
		if (type == null) {
			return null;
		}

		switch (e.op) {
		case NEGATIVE:
			if (type == NamedType.INT || type == NamedType.REAL) {
				return type;
			}
			break;

		case NOT:
			if (type == NamedType.BOOL) {
				return type;
			}
			break;

		case PRE:
			return type;
		}

		error(e, "operator '" + e.op + "' not defined on type " + type);
		return null;
	}

	private void compareTypes(Ast ast, Type expected, Type actual) {
		if (expected == null || actual == null) {
			return;
		}

		if (!expected.equals(actual)) {
			error(ast, "expected type " + expected + " but found type " + actual);
		}
	}

	private void error(Ast ast, String message) {
		passed = false;
		System.out.println("Type error at line " + ast.location + " " + message);
	}
}
