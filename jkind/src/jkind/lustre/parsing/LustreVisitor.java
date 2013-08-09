// Generated from Lustre.g4 by ANTLR 4.0
package jkind.lustre.parsing;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

public interface LustreVisitor<T> extends ParseTreeVisitor<T> {
	T visitIfThenElseExpr(LustreParser.IfThenElseExprContext ctx);

	T visitIdExpr(LustreParser.IdExprContext ctx);

	T visitIntType(LustreParser.IntTypeContext ctx);

	T visitBoolExpr(LustreParser.BoolExprContext ctx);

	T visitUserType(LustreParser.UserTypeContext ctx);

	T visitLhs(LustreParser.LhsContext ctx);

	T visitSubrangeType(LustreParser.SubrangeTypeContext ctx);

	T visitEquation(LustreParser.EquationContext ctx);

	T visitPreExpr(LustreParser.PreExprContext ctx);

	T visitNegateExpr(LustreParser.NegateExprContext ctx);

	T visitRealType(LustreParser.RealTypeContext ctx);

	T visitRecordType(LustreParser.RecordTypeContext ctx);

	T visitRealExpr(LustreParser.RealExprContext ctx);

	T visitNotExpr(LustreParser.NotExprContext ctx);

	T visitNodeCallExpr(LustreParser.NodeCallExprContext ctx);

	T visitProjectionExpr(LustreParser.ProjectionExprContext ctx);

	T visitConstant(LustreParser.ConstantContext ctx);

	T visitVarDeclList(LustreParser.VarDeclListContext ctx);

	T visitProperty(LustreParser.PropertyContext ctx);

	T visitBinaryExpr(LustreParser.BinaryExprContext ctx);

	T visitAssertion(LustreParser.AssertionContext ctx);

	T visitTypedef(LustreParser.TypedefContext ctx);

	T visitIntExpr(LustreParser.IntExprContext ctx);

	T visitNode(LustreParser.NodeContext ctx);

	T visitBoolType(LustreParser.BoolTypeContext ctx);

	T visitVarDeclGroup(LustreParser.VarDeclGroupContext ctx);

	T visitRecordExpr(LustreParser.RecordExprContext ctx);

	T visitProgram(LustreParser.ProgramContext ctx);

	T visitParenExpr(LustreParser.ParenExprContext ctx);

	T visitBound(LustreParser.BoundContext ctx);
}