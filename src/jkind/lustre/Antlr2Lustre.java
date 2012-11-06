package jkind.lustre;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class Antlr2Lustre {
	public static Program program(CommonTree tree) {
		List<TypeDef> types = types(getChild(tree, LustreParser.TYPES));
		List<Constant> constants = constants(getChild(tree, LustreParser.CONSTANTS));
		List<Node> nodes = nodes(getChild(tree, LustreParser.NODES));
		return new Program(loc(tree), types, constants, nodes);
	}

	private static List<TypeDef> types(CommonTree tree) {
		List<TypeDef> types = new ArrayList<TypeDef>();
		if (tree == null || tree.getChildCount() == 0) {
			return types;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			String id = child.getText();
			Type type = type(child.getChild(0));
			types.add(new TypeDef(loc(child), id, type));
		}
		return types;
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

	private static List<Node> nodes(CommonTree tree) {
		List<Node> nodes = new ArrayList<Node>();
		if (tree == null || tree.getChildCount() == 0) {
			return nodes;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			nodes.add(node(child));
		}

		return nodes;
	}

	public static Node node(CommonTree tree) {
		String id = tree.getText();
		List<VarDecl> inputs = varDecls(getChild(tree, LustreParser.INPUTS));
		List<VarDecl> outputs = varDecls(getChild(tree, LustreParser.OUTPUTS));
		List<VarDecl> locals = varDecls(getChild(tree, LustreParser.LOCALS));
		List<Equation> equations = equations(getChild(tree, LustreParser.EQUATIONS));
		List<String> properties = properties(getChild(tree, LustreParser.PROPERTIES));
		List<Expr> assertions = assertions(getChild(tree, LustreParser.ASSERTIONS));

		return new Node(loc(tree), id, inputs, outputs, locals, equations, properties, assertions);
	}

	private static List<VarDecl> varDecls(CommonTree tree) {
		List<VarDecl> decls = new ArrayList<VarDecl>();
		if (tree == null || tree.getChildCount() == 0) {
			return decls;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			String id = child.getText();
			Type type = type(child.getChild(0));
			decls.add(new VarDecl(loc(child), id, type));
		}
		return decls;
	}

	private static Type type(Tree tree) {
		return type((CommonTree) tree);
	}

	private static Type type(CommonTree tree) {
		String text = tree.getText();
		if (text.equals("int")) {
			if (tree.getChildCount() == 2) {
				BigInteger low = new BigInteger(tree.getChild(0).getText());
				BigInteger high = new BigInteger(tree.getChild(1).getText());
				return new SubrangeIntType(loc(tree), low, high);
			} else {
				return Type.INT;
			}
		} else if (text.equals("real")) {
			return Type.REAL;
		} else if (text.equals("bool")) {
			return Type.BOOL;
		} else {
			return new Type(text);
		}
	}

	private static List<Equation> equations(CommonTree tree) {
		List<Equation> equations = new ArrayList<Equation>();
		if (tree == null || tree.getChildCount() == 0) {
			return equations;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			List<IdExpr> lhs = lhs(child.getChild(0));
			Expr expr = expr(child.getChild(1));
			equations.add(new Equation(loc(tree), lhs, expr));
		}
		return equations;
	}

	private static List<IdExpr> lhs(Tree tree) {
		CommonTree ctree = (CommonTree) tree;
		List<IdExpr> lhs = new ArrayList<IdExpr>();

		for (Object o : ctree.getChildren()) {
			CommonTree child = (CommonTree) o;
			lhs.add(new IdExpr(loc(child), child.getText()));
		}

		return lhs;
	}

	private static Expr expr(Tree tree) {
		return expr((CommonTree) tree);
	}

	private static Expr expr(CommonTree tree) {
		switch (tree.getType()) {
		case LustreParser.BOOL:
			return new BoolExpr(loc(tree), tree.getText().equals("true"));
		case LustreParser.INT:
			return new IntExpr(loc(tree), new BigInteger(tree.getText()));
		case LustreParser.REAL:
			return new RealExpr(loc(tree), new BigDecimal(tree.getText()));
		case LustreParser.ID:
			return new IdExpr(loc(tree), tree.getText());
		case LustreParser.IF:
			return new IfThenElseExpr(loc(tree), expr(tree.getChild(0)), expr(tree.getChild(1)),
					expr(tree.getChild(2)));
		case LustreParser.NEGATE:
			return new UnaryExpr(loc(tree), UnaryOp.NEGATIVE, expr(tree.getChild(0)));
		case LustreParser.NOT:
			return new UnaryExpr(loc(tree), UnaryOp.NOT, expr(tree.getChild(0)));
		case LustreParser.PRE:
			return new UnaryExpr(loc(tree), UnaryOp.PRE, expr(tree.getChild(0)));
		case LustreParser.NODECALL:
			return nodeCall(tree);
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

	private static Expr nodeCall(CommonTree tree) {
		String node = tree.getChild(0).getText();
		List<Expr> args = new ArrayList<Expr>();

		for (int i = 1; i < tree.getChildCount(); i++) {
			CommonTree child = (CommonTree) tree.getChild(i);
			args.add(expr(child));
		}

		return new NodeCallExpr(loc(tree), node, args);
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

	private static List<Expr> assertions(CommonTree tree) {
		List<Expr> assertions = new ArrayList<Expr>();
		if (tree == null || tree.getChildCount() == 0) {
			return assertions;
		}

		for (Object o : tree.getChildren()) {
			CommonTree child = (CommonTree) o;
			assertions.add(expr(child));
		}
		return assertions;
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
