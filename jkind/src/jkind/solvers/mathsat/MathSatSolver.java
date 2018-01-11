package jkind.solvers.mathsat;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import jkind.JKindException;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.visitors.ExprConjunctiveVisitor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.SolverParserErrorListener;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.mathsat.MathSatParser.ModelContext;
import jkind.solvers.mathsat.MathSatParser.UnsatAssumptionsContext;
import jkind.solvers.smtlib2.SmtLib2Solver;

public class MathSatSolver extends SmtLib2Solver {
	public MathSatSolver(String scratchBase) {
		super(scratchBase);
	}

	@Override
	protected String getSolverName() {
		return "MathSAT";
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
		send("(set-option :produce-unsat-cores true)");
	}

	private int actCount = 1;

	@Override
	public Result query(Sexp sexp) {
		Result result = null;
		Symbol actLit = createActivationLiteral("act", actCount++);
		send(new Cons("assert", new Cons("=>", actLit, new Cons("not", sexp))));
		send(new Cons("check-sat-assumptions", new Cons(actLit)));
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			result = new SatResult(parseMathSatModel(readFromSolver()));
		} else if (isUnsat(status)) {
			result = new UnsatResult();
		} else {
			throw new IllegalArgumentException("Unknown result: " + result);
		}

		return result;
	}

	@Override
	protected Result quickCheckSat(List<Symbol> activationLiterals) {
		if (activationLiterals.isEmpty()) {
			send(new Cons("check-sat"));
		} else {
			Symbol head = activationLiterals.get(0);
			List<Symbol> tail = activationLiterals.subList(1, activationLiterals.size());
			send(new Cons("check-sat-assumptions", new Cons(head, tail)));
		}

		String status = readFromSolver();
		if (isSat(status)) {
			return new SatResult();
		} else if (isUnsat(status)) {
			return new UnsatResult(getUnsatCore(activationLiterals));
		} else {
			return new UnknownResult();
		}
	}

	@Override
	protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
		send(new Cons("get-unsat-assumptions"));
		return parseUnsatAssumptions(readFromSolver());
	}

	protected Model parseMathSatModel(String string) {
		MathSatParser parser = getParser(string);
		ModelContext ctx = parser.model();
		ensureNoParseError(parser, string);
		return ModelExtractor.getModel(ctx, varTypes, functions);
	}

	private List<Symbol> parseUnsatAssumptions(String string) {
		MathSatParser parser = getParser(string);
		UnsatAssumptionsContext ctx = parser.unsatAssumptions();
		ensureNoParseError(parser, string);
		return ctx.symbol().stream().map(sctx -> new Symbol(sctx.getText())).collect(toList());
	}

	private MathSatParser getParser(String string) {
		CharStream stream = new ANTLRInputStream(string);
		MathSatLexer lexer = new MathSatLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MathSatParser parser = new MathSatParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new SolverParserErrorListener());
		return parser;
	}

	private void ensureNoParseError(MathSatParser parser, String string) {
		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing " + getSolverName() + " output: " + string);
		}
	}

	@Override
	public boolean supports(Expr expr) {
		return expr.accept(new ExprConjunctiveVisitor() {
			@Override
			public Boolean visit(BinaryExpr e) {
				return e.op != BinaryOp.INT_DIVIDE && e.op != BinaryOp.MODULUS && super.visit(e);
			}
		});
	}
}
