package jkind.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jkind.StdErr;
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
import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.FunctionCallExpr;
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
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleExpr;
import jkind.lustre.TupleType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class TypeChecker implements ExprVisitor<Type> {
	private final Map<String, Type> typeTable = new HashMap<>();
	private final Map<String, Type> constantTable = new HashMap<>();
	private final Map<String, Constant> constantDefinitionTable = new HashMap<>();
	private final Map<String, EnumType> enumValueTable = new HashMap<>();
	private final Map<String, Type> variableTable = new HashMap<>();
	private final Map<String, Node> nodeTable;
	private final Map<String, Function> functionTable;
	private boolean passed;

	private TypeChecker(Program program) {
		this.nodeTable = Util.getNodeTable(program.nodes);
		this.functionTable = Util.getFunctionTable(program.functions);
		this.passed = true;

		populateTypeTable(program.types);
		populateEnumValueTable(program.types);
		populateConstantTable(program.constants);
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
		typeTable.putAll(Util.createResolvedTypeTable(typeDefs));
	}

	private void populateEnumValueTable(List<TypeDef> typeDefs) {
		for (EnumType et : Util.getEnumTypes(typeDefs)) {
			for (String id : et.values) {
				enumValueTable.put(id, et);
			}
		}
	}

	private void populateConstantTable(List<Constant> constants) {
		// The constantDefinitionTable is used for constants whose type has not
		// yet been computed
		for (Constant c : constants) {
			constantDefinitionTable.put(c.id, c);
		}

		for (Constant c : constants) {
			addConstant(c);
		}
	}

	private Type addConstant(Constant c) {
		if (constantTable.containsKey(c.id)) {
			return constantTable.get(c.id);
		}

		Type actual = c.expr.accept(this);
		if (c.type == null) {
			constantTable.put(c.id, actual);
			return actual;
		} else {
			Type expected = resolveType(c.type);
			compareTypeAssignment(c.expr, expected, actual);
			constantTable.put(c.id, expected);
			return expected;
		}
	}

	private boolean visitNode(Node node) {
		repopulateVariableTable(node);

		for (Equation eq : node.equations) {
			checkEquation(eq);
		}

		for (Expr assertion : node.assertions) {
			compareTypeAssignment(assertion, NamedType.BOOL, assertion.accept(this));
		}

		return passed;
	}

	private void repopulateVariableTable(Node node) {
		variableTable.clear();
		for (VarDecl v : Util.getVarDecls(node)) {
			variableTable.put(v.id, resolveType(v.type));
		}
	}

	private Type resolveType(Type type) {
		return Util.resolveType(type, typeTable);
	}

	private boolean isIntBased(Type type) {
		return type == NamedType.INT || type instanceof SubrangeIntType;
	}

	private void checkEquation(Equation eq) {
		List<Type> expected = getExpectedTypes(eq.lhs);
		List<Type> actual = removeTuple(eq.expr.accept(this));
		if (expected == null || actual == null || expected.contains(null) || actual.contains(null)) {
			return;
		}

		if (expected.size() != actual.size()) {
			error(eq, "trying to assign " + actual.size() + " values to " + expected.size() + " variables");
			return;
		}

		for (int i = 0; i < expected.size(); i++) {
			Type ex = expected.get(i);
			Type ac = actual.get(i);
			if (!typeAssignable(ex, ac)) {
				error(eq.lhs.get(i), "trying to assign value of type " + ac + " to variable of type " + ex);
			}
		}
	}

	private List<Type> getExpectedTypes(List<IdExpr> lhs) {
		List<Type> result = new ArrayList<>();
		for (IdExpr idExpr : lhs) {
			result.add(idExpr.accept(this));
		}
		return result;
	}

	private List<Type> removeTuple(Type type) {
		if (type == null) {
			return null;
		} else if (type instanceof TupleType) {
			return ((TupleType) type).types;
		} else {
			return Collections.singletonList(type);
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

		error(e, "operator '" + e.op + "' not defined on types " + simple(left) + ", " + simple(right));
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
		return compressTypes(visitCondactExpr(e));
	}

	private Type compressTypes(List<Type> types) {
		if (types == null || types.contains(null)) {
			return null;
		} else {
			return TupleType.compress(types);
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
	public Type visit(FunctionCallExpr e) {
		Function function = functionTable.get(e.function);
		if (function != null) {
			return compressTypes(visitCallExpr(e, e.args, function.inputs, function.outputs));
		}

		error(e, "unknown function " + e.function);
		return null;
	}

	private List<Type> visitCallExpr(Expr e, List<Expr> args, List<VarDecl> inputs, List<VarDecl> outputs) {
		List<Type> actual = new ArrayList<>();
		for (Expr arg : args) {
			actual.add(arg.accept(this));
		}

		List<Type> expected = new ArrayList<>();
		for (VarDecl input : inputs) {
			expected.add(resolveType(input.type));
		}

		if (actual.size() != expected.size()) {
			error(e, "expected " + expected.size() + " arguments, but found " + actual.size());
			return null;
		}

		for (int i = 0; i < expected.size(); i++) {
			compareTypeAssignment(args.get(i), expected.get(i), actual.get(i));
		}

		List<Type> result = new ArrayList<>();
		for (VarDecl decl : outputs) {
			result.add(resolveType(decl.type));
		}
		return result;
	}

	@Override
	public Type visit(IdExpr e) {
		if (variableTable.containsKey(e.id)) {
			return variableTable.get(e.id);
		} else if (constantTable.containsKey(e.id)) {
			return constantTable.get(e.id);
		} else if (constantDefinitionTable.containsKey(e.id)) {
			return addConstant(constantDefinitionTable.get(e.id));
		} else if (enumValueTable.containsKey(e.id)) {
			return enumValueTable.get(e.id);
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
		return compressTypes(visitNodeCallExpr(e));
	}

	private List<Type> visitNodeCallExpr(NodeCallExpr e) {
		Node node = nodeTable.get(e.node);
		if (node != null) {
			return visitCallExpr(e, e.args, node.inputs, node.outputs);
		}
		
		error(e, "unknown node " + e.node);
		return null;
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
				compareTypeAssignment(e.fields.get(expectedField), expectedFieldType, actualFieldType);
			}
		}

		for (String actualField : fields.keySet()) {
			if (!expectedRecordType.fields.keySet().contains(actualField)) {
				error(e.fields.get(actualField), "record of type " + e.id + " has extra field " + actualField);
			}
		}

		return new RecordType(e.location, e.id, fields);
	}

	@Override
	public Type visit(RecordUpdateExpr e) {
		Type type = e.record.accept(this);
		Type valueType = e.value.accept(this);

		if (type == null || valueType == null) {
			return null;
		}

		if (type instanceof RecordType) {
			RecordType rt = (RecordType) type;
			if (rt.fields.containsKey(e.field)) {
				Type fieldType = rt.fields.get(e.field);
				compareTypeAssignment(e.value, fieldType, valueType);
				return rt;
			} else {
				error(e, "expected record type with field " + e.field + ", but found " + simple(type));
			}
		} else {
			error(e.record, "expected a record type, but found " + simple(type));
		}
		return null;
	}

	@Override
	public Type visit(TupleExpr e) {
		List<Type> types = new ArrayList<>();
		for (Expr expr : e.elements) {
			types.add(expr.accept(this));
		}
		return compressTypes(types);
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

	private void compareTypeAssignment(Ast ast, Type expected, Type actual) {
		if (expected == null || actual == null) {
			return;
		}

		if (!typeAssignable(expected, actual)) {
			Type found = ContainsSubrange.check(expected) ? actual : simple(actual);
			error(ast, "expected type " + expected + ", but found type " + found);
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
			return exRange.low.compareTo(acRange.low) <= 0 && exRange.high.compareTo(acRange.high) >= 0;
		}

		if (expected instanceof ArrayType && actual instanceof ArrayType) {
			ArrayType expectedArrayType = (ArrayType) expected;
			ArrayType actualArrayType = (ArrayType) actual;
			return expectedArrayType.size == actualArrayType.size
					&& typeAssignable(expectedArrayType.base, actualArrayType.base);
		}

		if (expected instanceof TupleType && actual instanceof TupleType) {
			TupleType expectedTupleType = (TupleType) expected;
			TupleType actualTupleType = (TupleType) actual;
			if (expectedTupleType.types.size() != actualTupleType.types.size()) {
				return false;
			}
			for (int i = 0; i < expectedTupleType.types.size(); i++) {
				if (!typeAssignable(expectedTupleType.types.get(i), actualTupleType.types.get(i))) {
					return false;
				}
			}
			return true;
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

	private Type simple(Type type) {
		return RemoveSubranges.remove(type);
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
		} else if (t1 instanceof TupleType && t2 instanceof TupleType) {
			TupleType tt1 = (TupleType) t1;
			TupleType tt2 = (TupleType) t2;
			if (tt1.types.size() != tt2.types.size()) {
				return null;
			}
			List<Type> joinList = new ArrayList<>();
			for (int i = 0; i < tt1.types.size(); i++) {
				Type join = joinTypes(tt1.types.get(i), tt2.types.get(i));
				if (join == null) {
					return null;
				} else {
					joinList.add(join);
				}
			}
			return new TupleType(joinList);
		} else if (t1.equals(t2)) {
			return t1;
		} else {
			return null;
		}
	}

	private void error(Location location, String message) {
		passed = false;
		StdErr.error(location, message);
	}

	private void error(Ast ast, String message) {
		error(ast.location, message);
	}
}
