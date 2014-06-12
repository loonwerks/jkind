// Generated from Yices2.g4 by ANTLR 4.2
package jkind.solvers.yices2;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Yices2Parser}.
 */
public interface Yices2Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Yices2Parser#positiveInteger}.
	 * @param ctx the parse tree
	 */
	void enterPositiveInteger(@NotNull Yices2Parser.PositiveIntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#positiveInteger}.
	 * @param ctx the parse tree
	 */
	void exitPositiveInteger(@NotNull Yices2Parser.PositiveIntegerContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#quotient}.
	 * @param ctx the parse tree
	 */
	void enterQuotient(@NotNull Yices2Parser.QuotientContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#quotient}.
	 * @param ctx the parse tree
	 */
	void exitQuotient(@NotNull Yices2Parser.QuotientContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#integerNumeric}.
	 * @param ctx the parse tree
	 */
	void enterIntegerNumeric(@NotNull Yices2Parser.IntegerNumericContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#integerNumeric}.
	 * @param ctx the parse tree
	 */
	void exitIntegerNumeric(@NotNull Yices2Parser.IntegerNumericContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#model}.
	 * @param ctx the parse tree
	 */
	void enterModel(@NotNull Yices2Parser.ModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#model}.
	 * @param ctx the parse tree
	 */
	void exitModel(@NotNull Yices2Parser.ModelContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#negativeInteger}.
	 * @param ctx the parse tree
	 */
	void enterNegativeInteger(@NotNull Yices2Parser.NegativeIntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#negativeInteger}.
	 * @param ctx the parse tree
	 */
	void exitNegativeInteger(@NotNull Yices2Parser.NegativeIntegerContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(@NotNull Yices2Parser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(@NotNull Yices2Parser.FunctionTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#alias}.
	 * @param ctx the parse tree
	 */
	void enterAlias(@NotNull Yices2Parser.AliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#alias}.
	 * @param ctx the parse tree
	 */
	void exitAlias(@NotNull Yices2Parser.AliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(@NotNull Yices2Parser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(@NotNull Yices2Parser.ValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#quotientNumeric}.
	 * @param ctx the parse tree
	 */
	void enterQuotientNumeric(@NotNull Yices2Parser.QuotientNumericContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#quotientNumeric}.
	 * @param ctx the parse tree
	 */
	void exitQuotientNumeric(@NotNull Yices2Parser.QuotientNumericContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull Yices2Parser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull Yices2Parser.TypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(@NotNull Yices2Parser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(@NotNull Yices2Parser.VariableContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#functionValue}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValue(@NotNull Yices2Parser.FunctionValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#functionValue}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValue(@NotNull Yices2Parser.FunctionValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link Yices2Parser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(@NotNull Yices2Parser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Yices2Parser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(@NotNull Yices2Parser.FunctionContext ctx);
}