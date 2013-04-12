// Generated from Cvc4.g4 by ANTLR 4.0
package jkind.solvers.cvc4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface Cvc4Listener extends ParseTreeListener {
	void enterArg(Cvc4Parser.ArgContext ctx);
	void exitArg(Cvc4Parser.ArgContext ctx);

	void enterModel(Cvc4Parser.ModelContext ctx);
	void exitModel(Cvc4Parser.ModelContext ctx);

	void enterDefun(Cvc4Parser.DefunContext ctx);
	void exitDefun(Cvc4Parser.DefunContext ctx);

	void enterSymbol(Cvc4Parser.SymbolContext ctx);
	void exitSymbol(Cvc4Parser.SymbolContext ctx);

	void enterSymbolBody(Cvc4Parser.SymbolBodyContext ctx);
	void exitSymbolBody(Cvc4Parser.SymbolBodyContext ctx);

	void enterDefval(Cvc4Parser.DefvalContext ctx);
	void exitDefval(Cvc4Parser.DefvalContext ctx);

	void enterType(Cvc4Parser.TypeContext ctx);
	void exitType(Cvc4Parser.TypeContext ctx);

	void enterFn(Cvc4Parser.FnContext ctx);
	void exitFn(Cvc4Parser.FnContext ctx);

	void enterConsBody(Cvc4Parser.ConsBodyContext ctx);
	void exitConsBody(Cvc4Parser.ConsBodyContext ctx);
}