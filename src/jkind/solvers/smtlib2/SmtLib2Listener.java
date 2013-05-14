// Generated from SmtLib2.g4 by ANTLR 4.0
package jkind.solvers.smtlib2;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface SmtLib2Listener extends ParseTreeListener {
	void enterArg(SmtLib2Parser.ArgContext ctx);
	void exitArg(SmtLib2Parser.ArgContext ctx);

	void enterModel(SmtLib2Parser.ModelContext ctx);
	void exitModel(SmtLib2Parser.ModelContext ctx);

	void enterDefun(SmtLib2Parser.DefunContext ctx);
	void exitDefun(SmtLib2Parser.DefunContext ctx);

	void enterSymbol(SmtLib2Parser.SymbolContext ctx);
	void exitSymbol(SmtLib2Parser.SymbolContext ctx);

	void enterSymbolBody(SmtLib2Parser.SymbolBodyContext ctx);
	void exitSymbolBody(SmtLib2Parser.SymbolBodyContext ctx);

	void enterDefval(SmtLib2Parser.DefvalContext ctx);
	void exitDefval(SmtLib2Parser.DefvalContext ctx);

	void enterType(SmtLib2Parser.TypeContext ctx);
	void exitType(SmtLib2Parser.TypeContext ctx);

	void enterFn(SmtLib2Parser.FnContext ctx);
	void exitFn(SmtLib2Parser.FnContext ctx);

	void enterConsBody(SmtLib2Parser.ConsBodyContext ctx);
	void exitConsBody(SmtLib2Parser.ConsBodyContext ctx);
}