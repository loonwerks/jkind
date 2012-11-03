package jkind.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.AST;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.translation.Util;

public class TypeChecker implements ExprVisitor<Type> {
	private Map<String, Type> typeTable;
	private Map<String, Type> constantTable;
	private Map<String, Type> variableTable;
	private Map<String, Node> nodeTable;
	private boolean passed;

	public TypeChecker() {
		this.typeTable = new HashMap<String, Type>();
		this.constantTable = new HashMap<String, Type>();
		this.variableTable = new HashMap<String, Type>();
		this.nodeTable = new HashMap<String, Node>();
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
				error(def, "unknown type " + def.type.name);
			}
			typeTable.put(def.id, type);
		}
	}

	private void populateConstantTable(List<Constant> constants) {
		for (Constant c : constants) {
			constantTable.put(c.id, c.expr.accept(this));
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

	private void repopulateVariableTable(Node node) {
		variableTable.clear();
		for (VarDecl v : Util.getVarDecls(node)) {
			Type type = lookupBaseType(v.type);
			if (type == null) {
				error(v, "unknown type " + v.type);
				type = null;
			}
			variableTable.put(v.id, type);
		}
	}

	private Type lookupBaseType(Type type) {
		if (type.isBase()) {
			return type;
		} else if (type instanceof SubrangeIntType) {
			return Type.INT;
		} else if (typeTable.containsKey(type.name)) {
			return typeTable.get(type.name);
		} else {
			return null;
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

			List<Type> expected = new ArrayList<Type>();
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
			if (left == Type.REAL && right == Type.REAL) {
				return Type.REAL;
			}
			if (left == Type.INT && right == Type.INT) {
				return Type.INT;
			}
			break;

		case DIVIDE:
			if (left == Type.REAL && right == Type.REAL) {
				return Type.REAL;
			}
			break;

		case INT_DIVIDE:
			if (left == Type.INT && right == Type.INT) {
				return Type.INT;
			}
			break;

		case EQUAL:
		case NOTEQUAL:
			if (left == right) {
				return Type.BOOL;
			}
			break;

		case GREATER:
		case LESS:
		case GREATEREQUAL:
		case LESSEQUAL:
			if (left == Type.REAL && right == Type.REAL) {
				return Type.BOOL;
			}
			if (left == Type.INT && right == Type.INT) {
				return Type.BOOL;
			}
			break;

		case OR:
		case AND:
		case XOR:
		case IMPLIES:
			if (left == Type.BOOL && right == Type.BOOL) {
				return Type.BOOL;
			}
			break;

		case ARROW:
			if (left == right) {
				return left;
			}
			break;
		}

		error(e, "operator '" + e.op + "' not defined on types " + left + ", " + right);
		return null;
	}

	@Override
	public Type visit(BoolExpr e) {
		return Type.BOOL;
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

		compareTypes(e.cond, Type.BOOL, condType);
		compareTypes(e.elseExpr, thenType, elseType);

		return thenType;
	}

	@Override
	public Type visit(IntExpr e) {
		return Type.INT;
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

		List<Type> actual = new ArrayList<Type>();
		for (Expr arg : e.args) {
			actual.add(arg.accept(this));
		}

		List<Type> expected = new ArrayList<Type>();
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

		List<Type> result = new ArrayList<Type>();
		for (VarDecl decl : node.outputs) {
			result.add(lookupBaseType(decl.type));
		}
		return result;
	}

	@Override
	public Type visit(RealExpr e) {
		return Type.REAL;
	}

	@Override
	public Type visit(UnaryExpr e) {
		Type type = e.expr.accept(this);
		if (type == null) {
			return null;
		}

		switch (e.op) {
		case NEGATIVE:
			if (type == Type.INT || type == Type.REAL) {
				return type;
			}
			break;

		case NOT:
			if (type == Type.BOOL) {
				return type;
			}
			break;

		case PRE:
			return type;
		}

		error(e, "operator '" + e.op + "' not defined on type " + type);
		return null;
	}

	private void compareTypes(AST ast, Type expected, Type actual) {
		if (expected == null || actual == null) {
			return;
		}

		if (expected != actual) {
			error(ast, "expected type " + expected + " but found type " + actual);
		}
	}

	private void error(AST ast, String message) {
		passed = false;
		System.out.println("Type error at line " + ast.location + " " + message);
	}
}
