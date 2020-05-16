// Generated from Yices.g4 by ANTLR 4.4
package jkind.solvers.yices;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link YicesParser}.
 */
public interface YicesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link YicesParser#unsatResult}.
	 * @param ctx the parse tree
	 */
	void enterUnsatResult(@NotNull YicesParser.UnsatResultContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#unsatResult}.
	 * @param ctx the parse tree
	 */
	void exitUnsatResult(@NotNull YicesParser.UnsatResultContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#unsatAssertions}.
	 * @param ctx the parse tree
	 */
	void enterUnsatAssertions(@NotNull YicesParser.UnsatAssertionsContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#unsatAssertions}.
	 * @param ctx the parse tree
	 */
	void exitUnsatAssertions(@NotNull YicesParser.UnsatAssertionsContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#cost}.
	 * @param ctx the parse tree
	 */
	void enterCost(@NotNull YicesParser.CostContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#cost}.
	 * @param ctx the parse tree
	 */
	void exitCost(@NotNull YicesParser.CostContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#numeric}.
	 * @param ctx the parse tree
	 */
	void enterNumeric(@NotNull YicesParser.NumericContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#numeric}.
	 * @param ctx the parse tree
	 */
	void exitNumeric(@NotNull YicesParser.NumericContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(@NotNull YicesParser.IntegerContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(@NotNull YicesParser.IntegerContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#predefined}.
	 * @param ctx the parse tree
	 */
	void enterPredefined(@NotNull YicesParser.PredefinedContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#predefined}.
	 * @param ctx the parse tree
	 */
	void exitPredefined(@NotNull YicesParser.PredefinedContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#result}.
	 * @param ctx the parse tree
	 */
	void enterResult(@NotNull YicesParser.ResultContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#result}.
	 * @param ctx the parse tree
	 */
	void exitResult(@NotNull YicesParser.ResultContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#unsatCore}.
	 * @param ctx the parse tree
	 */
	void enterUnsatCore(@NotNull YicesParser.UnsatCoreContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#unsatCore}.
	 * @param ctx the parse tree
	 */
	void exitUnsatCore(@NotNull YicesParser.UnsatCoreContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#satResult}.
	 * @param ctx the parse tree
	 */
	void enterSatResult(@NotNull YicesParser.SatResultContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#satResult}.
	 * @param ctx the parse tree
	 */
	void exitSatResult(@NotNull YicesParser.SatResultContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(@NotNull YicesParser.FunctionContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(@NotNull YicesParser.FunctionContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(@NotNull YicesParser.VariableContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(@NotNull YicesParser.VariableContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#alias}.
	 * @param ctx the parse tree
	 */
	void enterAlias(@NotNull YicesParser.AliasContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#alias}.
	 * @param ctx the parse tree
	 */
	void exitAlias(@NotNull YicesParser.AliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#model}.
	 * @param ctx the parse tree
	 */
	void enterModel(@NotNull YicesParser.ModelContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#model}.
	 * @param ctx the parse tree
	 */
	void exitModel(@NotNull YicesParser.ModelContext ctx);

	/**
	 * Enter a parse tree produced by {@link YicesParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(@NotNull YicesParser.ValueContext ctx);

	/**
	 * Exit a parse tree produced by {@link YicesParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(@NotNull YicesParser.ValueContext ctx);
}