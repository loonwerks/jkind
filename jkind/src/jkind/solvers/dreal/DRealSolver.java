package jkind.solvers.dreal;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import jkind.JKindException;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.SolverParserErrorListener;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.dreal.parser.DRealModelLexer;
import jkind.solvers.dreal.parser.DRealModelParser;
import jkind.solvers.smtlib2.SmtLib2Solver;

public class DRealSolver extends SmtLib2Solver {

	public DRealSolver(String scratchBase) {
		super(scratchBase);
	}

	@Override
	protected String getSolverName() {
		return "dfake";
	}

	@Override
	protected String[] getSolverOptions() {
		return new String[] { "-in" };
	}

	@Override
	public void initialize() {
		send("(set-logic QF_NRA)");
	}

	@Override
	public Result query(Sexp sexp) {
		Result result;

		push();
		send(new Cons("assert", new Cons("not", sexp)));
		send(new Cons("check-sat"));

		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			String modelResult = readFromSolver();
			result = new SatResult(parseModel(modelResult));
		} else if (isUnsat(status)) {
			result = new UnsatResult();
		} else {
			// Even for unknown we can sometimes get a partial model
			send("(get-model)");
			Model m = null;
			try {
				m = parseModel(readFromSolver());
			} catch(JKindException e) {
				// O.k., not able to read the CEX.
			}
			if (m == null) {
				return new UnknownResult();
			} else {
				result = new UnknownResult(m);
			}
		}

		pop();

		return result;
	}

	protected Model parseModel(String string) {
		CharStream stream = new ANTLRInputStream(string);
		DRealModelLexer lexer = new DRealModelLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DRealModelParser parser = new DRealModelParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new SolverParserErrorListener());
		DRealModelParser.ModelContext ctx = parser.model();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing " + getSolverName() + " output: " + string);
		}

		return ModelExtractor.getModel(ctx, varTypes);
	}

	
	@Override
	protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
		// dReal does not yet appear to support unsat-cores
		return activationLiterals;
	}

}
