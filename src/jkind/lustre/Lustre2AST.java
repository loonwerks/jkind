package jkind.lustre;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class Lustre2AST {
	public static Node node(CommonTree tree) {
		List<Constant> constants = constants(getChild(tree, LustreParser.CONSTANTS));
		List<VarDecl> inputs = varDecls(getChild(tree, LustreParser.INPUTS));
		List<VarDecl> outputs = varDecls(getChild(tree, LustreParser.OUTPUTS));
		List<VarDecl> locals = varDecls(getChild(tree, LustreParser.LOCALS));
		List<Equation> equations = equations(getChild(tree, LustreParser.EQUATIONS));
		List<String> properties = properties(getChild(tree, LustreParser.PROPERTIES));

		return new Node(loc(tree), constants, inputs, outputs, locals, equations, properties);
	}

	private static List<Constant> constants(CommonTree tree) {
		List<Constant> constants = new ArrayList<Constant>();
		if (tree == null || tree.getChildCount() == 0) {
			return constants;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			String id = child.getText();
			Expr expr = expr(child.getChild(0));
			constants.add(new Constant(loc(child), id, expr));
		}
		return constants;
	}

	private static List<VarDecl> varDecls(CommonTree tree) {
		List<VarDecl> decls = new ArrayList<VarDecl>();
		if (tree == null || tree.getChildCount() == 0) {
			return decls;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			String id = child.getText();
			Type type = type(child.getChild(0).getText());
			decls.add(new VarDecl(loc(child), id, type));
		}
		return decls;
	}

	private static Type type(String text) {
		if (text.equals("int")) {
			return Type.INT;
		} else if (text.equals("real")) {
			return Type.REAL;
		} else if (text.equals("bool")) {
			return Type.BOOL;
		} else {
			throw new IllegalArgumentException("Unknown type");
		}
	}

	private static List<Equation> equations(CommonTree tree) {
		List<Equation> equations = new ArrayList<Equation>();
		if (tree == null || tree.getChildCount() == 0) {
			return equations;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			String id = child.getText();
			Expr expr = expr(child.getChild(0));
			equations.add(new Equation(loc(tree), id, expr));
		}
		return equations;
	}

	private static Expr expr(Tree tree) {
		return expr((CommonTree) tree);
	}

	private static Expr expr(CommonTree tree) {
		switch (tree.getType()) {
		case LustreParser.BOOL:
			return new BoolExpr(loc(tree), tree.getText().equals("true"));
		case LustreParser.INT:
			return new IntExpr(loc(tree), Integer.parseInt(tree.getText()));
		case LustreParser.REAL:
			return new RealExpr(loc(tree), new BigDecimal(tree.getText()));
		case LustreParser.IDENT:
			return new IdExpr(loc(tree), tree.getChild(0).getText());
		case LustreParser.IF:
			return new IfThenElseExpr(loc(tree), expr(tree.getChild(0)), expr(tree.getChild(1)),
					expr(tree.getChild(2)));
		case LustreParser.NEGATE:
			return new UnaryExpr(loc(tree), UnaryOp.NEGATIVE, expr(tree.getChild(0)));
		case LustreParser.NOT:
			return new UnaryExpr(loc(tree), UnaryOp.NOT, expr(tree.getChild(0)));
		case LustreParser.PRE:
			return new UnaryExpr(loc(tree), UnaryOp.PRE, expr(tree.getChild(0)));
		default:
			return binaryExpr(tree);
		}
	}

	private static Expr binaryExpr(CommonTree tree) {
		String op = tree.getText();
		Expr left = expr(tree.getChild(0));
		Expr right = expr(tree.getChild(1));
		return new BinaryExpr(loc(tree), left, binaryOp(op), right);
	}

	private static BinaryOp binaryOp(String op) {
		if (op.equals("+")) {
			return BinaryOp.PLUS;
		} else if (op.equals("-")) {
			return BinaryOp.MINUS;
		} else if (op.equals("*")) {
			return BinaryOp.MULTIPLY;
		} else if (op.equals("/")) {
			return BinaryOp.DIVIDE;
		} else if (op.equals("div")) {
			return BinaryOp.INT_DIVIDE;
		} else if (op.equals("=")) {
			return BinaryOp.EQUAL;
		} else if (op.equals("<>")) {
			return BinaryOp.NOTEQUAL;
		} else if (op.equals(">")) {
			return BinaryOp.GREATER;
		} else if (op.equals("<")) {
			return BinaryOp.LESS;
		} else if (op.equals(">=")) {
			return BinaryOp.GREATEREQUAL;
		} else if (op.equals("<=")) {
			return BinaryOp.LESSEQUAL;
		} else if (op.equals("or")) {
			return BinaryOp.OR;
		} else if (op.equals("and")) {
			return BinaryOp.AND;
		} else if (op.equals("xor")) {
			return BinaryOp.XOR;
		} else if (op.equals("=>")) {
			return BinaryOp.IMPLIES;
		} else if (op.equals("->")) {
			return BinaryOp.ARROW;
		} else {
			throw new IllegalArgumentException("Unknown binary operator");
		}
	}

	private static List<String> properties(CommonTree tree) {
		List<String> props = new ArrayList<String>();
		if (tree == null || tree.getChildCount() == 0) {
			return props;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			props.add(child.getText());
		}
		return props;
	}

	private static CommonTree getChild(CommonTree tree, int type) {
		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			if (child.getType() == type) {
				return child;
			}
		}
		return null;
	}

	public static Location loc(CommonTree tree) {
		return new Location(tree.getLine(), tree.getCharPositionInLine());
	}
}
