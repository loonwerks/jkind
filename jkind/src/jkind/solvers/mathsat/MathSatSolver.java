package jkind.solvers.mathsat;

import jkind.JKindException;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.NamedType;
import jkind.lustre.VarDecl;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.lustre.visitors.ExprConjunctiveVisitor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.mathsat.MathSatParser.ModelContext;
import jkind.solvers.smtlib2.SmtLib2Solver;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

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
	}

	private int assumCount = 1;

	@Override
	public Result query(Sexp sexp) {
		Result result = null;

		Symbol assum = new Symbol("assum" + assumCount++);
		define(new VarDecl(assum.str, NamedType.BOOL));
		send(new Cons("assert", new Cons("=>", assum, new Cons("not", sexp))));
		send(new Cons("check-sat-assumptions", new Cons(assum)));
		send("(echo \"" + DONE + "\")");
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			send("(echo \"" + DONE + "\")");
			result = new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			result = new UnsatResult();
		} else {
			throw new IllegalArgumentException("Unknown result: " + result);
		}

		return result;
	}

	@Override
	protected boolean isDone(String line) {
		return line.equals("(error \"unknown command: echo\")");
	}

	@Override
	protected Model parseModel(String string) {
		CharStream stream = new ANTLRInputStream(string);
		MathSatLexer lexer = new MathSatLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MathSatParser parser = new MathSatParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new StdoutErrorListener());
		ModelContext ctx = parser.model();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing " + getSolverName() + " output: " + string);
		}

		return ModelExtractor.getModel(ctx, varTypes);
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
