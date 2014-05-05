// Generated from Lustre.g4 by ANTLR 4.2
package jkind.lustre.parsing;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LustreParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LustreVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LustreParser#recordAccessExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordAccessExpr(@NotNull LustreParser.RecordAccessExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#condactExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondactExpr(@NotNull LustreParser.CondactExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#plainType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlainType(@NotNull LustreParser.PlainTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(@NotNull LustreParser.ArrayTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#arrayUpdateExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayUpdateExpr(@NotNull LustreParser.ArrayUpdateExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#castExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpr(@NotNull LustreParser.CastExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#ifThenElseExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseExpr(@NotNull LustreParser.IfThenElseExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#idExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(@NotNull LustreParser.IdExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#intType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(@NotNull LustreParser.IntTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(@NotNull LustreParser.BoolExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#arrayExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(@NotNull LustreParser.ArrayExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#tupleExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleExpr(@NotNull LustreParser.TupleExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#userType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserType(@NotNull LustreParser.UserTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#lhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs(@NotNull LustreParser.LhsContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#subrangeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubrangeType(@NotNull LustreParser.SubrangeTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquation(@NotNull LustreParser.EquationContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#enumType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumType(@NotNull LustreParser.EnumTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#preExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreExpr(@NotNull LustreParser.PreExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#negateExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpr(@NotNull LustreParser.NegateExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#realType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealType(@NotNull LustreParser.RealTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#recordType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordType(@NotNull LustreParser.RecordTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#arrayAccessExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpr(@NotNull LustreParser.ArrayAccessExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#realExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealExpr(@NotNull LustreParser.RealExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#notExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(@NotNull LustreParser.NotExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#recordUpdateExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordUpdateExpr(@NotNull LustreParser.RecordUpdateExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#nodeCallExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeCallExpr(@NotNull LustreParser.NodeCallExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(@NotNull LustreParser.ConstantContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#varDeclList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclList(@NotNull LustreParser.VarDeclListContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(@NotNull LustreParser.PropertyContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#binaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(@NotNull LustreParser.BinaryExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#assertion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertion(@NotNull LustreParser.AssertionContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#typedef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedef(@NotNull LustreParser.TypedefContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(@NotNull LustreParser.MainContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#intExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntExpr(@NotNull LustreParser.IntExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#node}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNode(@NotNull LustreParser.NodeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#boolType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(@NotNull LustreParser.BoolTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#varDeclGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclGroup(@NotNull LustreParser.VarDeclGroupContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#recordExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordExpr(@NotNull LustreParser.RecordExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull LustreParser.ProgramContext ctx);

	/**
	 * Visit a parse tree produced by {@link LustreParser#bound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBound(@NotNull LustreParser.BoundContext ctx);
}