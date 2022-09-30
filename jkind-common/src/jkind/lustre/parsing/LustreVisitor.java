// Generated from Lustre.g4 by ANTLR 4.4
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
	 * Visit a parse tree produced by {@link LustreParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(@NotNull LustreParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code realType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealType(@NotNull LustreParser.RealTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code baseEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseEID(@NotNull LustreParser.BaseEIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpr(@NotNull LustreParser.CastExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code realExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealExpr(@NotNull LustreParser.RealExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code enumType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumType(@NotNull LustreParser.EnumTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifThenElseExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseExpr(@NotNull LustreParser.IfThenElseExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayEID(@NotNull LustreParser.ArrayEIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(@NotNull LustreParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by the {@code preExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreExpr(@NotNull LustreParser.PreExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull LustreParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#varDeclList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclList(@NotNull LustreParser.VarDeclListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(@NotNull LustreParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpr(@NotNull LustreParser.NegateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condactExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondactExpr(@NotNull LustreParser.CondactExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpr(@NotNull LustreParser.ArrayAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(@NotNull LustreParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#ivc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIvc(@NotNull LustreParser.IvcContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(@NotNull LustreParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayUpdateExpr(@NotNull LustreParser.ArrayUpdateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordEID(@NotNull LustreParser.RecordEIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#assertion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertion(@NotNull LustreParser.AssertionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(@NotNull LustreParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordExpr(@NotNull LustreParser.RecordExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(@NotNull LustreParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntExpr(@NotNull LustreParser.IntExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(@NotNull LustreParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordType(@NotNull LustreParser.RecordTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(@NotNull LustreParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#bound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBound(@NotNull LustreParser.BoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquation(@NotNull LustreParser.EquationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(@NotNull LustreParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plainType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlainType(@NotNull LustreParser.PlainTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#typedef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedef(@NotNull LustreParser.TypedefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordAccessExpr(@NotNull LustreParser.RecordAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#node}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNode(@NotNull LustreParser.NodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#realizabilityInputs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealizabilityInputs(@NotNull LustreParser.RealizabilityInputsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(@NotNull LustreParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subrangeType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubrangeType(@NotNull LustreParser.SubrangeTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#lhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs(@NotNull LustreParser.LhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#varDeclGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclGroup(@NotNull LustreParser.VarDeclGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code userType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserType(@NotNull LustreParser.UserTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(@NotNull LustreParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tupleExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleExpr(@NotNull LustreParser.TupleExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordUpdateExpr(@NotNull LustreParser.RecordUpdateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(@NotNull LustreParser.IdExprContext ctx);
}