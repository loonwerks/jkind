// Generated from Lustre.g4 by ANTLR 4.0
package jkind.lustre.parsing;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class LustreBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements LustreVisitor<T> {
	@Override public T visitIdExpr(LustreParser.IdExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitIfThenElseExpr(LustreParser.IfThenElseExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitIntType(LustreParser.IntTypeContext ctx) { return visitChildren(ctx); }

	@Override public T visitBoolExpr(LustreParser.BoolExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitUserType(LustreParser.UserTypeContext ctx) { return visitChildren(ctx); }

	@Override public T visitLhs(LustreParser.LhsContext ctx) { return visitChildren(ctx); }

	@Override public T visitSubrangeType(LustreParser.SubrangeTypeContext ctx) { return visitChildren(ctx); }

	@Override public T visitEquation(LustreParser.EquationContext ctx) { return visitChildren(ctx); }

	@Override public T visitPreExpr(LustreParser.PreExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitNegateExpr(LustreParser.NegateExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitRealType(LustreParser.RealTypeContext ctx) { return visitChildren(ctx); }

	@Override public T visitRealExpr(LustreParser.RealExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitNotExpr(LustreParser.NotExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitNodeCallExpr(LustreParser.NodeCallExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitConstant(LustreParser.ConstantContext ctx) { return visitChildren(ctx); }

	@Override public T visitVarDeclList(LustreParser.VarDeclListContext ctx) { return visitChildren(ctx); }

	@Override public T visitProperty(LustreParser.PropertyContext ctx) { return visitChildren(ctx); }

	@Override public T visitBinaryExpr(LustreParser.BinaryExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitAssertion(LustreParser.AssertionContext ctx) { return visitChildren(ctx); }

	@Override public T visitTypedef(LustreParser.TypedefContext ctx) { return visitChildren(ctx); }

	@Override public T visitIntExpr(LustreParser.IntExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitNode(LustreParser.NodeContext ctx) { return visitChildren(ctx); }

	@Override public T visitBoolType(LustreParser.BoolTypeContext ctx) { return visitChildren(ctx); }

	@Override public T visitVarDeclGroup(LustreParser.VarDeclGroupContext ctx) { return visitChildren(ctx); }

	@Override public T visitProgram(LustreParser.ProgramContext ctx) { return visitChildren(ctx); }

	@Override public T visitParenExpr(LustreParser.ParenExprContext ctx) { return visitChildren(ctx); }

	@Override public T visitBound(LustreParser.BoundContext ctx) { return visitChildren(ctx); }
}