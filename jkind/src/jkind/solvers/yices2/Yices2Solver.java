package jkind.solvers.yices2;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import jkind.JKindException;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.SolverParserErrorListener;
import jkind.solvers.smtlib2.SmtLib2Solver;
import jkind.solvers.yices2.Yices2Parser.ModelContext;

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
		send("(set-logic QF_UFLIRA)");
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
		parser.addErrorListener(new SolverParserErrorListener());
		ModelContext ctx = parser.model();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing " + getSolverName() + " output: " + string);
		}

		ParseTreeWalker walker = new ParseTreeWalker();
		ModelExtractorListener extractor = new ModelExtractorListener(varTypes, functions);
		walker.walk(extractor, ctx);
		return extractor.getModel();
	}
}
