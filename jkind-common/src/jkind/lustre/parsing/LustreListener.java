// Generated from /Users/sohahussein/git/jkind/jkind-common/src/jkind/lustre/parsing/Lustre.g4 by ANTLR 4.7.2
package jkind.lustre.parsing;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LustreParser}.
 */
public interface LustreListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LustreParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(LustreParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(LustreParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#typedef}.
	 * @param ctx the parse tree
	 */
	void enterTypedef(LustreParser.TypedefContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#typedef}.
	 * @param ctx the parse tree
	 */
	void exitTypedef(LustreParser.TypedefContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(LustreParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(LustreParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#node}.
	 * @param ctx the parse tree
	 */
	void enterNode(LustreParser.NodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#node}.
	 * @param ctx the parse tree
	 */
	void exitNode(LustreParser.NodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#repairnode}.
	 * @param ctx the parse tree
	 */
	void enterRepairnode(LustreParser.RepairnodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#repairnode}.
	 * @param ctx the parse tree
	 */
	void exitRepairnode(LustreParser.RepairnodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(LustreParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(LustreParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#varDeclList}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclList(LustreParser.VarDeclListContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#varDeclList}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclList(LustreParser.VarDeclListContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#varDeclGroup}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclGroup(LustreParser.VarDeclGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#varDeclGroup}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclGroup(LustreParser.VarDeclGroupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code plainType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 */
	void enterPlainType(LustreParser.PlainTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code plainType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 */
	void exitPlainType(LustreParser.PlainTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code recordType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 */
	void enterRecordType(LustreParser.RecordTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code recordType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 */
	void exitRecordType(LustreParser.RecordTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code enumType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 */
	void enterEnumType(LustreParser.EnumTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code enumType}
	 * labeled alternative in {@link LustreParser#topLevelType}.
	 * @param ctx the parse tree
	 */
	void exitEnumType(LustreParser.EnumTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(LustreParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(LustreParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code realType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void enterRealType(LustreParser.RealTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code realType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void exitRealType(LustreParser.RealTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subrangeType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void enterSubrangeType(LustreParser.SubrangeTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subrangeType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void exitSubrangeType(LustreParser.SubrangeTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(LustreParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(LustreParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code userType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void enterUserType(LustreParser.UserTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code userType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void exitUserType(LustreParser.UserTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(LustreParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link LustreParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(LustreParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#bound}.
	 * @param ctx the parse tree
	 */
	void enterBound(LustreParser.BoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#bound}.
	 * @param ctx the parse tree
	 */
	void exitBound(LustreParser.BoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(LustreParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(LustreParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#realizabilityInputs}.
	 * @param ctx the parse tree
	 */
	void enterRealizabilityInputs(LustreParser.RealizabilityInputsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#realizabilityInputs}.
	 * @param ctx the parse tree
	 */
	void exitRealizabilityInputs(LustreParser.RealizabilityInputsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#ivc}.
	 * @param ctx the parse tree
	 */
	void enterIvc(LustreParser.IvcContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#ivc}.
	 * @param ctx the parse tree
	 */
	void exitIvc(LustreParser.IvcContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(LustreParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(LustreParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#assertion}.
	 * @param ctx the parse tree
	 */
	void enterAssertion(LustreParser.AssertionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#assertion}.
	 * @param ctx the parse tree
	 */
	void exitAssertion(LustreParser.AssertionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterEquation(LustreParser.EquationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitEquation(LustreParser.EquationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LustreParser#lhs}.
	 * @param ctx the parse tree
	 */
	void enterLhs(LustreParser.LhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LustreParser#lhs}.
	 * @param ctx the parse tree
	 */
	void exitLhs(LustreParser.LhsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code recordExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRecordExpr(LustreParser.RecordExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code recordExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRecordExpr(LustreParser.RecordExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntExpr(LustreParser.IntExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntExpr(LustreParser.IntExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(LustreParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(LustreParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCastExpr(LustreParser.CastExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code castExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCastExpr(LustreParser.CastExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code realExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRealExpr(LustreParser.RealExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code realExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRealExpr(LustreParser.RealExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifThenElseExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseExpr(LustreParser.IfThenElseExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifThenElseExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseExpr(LustreParser.IfThenElseExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(LustreParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(LustreParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code repairExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRepairExpr(LustreParser.RepairExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code repairExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRepairExpr(LustreParser.RepairExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code preExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPreExpr(LustreParser.PreExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code preExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPreExpr(LustreParser.PreExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code recordAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRecordAccessExpr(LustreParser.RecordAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code recordAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRecordAccessExpr(LustreParser.RecordAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNegateExpr(LustreParser.NegateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNegateExpr(LustreParser.NegateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condactExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCondactExpr(LustreParser.CondactExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condactExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCondactExpr(LustreParser.CondactExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(LustreParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(LustreParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpr(LustreParser.ArrayAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccessExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpr(LustreParser.ArrayAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayUpdateExpr(LustreParser.ArrayUpdateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayUpdateExpr(LustreParser.ArrayUpdateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(LustreParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(LustreParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(LustreParser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(LustreParser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tupleExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTupleExpr(LustreParser.TupleExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tupleExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTupleExpr(LustreParser.TupleExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code recordUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRecordUpdateExpr(LustreParser.RecordUpdateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code recordUpdateExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRecordUpdateExpr(LustreParser.RecordUpdateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(LustreParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link LustreParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(LustreParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code baseEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 */
	void enterBaseEID(LustreParser.BaseEIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code baseEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 */
	void exitBaseEID(LustreParser.BaseEIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 */
	void enterArrayEID(LustreParser.ArrayEIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 */
	void exitArrayEID(LustreParser.ArrayEIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code recordEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 */
	void enterRecordEID(LustreParser.RecordEIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code recordEID}
	 * labeled alternative in {@link LustreParser#eID}.
	 * @param ctx the parse tree
	 */
	void exitRecordEID(LustreParser.RecordEIDContext ctx);
}