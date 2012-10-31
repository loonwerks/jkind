package jkind.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.AST;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.ExprVisitor;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.RealExpr;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.translation.Util;

public class TypeChecker implements ExprVisitor<Type> {
	private Map<String, Type> table;
	private boolean passed;

	public TypeChecker() {
		this.table = new HashMap<String, Type>();
		this.passed = true;
	}

	public static boolean node(Node node) {
		return new TypeChecker().visitNode(node);
	}

	public boolean visitNode(Node node) {
		addConstantsToTable(node.constants);
		table.putAll(Util.createTypeMap(node));

		for (Equation eq : node.equations) {
			Type expected = table.get(eq.id);
			Type actual = eq.expr.accept(this);
			if (expected == null) {
				error(eq, "unknown variable " + eq.id);
			} else {
				compareTypes(eq, expected, actual);
			}
		}

		return passed;
	}

	private void addConstantsToTable(List<Constant> constants) {
		for (Constant c : constants) {
			table.put(c.id, c.expr.accept(this));
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
		Type type = table.get(e.id);
		if (type == null) {
			error(e, "unknown variable " + e.id);
		}
		return type;
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
		System.out.println("Type error at line " + ast.location.line + ":"
				+ ast.location.charPositionInLine + " " + message);
	}
}
