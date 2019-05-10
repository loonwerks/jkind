package jkind.solvers.yices2;

import java.io.IOException;
import java.util.ArrayList;
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
	private final boolean linear;

	public Yices2Solver(String scratchBase, boolean linear) {
		super(scratchBase);
		this.linear = linear;
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
		if (linear) {
			send("(set-option :produce-unsat-cores true)");
			send("(set-logic QF_UFLIRA)");
		} else {
			send("(set-logic QF_UFNIRA)");
		}
	}

	@Override
	protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
		List<Symbol> unsatCore = new ArrayList<>();
		send("(get-unsat-core)");
		for (String s : readCore().split(" ")) {
			if (!s.isEmpty()) {
				unsatCore.add(new Symbol(s.substring(1)));
			}
		}
		return unsatCore;
	}

	private String readCore() {
		String line = "";
		try {
			line = fromSolver.readLine();
			comment(getSolverName() + ": " + line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line.substring(1, line.length() - 1);
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
