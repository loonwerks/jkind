package jkind.solvers.yices2;

import java.util.List;

import jkind.JKindException;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.solvers.yices2.Yices2Parser.ModelContext;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Yices2Solver extends SmtLib2Solver {
	public Yices2Solver(String scratchBase) {
		super(scratchBase);
	}

	@Override
	protected String getSolverName() {
		return "Yices2";
	}

	@Override
	protected String getSolverExecutable() {
		return "yices-smt2";
	}

	@Override
	protected String[] getSolverOptions() {
		return new String[] { "--incremental" };
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
		send("(set-logic QF_LIRA)");
	}

	@Override
	protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
		// Yices2 does not yet support unsat-cores
		return activationLiterals;
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
			throw new JKindException("Error parsing " + getSolverName() + " output: " + string);
		}

		ParseTreeWalker walker = new ParseTreeWalker();
		ModelExtractorListener extractor = new ModelExtractorListener(varTypes);
		walker.walk(extractor, ctx);
		return extractor.getModel();
	}
}
