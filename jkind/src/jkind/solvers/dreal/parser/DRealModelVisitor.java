// Generated from DRealModel.g4 by ANTLR 4.4
package jkind.solvers.dreal.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DRealModelParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DRealModelVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DRealModelParser#symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(@NotNull DRealModelParser.SymbolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberRangeVal}
	 * labeled alternative in {@link DRealModelParser#var_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberRangeVal(@NotNull DRealModelParser.NumberRangeValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueVal(@NotNull DRealModelParser.TrueValContext ctx);
	/**
	 * Visit a parse tree produced by {@link DRealModelParser#var_assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_assign(@NotNull DRealModelParser.Var_assignContext ctx);
	/**
	 * Visit a parse tree produced by {@link DRealModelParser#warning}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWarning(@NotNull DRealModelParser.WarningContext ctx);
	/**
	 * Visit a parse tree produced by the {@code infinityVal}
	 * labeled alternative in {@link DRealModelParser#number_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfinityVal(@NotNull DRealModelParser.InfinityValContext ctx);
	/**
	 * Visit a parse tree produced by {@link DRealModelParser#model}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModel(@NotNull DRealModelParser.ModelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code realVal}
	 * labeled alternative in {@link DRealModelParser#number_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRealVal(@NotNull DRealModelParser.RealValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolVal}
	 * labeled alternative in {@link DRealModelParser#var_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolVal(@NotNull DRealModelParser.BoolValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseVal(@NotNull DRealModelParser.FalseValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integerVal}
	 * labeled alternative in {@link DRealModelParser#number_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerVal(@NotNull DRealModelParser.IntegerValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code undefVal}
	 * labeled alternative in {@link DRealModelParser#three_val_bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUndefVal(@NotNull DRealModelParser.UndefValContext ctx);
}