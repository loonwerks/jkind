package jkind.lustre.parsing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Constant;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.parsing.LustreParser.AssertionContext;
import jkind.lustre.parsing.LustreParser.BinaryExprContext;
import jkind.lustre.parsing.LustreParser.BoolExprContext;
import jkind.lustre.parsing.LustreParser.BoolTypeContext;
import jkind.lustre.parsing.LustreParser.ConstantContext;
import jkind.lustre.parsing.LustreParser.EquationContext;
import jkind.lustre.parsing.LustreParser.ExprContext;
import jkind.lustre.parsing.LustreParser.IdExprContext;
import jkind.lustre.parsing.LustreParser.IfThenElseExprContext;
import jkind.lustre.parsing.LustreParser.IntExprContext;
import jkind.lustre.parsing.LustreParser.IntTypeContext;
import jkind.lustre.parsing.LustreParser.LhsContext;
import jkind.lustre.parsing.LustreParser.NegateExprContext;
import jkind.lustre.parsing.LustreParser.NodeCallExprContext;
import jkind.lustre.parsing.LustreParser.NodeContext;
import jkind.lustre.parsing.LustreParser.NotExprContext;
import jkind.lustre.parsing.LustreParser.ParenExprContext;
import jkind.lustre.parsing.LustreParser.PreExprContext;
import jkind.lustre.parsing.LustreParser.ProgramContext;
import jkind.lustre.parsing.LustreParser.PropertyContext;
import jkind.lustre.parsing.LustreParser.RealExprContext;
import jkind.lustre.parsing.LustreParser.RealTypeContext;
import jkind.lustre.parsing.LustreParser.SubrangeTypeContext;
import jkind.lustre.parsing.LustreParser.TypeContext;
import jkind.lustre.parsing.LustreParser.TypedefContext;
import jkind.lustre.parsing.LustreParser.UserTypeContext;
import jkind.lustre.parsing.LustreParser.VarDeclGroupContext;
import jkind.lustre.parsing.LustreParser.VarDeclListContext;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class LustreToAstVisitor extends LustreBaseVisitor<Object> {
	public Program program(ProgramContext ctx) {
		List<TypeDef> types = types(ctx.typedef());
		List<Constant> constants = constants(ctx.constant());
		List<Node> nodes = nodes(ctx.node());
		return new Program(loc(ctx), types, constants, nodes);
	}

	private List<TypeDef> types(List<TypedefContext> ctxs) {
		List<TypeDef> types = new ArrayList<TypeDef>();
		for (TypedefContext ctx : ctxs) {
			String id = ctx.ID().getText();
			Type type = type(ctx.type());
			types.add(new TypeDef(loc(ctx), id, type));
		}
		return types;
	}

	private List<Constant> constants(List<ConstantContext> ctxs) {
		List<Constant> constants = new ArrayList<Constant>();
		for (ConstantContext ctx : ctxs) {
			String id = ctx.ID().getText();
			Type type = ctx.type() == null ? null : type(ctx.type());
			Expr expr = expr(ctx.expr());
			constants.add(new Constant(loc(ctx), id, type, expr));
		}
		return constants;
	}

	private List<Node> nodes(List<NodeContext> ctxs) {
		List<Node> nodes = new ArrayList<Node>();
		for (NodeContext ctx : ctxs) {
			nodes.add(node(ctx));
		}
		return nodes;
	}

	private Node node(NodeContext ctx) {
		String id = ctx.ID().getText();
		List<VarDecl> inputs = varDecls(ctx.input);
		List<VarDecl> outputs = varDecls(ctx.output);
		List<VarDecl> locals = varDecls(ctx.local);
		List<Equation> equations = equations(ctx.equation());
		List<String> properties = properties(ctx.property());
		List<Expr> assertions = assertions(ctx.assertion());
		return new Node(loc(ctx), id, inputs, outputs, locals, equations, properties, assertions);
	}

	private List<VarDecl> varDecls(VarDeclListContext listCtx) {
		List<VarDecl> decls = new ArrayList<VarDecl>();
		if (listCtx == null) {
			return decls;
		}
		
		for (VarDeclGroupContext groupCtx : listCtx.varDeclGroup()) {
			Type type = type(groupCtx.type());
			for (TerminalNode id : groupCtx.ID()) {
				decls.add(new VarDecl(loc(id), id.getText(), type));
			}
		}
		return decls;
	}

	private List<Equation> equations(List<EquationContext> ctxs) {
		List<Equation> equations = new ArrayList<Equation>();
		for (EquationContext ctx : ctxs) {
			List<IdExpr> lhs = lhs(ctx.lhs());
			Expr expr = expr(ctx.expr());
			equations.add(new Equation(loc(ctx), lhs, expr));
		}
		return equations;
	}

	private List<IdExpr> lhs(LhsContext ctx) {
		List<IdExpr> lhs = new ArrayList<IdExpr>();
		for (TerminalNode node : ctx.ID()) {
			lhs.add(new IdExpr(loc(node), node.getText()));
		}
		return lhs;
	}

	private List<String> properties(List<PropertyContext> ctxs) {
		List<String> props = new ArrayList<String>();
		for (PropertyContext ctx : ctxs) {
			props.add(ctx.ID().getText());
		}
		return props;
	}

	private List<Expr> assertions(List<AssertionContext> ctxs) {
		List<Expr> assertions = new ArrayList<Expr>();
		for (AssertionContext ctx : ctxs) {
			assertions.add(expr(ctx.expr()));
		}
		return assertions;
	}

	private Type type(TypeContext ctx) {
		return (Type) ctx.accept(this);
	}

	@Override
	public Type visitIntType(IntTypeContext ctx) {
		return Type.INT;
	}

	@Override
	public Type visitSubrangeType(SubrangeTypeContext ctx) {
		BigInteger low = new BigInteger(ctx.bound(0).getText());
		BigInteger high = new BigInteger(ctx.bound(1).getText());
		return new SubrangeIntType(loc(ctx), low, high);
	}

	@Override
	public Type visitBoolType(BoolTypeContext ctx) {
		return Type.BOOL;
	}

	@Override
	public Type visitRealType(RealTypeContext ctx) {
		return Type.REAL;
	}

	@Override
	public Type visitUserType(UserTypeContext ctx) {
		return new Type(ctx.ID().getText());
	}

	private Expr expr(ExprContext ctx) {
		return (Expr) ctx.accept(this);
	}

	@Override
	public Expr visitIdExpr(IdExprContext ctx) {
		return new IdExpr(loc(ctx), ctx.ID().getText());
	}

	@Override
	public Expr visitIntExpr(IntExprContext ctx) {
		return new IntExpr(loc(ctx), new BigInteger(ctx.INT().getText()));
	}

	@Override
	public Expr visitRealExpr(RealExprContext ctx) {
		return new RealExpr(loc(ctx), new BigDecimal(ctx.REAL().getText()));
	}

	@Override
	public Expr visitBoolExpr(BoolExprContext ctx) {
		return new BoolExpr(loc(ctx), ctx.BOOL().getText().equals("true"));
	}

	@Override
	public Expr visitNodeCallExpr(NodeCallExprContext ctx) {
		String node = ctx.ID().getText();
		List<Expr> args = new ArrayList<Expr>();
		for (ExprContext arg : ctx.expr()) {
			args.add(expr(arg));
		}
		return new NodeCallExpr(loc(ctx), node, args);
	}

	@Override
	public Expr visitPreExpr(PreExprContext ctx) {
		return new UnaryExpr(loc(ctx), UnaryOp.PRE, expr(ctx.expr()));
	}

	@Override
	public Expr visitNotExpr(NotExprContext ctx) {
		return new UnaryExpr(loc(ctx), UnaryOp.NOT, expr(ctx.expr()));
	}

	@Override
	public Expr visitNegateExpr(NegateExprContext ctx) {
		return new UnaryExpr(loc(ctx), UnaryOp.NEGATIVE, expr(ctx.expr()));
	}

	@Override
	public Expr visitBinaryExpr(BinaryExprContext ctx) {
		String op = ctx.op.getText();
		Expr left = expr(ctx.expr(0));
		Expr right = expr(ctx.expr(1));
		return new BinaryExpr(loc(ctx.op), left, binaryOp(op), right);
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
	
	@Override
	public Expr visitIfThenElseExpr(IfThenElseExprContext ctx) {
		return new IfThenElseExpr(loc(ctx), expr(ctx.expr(0)), expr(ctx.expr(1)), expr(ctx.expr(2)));
	}

	@Override
	public Expr visitParenExpr(ParenExprContext ctx) {
		return expr(ctx.expr());
	}

	private static Location loc(ParserRuleContext ctx) {
		return loc(ctx.getStart());
	}

	private static Location loc(TerminalNode node) {
		return loc(node.getSymbol());
	}

	private static Location loc(Token token) {
		return new Location(token.getLine(), token.getCharPositionInLine());
	}
}
