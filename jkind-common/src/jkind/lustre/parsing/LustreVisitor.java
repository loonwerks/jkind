// Generated from /Users/sohahussein/git/jkind/jkind-common/src/jkind/lustre/parsing/Lustre.g4 by ANTLR 4.7.2
package jkind.lustre.parsing;
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
	 * Visit a parse tree produced by {@link LustreParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(LustreParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#typedef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedef(LustreParser.TypedefContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(LustreParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#node}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNode(LustreParser.NodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#repairnode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepairnode(LustreParser.RepairnodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(LustreParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#varDeclList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclList(LustreParser.VarDeclListContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#varDeclGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclGroup(LustreParser.VarDeclGroupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plainType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlainType(LustreParser.PlainTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordType(LustreParser.RecordTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code enumType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumType(LustreParser.EnumTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(LustreParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code realType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealType(LustreParser.RealTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subrangeType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubrangeType(LustreParser.SubrangeTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(LustreParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code userType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserType(LustreParser.UserTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(LustreParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#bound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBound(LustreParser.BoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(LustreParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#realizabilityInputs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealizabilityInputs(LustreParser.RealizabilityInputsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#ivc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIvc(LustreParser.IvcContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(LustreParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#assertion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertion(LustreParser.AssertionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquation(LustreParser.EquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link LustreParser#lhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLhs(LustreParser.LhsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordExpr(LustreParser.RecordExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntExpr(LustreParser.IntExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(LustreParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpr(LustreParser.CastExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code realExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealExpr(LustreParser.RealExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifThenElseExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseExpr(LustreParser.IfThenElseExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(LustreParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code repairExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepairExpr(LustreParser.RepairExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code preExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreExpr(LustreParser.PreExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordAccessExpr(LustreParser.RecordAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpr(LustreParser.NegateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condactExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondactExpr(LustreParser.CondactExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(LustreParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpr(LustreParser.ArrayAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayUpdateExpr(LustreParser.ArrayUpdateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(LustreParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(LustreParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tupleExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleExpr(LustreParser.TupleExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordUpdateExpr(LustreParser.RecordUpdateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(LustreParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code baseEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseEID(LustreParser.BaseEIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayEID(LustreParser.ArrayEIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code recordEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordEID(LustreParser.RecordEIDContext ctx);
}