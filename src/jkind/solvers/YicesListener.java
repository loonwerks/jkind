// Generated from Yices.g4 by ANTLR 4.0
package jkind.solvers;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface YicesListener extends ParseTreeListener {
	void enterSolverResult(YicesParser.SolverResultContext ctx);
	void exitSolverResult(YicesParser.SolverResultContext ctx);

	void enterModel(YicesParser.ModelContext ctx);
	void exitModel(YicesParser.ModelContext ctx);

	void enterNumeric(YicesParser.NumericContext ctx);
	void exitNumeric(YicesParser.NumericContext ctx);

	void enterInteger(YicesParser.IntegerContext ctx);
	void exitInteger(YicesParser.IntegerContext ctx);

	void enterAlias(YicesParser.AliasContext ctx);
	void exitAlias(YicesParser.AliasContext ctx);

	void enterValue(YicesParser.ValueContext ctx);
	void exitValue(YicesParser.ValueContext ctx);

	void enterPredefined(YicesParser.PredefinedContext ctx);
	void exitPredefined(YicesParser.PredefinedContext ctx);

	void enterVariable(YicesParser.VariableContext ctx);
	void exitVariable(YicesParser.VariableContext ctx);

	void enterFunction(YicesParser.FunctionContext ctx);
	void exitFunction(YicesParser.FunctionContext ctx);
}