package jkind.solvers.yices2;

import jkind.JKindException;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.solvers.Model;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.solvers.yices2.Yices2Parser.ModelContext;
import jkind.translation.Specification;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Yices2Solver extends SmtLib2Solver {
	public Yices2Solver(String scratchBase) {
		super(scratchBase, new ProcessBuilder("yices-smt2", "--incremental"), "Yices2");
	}

	@Override
	public void initialize(Specification spec) {
		send("(set-option :produce-models true)");
		send("(set-logic QF_LIRA)");
	}

	@Override
	protected Model parseModel(String string) {
		CharStream stream = new ANTLRInputStream(string);
		Yices2Lexer lexer = new Yices2Lexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Yices2Parser parser = new Yices2Parser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new StdoutErrorListener());
		ModelContext ctx = parser.model();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing " + name + " output: " + string);
		}

		ParseTreeWalker walker = new ParseTreeWalker();
		ModelExtractorListener extractor = new ModelExtractorListener(varTypes);
		walker.walk(extractor, ctx);
		return extractor.getModel();
	}
}
