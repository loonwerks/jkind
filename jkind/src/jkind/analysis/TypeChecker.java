package jkind.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Ast;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Location;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.TypeResolutionException;
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

	public TypeChecker(Program program) {
		this();
		populateTypeTable(program.types);
		populateConstantTable(program.constants);
		this.nodeTable = Util.getNodeTable(program.nodes);
	}

	public static boolean check(Program program) {
		return new TypeChecker(program).visitProgram(program);
	}

	private boolean visitProgram(Program program) {
		for (Node node : program.nodes) {
			visitNode(node);
		}

		return passed;
	}

	private void populateTypeTable(List<TypeDef> typeDefs) {
		for (TypeDef def : typeDefs) {
			typeTable.put(def.id, resolveType(def.type));
		}
	}

	private void populateConstantTable(List<Constant> constants) {
		for (Constant c : constants) {
			Type actual = c.expr.accept(this);
			if (c.type == null) {
				constantTable.put(c.id, actual);
			} else {
				Type expected = resolveType(c.type);
				compareTypeAssignment(c.expr, expected, actual);
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

		for (Expr assertion : node.assertions) {
			compareTypeAssignment(assertion, NamedType.BOOL, assertion.accept(this));
		}

		return passed;
	}

	public void repopulateVariableTable(Node node) {
		variableTable.clear();
		for (VarDecl v : Util.getVarDecls(node)) {
			variableTable.put(v.id, resolveType(v.type));
		}
	}

	private Type resolveType(Type type) {
		try {
			return Util.resolveType(type, typeTable);
		} catch (TypeResolutionException e) {
			error(e.type.location, "unknown type " + e.type);
			return null;
		}
	}

	private boolean isIntBased(Type type) {
		return type == NamedType.INT || type instanceof SubrangeIntType;
	}

	private void checkSingleAssignment(Equation eq) {
		Type expected = eq.lhs.get(0).accept(this);
		Type actual = eq.expr.accept(this);
		compareTypeAssignment(eq, expected, actual);
	}

	private void checkNodeCallAssignment(Equation eq) {
		if (eq.expr instanceof NodeCallExpr || eq.expr instanceof CondactExpr) {
			List<Type> expected = new ArrayList<>();
			for (IdExpr idExpr : eq.lhs) {
				expected.add(idExpr.accept(this));
			}

			List<Type> actual = visitTopLevelCall(eq.expr);
			if (actual == null) {
				return;
			}

			if (expected.size() != actual.size()) {
				error(eq, "expected " + expected.size() + " values, but found " + actual.size());
				return;
			}

			for (int i = 0; i < expected.size(); i++) {
				compareTypeAssignment(eq.lhs.get(i), expected.get(i), actual.get(i));
			}
		} else {
			error(eq.expr, "expected node call for multiple value assignment");
			return;
		}
	}

	private List<Type> visitTopLevelCall(Expr call) {
		if (call instanceof NodeCallExpr) {
			return visitNodeCallExpr((NodeCallExpr) call);
		} else if (call instanceof CondactExpr) {
			return visitCondactExpr((CondactExpr) call);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Type visit(ArrayAccessExpr e) {
		Type type = e.array.accept(this);
		Type indexType = e.index.accept(this);
		if (type == null || indexType == null) {
			return null;
		}

		if (!isIntBased(indexType)) {
			error(e.index, "expected type int, but found " + simple(indexType));
		}

		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return arrayType.base;
		}

		error(e.array, "expected array type, but found " + simple(type));
		return null;
	}

	@Override
	public Type visit(ArrayExpr e) {
		Iterator<Expr> iterator = e.elements.iterator();

		Type common = iterator.next().accept(this);
		if (common == null) {
			return null;
		}

		while (iterator.hasNext()) {
			Expr nextExpr = iterator.next();
			Type next = nextExpr.accept(this);
			if (next == null) {
				return null;
			}
			Type join = joinTypes(common, next);
			if (join == null) {
				error(nextExpr, "expected type " + simple(common) + ", but found " + simple(next));
				return null;
			}
			common = join;
		}

		return new ArrayType(common, e.elements.size());
	}

	@Override
	public Type visit(ArrayUpdateExpr e) {
		Type type = e.array.accept(this);
		Type indexType = e.index.accept(this);
		Type valueType = e.value.accept(this);
		if (type == null || indexType == null || valueType == null) {
			return null;
		}

		if (!isIntBased(indexType)) {
			error(e.index, "expected type int, but found " + simple(indexType));
		}

		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			compareTypeAssignment(e.value, arrayType.base, valueType);
			return arrayType;
		}

		error(e.array, "expected array type, but found " + simple(type));
		return null;
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
			if (isIntBased(left) && isIntBased(right)) {
				return NamedType.INT;
			}
			break;

		case DIVIDE:
			if (left == NamedType.REAL && right == NamedType.REAL) {
				return NamedType.REAL;
			}
			break;

		case INT_DIVIDE:
		case MODULUS:
			if (isIntBased(left) && isIntBased(right)) {
				return NamedType.INT;
			}
			break;

		case EQUAL:
		case NOTEQUAL:
			if (joinTypes(left, right) != null) {
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
			if (isIntBased(left) && isIntBased(right)) {
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
			Type join = joinTypes(left, right);
			if (join != null) {
				return join;
			}
			break;
		}

		error(e, "operator '" + e.op + "' not defined on types " + simple(left) + ", "
				+ simple(right));
		return null;
	}

	@Override
	public Type visit(BoolExpr e) {
		return NamedType.BOOL;
	}

	@Override
	public Type visit(CastExpr e) {
		Type type = e.expr.accept(this);
		if (type == null) {
			return null;
		}

		if (e.type == NamedType.REAL && !isIntBased(type)) {
			error(e, "expected type int, but found " + simple(type));
		} else if (e.type == NamedType.INT && type != NamedType.REAL) {
			error(e, "expected type real, but found " + simple(type));
		}
		return e.type;
	}

	@Override
	public Type visit(CondactExpr e) {
		List<Type> result = visitCondactExpr(e);

		if (result == null) {
			return null;
		} else if (result.size() == 1) {
			return result.get(0);
		} else {
			error(e, "condact returns multiple values");
			return null;
		}
	}

	private List<Type> visitCondactExpr(CondactExpr e) {
		compareTypeAssignment(e.clock, NamedType.BOOL, e.clock.accept(this));

		List<Type> expected = visitNodeCallExpr(e.call);
		if (expected == null) {
			return null;
		}

		List<Type> actual = new ArrayList<>();
		for (Expr arg : e.args) {
			actual.add(arg.accept(this));
		}

		if (actual.size() != expected.size()) {
			error(e, "expected " + expected.size() + " default values, but found " + actual.size());
			return null;
		}

		for (int i = 0; i < expected.size(); i++) {
			compareTypeAssignment(e.args.get(i), expected.get(i), actual.get(i));
		}

		return expected;
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

		compareTypeAssignment(e.cond, NamedType.BOOL, condType);
		return compareTypeJoin(e.elseExpr, thenType, elseType);
	}

	@Override
	public Type visit(IntExpr e) {
		return new SubrangeIntType(e.value, e.value);
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

	private List<Type> visitNodeCallExpr(NodeCallExpr e) {
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
			expected.add(resolveType(input.type));
		}

		if (actual.size() != expected.size()) {
			error(e, "expected " + expected.size() + " arguments, but found " + actual.size());
			return null;
		}

		for (int i = 0; i < expected.size(); i++) {
			compareTypeAssignment(e.args.get(i), expected.get(i), actual.get(i));
		}

		List<Type> result = new ArrayList<>();
		for (VarDecl decl : node.outputs) {
			result.add(resolveType(decl.type));
		}
		return result;
	}

	@Override
	public Type visit(RealExpr e) {
		return NamedType.REAL;
	}

	@Override
	public Type visit(RecordAccessExpr e) {
		Type type = e.record.accept(this);
		if (type == null) {
			return null;
		}

		if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			if (recordType.fields.containsKey(e.field)) {
				return recordType.fields.get(e.field);
			}
		}

		error(e, "expected record type with field " + e.field + ", but found " + simple(type));
		return null;
	}

	@Override
	public Type visit(RecordExpr e) {
		Map<String, Type> fields = new HashMap<>();
		for (Entry<String, Expr> entry : e.fields.entrySet()) {
			fields.put(entry.getKey(), entry.getValue().accept(this));
		}

		Type expectedType = typeTable.get(e.id);
		if (expectedType == null) {
			return null;
		} else if (!(expectedType instanceof RecordType)) {
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
				compareTypeAssignment(e.fields.get(expectedField), expectedFieldType,
						actualFieldType);
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
			if (type instanceof SubrangeIntType) {
				SubrangeIntType subrange = (SubrangeIntType) type;
				return new SubrangeIntType(subrange.high.negate(), subrange.low.negate());
			}
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

		error(e, "operator '" + e.op + "' not defined on type " + simple(type));
		return null;
	}

	private Type simple(Type type) {
		if (type instanceof SubrangeIntType) {
			return NamedType.INT;
		} else if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return new ArrayType(simple(arrayType.base), arrayType.size);
		} else {
			return type;
		}
	}

	private void compareTypeAssignment(Ast ast, Type expected, Type actual) {
		if (expected == null || actual == null) {
			return;
		}

		if (!typeAssignable(expected, actual)) {
			Type found = containsSubrange(expected) ? actual : simple(actual);
			error(ast, "expected type " + expected + ", but found type " + found);
		}
	}

	private boolean containsSubrange(Type type) {
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return containsSubrange(arrayType.base);
		} else {
			return type instanceof SubrangeIntType;
		}
	}

	private boolean typeAssignable(Type expected, Type actual) {
		if (expected.equals(actual)) {
			return true;
		}

		if (expected == NamedType.INT && actual instanceof SubrangeIntType) {
			return true;
		}

		if (expected instanceof SubrangeIntType && actual instanceof SubrangeIntType) {
			SubrangeIntType exRange = (SubrangeIntType) expected;
			SubrangeIntType acRange = (SubrangeIntType) actual;
			return exRange.low.compareTo(acRange.low) <= 0
					&& exRange.high.compareTo(acRange.high) >= 0;
		}
		
		if (expected instanceof ArrayType && actual instanceof ArrayType) {
			ArrayType expectedArrayType = (ArrayType) expected;
			ArrayType actualArrayType = (ArrayType) actual;
			return expectedArrayType.size == actualArrayType.size
					&& typeAssignable(expectedArrayType.base, actualArrayType.base);
		}

		return false;
	}

	private Type compareTypeJoin(Ast ast, Type t1, Type t2) {
		if (t1 == null || t2 == null) {
			return null;
		}

		Type join = joinTypes(t1, t2);
		if (join == null) {
			error(ast, "cannot join types " + simple(t1) + " and " + simple(t2));
			return null;
		}
		return join;
	}

	private Type joinTypes(Type t1, Type t2) {
		if (t1 instanceof SubrangeIntType && t2 instanceof SubrangeIntType) {
			SubrangeIntType s1 = (SubrangeIntType) t1;
			SubrangeIntType s2 = (SubrangeIntType) t2;
			return new SubrangeIntType(s1.low.min(s2.low), s1.high.max(s2.high));
		} else if (isIntBased(t1) && isIntBased(t2)) {
			return NamedType.INT;
		} else if (t1 instanceof ArrayType && t2 instanceof ArrayType) {
			ArrayType a1 = (ArrayType) t1;
			ArrayType a2 = (ArrayType) t2;
			Type join = joinTypes(a1.base, a2.base);
			if (a1.size != a2.size || join == null) {
				return null;
			}
			return new ArrayType(join, a1.size);
		} else if (t1.equals(t2)) {
			return t1;
		} else {
			return null;
		}
	}

	private void error(Location location, String message) {
		passed = false;
		System.out.println("Type error at line " + location + " " + message);
	}

	private void error(Ast ast, String message) {
		error(ast.location, message);
	}
}
