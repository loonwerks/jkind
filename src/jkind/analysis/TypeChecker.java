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
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.translation.Util;

public class TypeChecker implements ExprVisitor<Type> {
	private Map<String, Type> constantTable;
	private Map<String, Type> variableTable;
	private Map<String, Node> nodeTable;
	private boolean passed;

	public TypeChecker() {
		this.constantTable = new HashMap<String, Type>();
		this.variableTable = new HashMap<String, Type>();
		this.nodeTable = new HashMap<String, Node>();
		this.passed = true;
	}

	public static boolean check(Program program) {
		return new TypeChecker().visitProgram(program);
	}
	
	public boolean visitProgram(Program program) {
		populateConstantTable(program.constants);
		nodeTable = Util.getNodeTable(program.nodes);
		
		for (Node node : program.nodes) {
			visitNode(node);
		}
		
		return passed;
	}
	
	private void populateConstantTable(List<Constant> constants) {
		for (Constant c : constants) {
			constantTable.put(c.id, c.expr.accept(this));
		}
	}
	
	public boolean visitNode(Node node) {
		variableTable.clear();
		variableTable.putAll(Util.createTypeMap(node));

		for (Equation eq : node.equations) {
			Type expected = lookup(eq.id);
			Type actual = eq.expr.accept(this);
			if (expected == null) {
				error(eq, "unknown variable " + eq.id);
			} else {
				compareTypes(eq, expected, actual);
			}
		}

		return passed;
	}

	private Type lookup(String id) {
		if (variableTable.containsKey(id)) {
			return variableTable.get(id);
		} else if (constantTable.containsKey(id)) {
			return constantTable.get(id);
		} else {
			return null;
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
		Type type = lookup(e.id);
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
	public Type visit(NodeCallExpr e) {
		Node node = nodeTable.get(e.node);
		if (node == null) {
			error(e, "unknown node " + e.node);
		}
		
		List<Type> actual = new ArrayList<Type>();
		for (Expr arg : e.args) {
			actual.add(arg.accept(this));
		}
		
		if (actual.contains(null)) {
			return null;
		}
		
		List<Type> expected = new ArrayList<Type>();
		for (VarDecl input : node.inputs) {
			expected.add(input.type);
		}
		
		if (actual.size() != expected.size()) {
			error(e, "expected " + expected.size() + " arguments, but found " + actual.size());
		}
		
		for (int i = 0; i < expected.size(); i++) {
			compareTypes(e.args.get(i), expected.get(i), actual.get(i));
		}
		
		if (node.outputs.size() == 1) {
			return node.outputs.get(0).type;
		} else {
			System.out.println("Error at line " + e.location + " unsupported return values from node");
			return null;
		}
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
