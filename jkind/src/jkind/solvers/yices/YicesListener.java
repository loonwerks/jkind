// Generated from Yices.g4 by ANTLR 4.0
package jkind.solvers.yices;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface YicesListener extends ParseTreeListener {
	void enterResult(YicesParser.ResultContext ctx);
	void exitResult(YicesParser.ResultContext ctx);

	void enterModel(YicesParser.ModelContext ctx);
	void exitModel(YicesParser.ModelContext ctx);

	void enterNumeric(YicesParser.NumericContext ctx);
	void exitNumeric(YicesParser.NumericContext ctx);

	void enterInteger(YicesParser.IntegerContext ctx);
	void exitInteger(YicesParser.IntegerContext ctx);

	void enterAlias(YicesParser.AliasContext ctx);
	void exitAlias(YicesParser.AliasContext ctx);

	void enterCost(YicesParser.CostContext ctx);
	void exitCost(YicesParser.CostContext ctx);

	void enterSatResult(YicesParser.SatResultContext ctx);
	void exitSatResult(YicesParser.SatResultContext ctx);

	void enterFunction(YicesParser.FunctionContext ctx);
	void exitFunction(YicesParser.FunctionContext ctx);

	void enterUnsatCore(YicesParser.UnsatCoreContext ctx);
	void exitUnsatCore(YicesParser.UnsatCoreContext ctx);

	void enterUnsatAssertions(YicesParser.UnsatAssertionsContext ctx);
	void exitUnsatAssertions(YicesParser.UnsatAssertionsContext ctx);

	void enterValue(YicesParser.ValueContext ctx);
	void exitValue(YicesParser.ValueContext ctx);

	void enterUnsatResult(YicesParser.UnsatResultContext ctx);
	void exitUnsatResult(YicesParser.UnsatResultContext ctx);

	void enterVariable(YicesParser.VariableContext ctx);
	void exitVariable(YicesParser.VariableContext ctx);

	void enterPredefined(YicesParser.PredefinedContext ctx);
	void exitPredefined(YicesParser.PredefinedContext ctx);
}