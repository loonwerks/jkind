// Generated from DRealModel.g4 by ANTLR 4.4
package jkind.solvers.dreal.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DRealModelParser}.
 */
public interface DRealModelListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DRealModelParser#symbol}.
	 * @param ctx the parse tree
	 */
	void enterSymbol(@NotNull DRealModelParser.SymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link DRealModelParser#symbol}.
	 * @param ctx the parse tree
	 */
	void exitSymbol(@NotNull DRealModelParser.SymbolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberRangeVal}
	 * labeled alternative in {@link DRealModelParser#var_value}.
	 * @param ctx the parse tree
	 */
	void enterNumberRangeVal(@NotNull DRealModelParser.NumberRangeValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberRangeVal}
	 * labeled alternative in {@link DRealModelParser#var_value}.
	 * @param ctx the parse tree
	 */
	void exitNumberRangeVal(@NotNull DRealModelParser.NumberRangeValContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 */
	void enterTrueVal(@NotNull DRealModelParser.TrueValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 */
	void exitTrueVal(@NotNull DRealModelParser.TrueValContext ctx);
	/**
	 * Enter a parse tree produced by {@link DRealModelParser#var_assign}.
	 * @param ctx the parse tree
	 */
	void enterVar_assign(@NotNull DRealModelParser.Var_assignContext ctx);
	/**
	 * Exit a parse tree produced by {@link DRealModelParser#var_assign}.
	 * @param ctx the parse tree
	 */
	void exitVar_assign(@NotNull DRealModelParser.Var_assignContext ctx);
	/**
	 * Enter a parse tree produced by {@link DRealModelParser#warning}.
	 * @param ctx the parse tree
	 */
	void enterWarning(@NotNull DRealModelParser.WarningContext ctx);
	/**
	 * Exit a parse tree produced by {@link DRealModelParser#warning}.
	 * @param ctx the parse tree
	 */
	void exitWarning(@NotNull DRealModelParser.WarningContext ctx);
	/**
	 * Enter a parse tree produced by the {@code infinityVal}
	 * labeled alternative in {@link DRealModelParser#number_value}.
	 * @param ctx the parse tree
	 */
	void enterInfinityVal(@NotNull DRealModelParser.InfinityValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code infinityVal}
	 * labeled alternative in {@link DRealModelParser#number_value}.
	 * @param ctx the parse tree
	 */
	void exitInfinityVal(@NotNull DRealModelParser.InfinityValContext ctx);
	/**
	 * Enter a parse tree produced by {@link DRealModelParser#model}.
	 * @param ctx the parse tree
	 */
	void enterModel(@NotNull DRealModelParser.ModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link DRealModelParser#model}.
	 * @param ctx the parse tree
	 */
	void exitModel(@NotNull DRealModelParser.ModelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code realVal}
	 * labeled alternative in {@link DRealModelParser#number_value}.
	 * @param ctx the parse tree
	 */
	void enterRealVal(@NotNull DRealModelParser.RealValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code realVal}
	 * labeled alternative in {@link DRealModelParser#number_value}.
	 * @param ctx the parse tree
	 */
	void exitRealVal(@NotNull DRealModelParser.RealValContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolVal}
	 * labeled alternative in {@link DRealModelParser#var_value}.
	 * @param ctx the parse tree
	 */
	void enterBoolVal(@NotNull DRealModelParser.BoolValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolVal}
	 * labeled alternative in {@link DRealModelParser#var_value}.
	 * @param ctx the parse tree
	 */
	void exitBoolVal(@NotNull DRealModelParser.BoolValContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 */
	void enterFalseVal(@NotNull DRealModelParser.FalseValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 */
	void exitFalseVal(@NotNull DRealModelParser.FalseValContext ctx);
	/**
	 * Enter a parse tree produced by the {@code undefVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 */
	void enterUndefVal(@NotNull DRealModelParser.UndefValContext ctx);
	/**
	 * Exit a parse tree produced by the {@code undefVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 */
	void exitUndefVal(@NotNull DRealModelParser.UndefValContext ctx);
}