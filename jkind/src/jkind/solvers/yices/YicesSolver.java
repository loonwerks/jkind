package jkind.solvers.yices;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Label;
import jkind.solvers.Result;
import jkind.solvers.Solver;
import jkind.solvers.UnsatResult;
import jkind.solvers.yices.YicesParser.ResultContext;
import jkind.translation.TransitionRelation;
import jkind.util.Util;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class YicesSolver extends Solver {
	private static final String DONE = "@DONE";
	private final boolean arithOnly;

	public YicesSolver(boolean arithOnly) {
		super(new ProcessBuilder(getYices()));
		this.arithOnly = arithOnly;
	}

	private static String getYices() {
		String home = System.getenv("YICES_HOME");
		if (home != null) {
			return new File(new File(home, "bin"), "yices").toString();
		}
		return "yices";
	}

	@Override
	public void initialize() {
		send("(set-evidence! true)");
		if (arithOnly) {
			send("(set-arith-only! true)");
		} else {
			send("(define to_int::(-> x::real (subtype (y::int) (and (<= y x) (< x (+ y 1))))))");
			send("(define to_real::(-> x::int (subtype (y::real) (= y x))))");
		}
	}

	@Override
	public void send(Sexp sexp) {
		send(sexp.toString());
	}

	private void send(String str) {
		debug(str);
		try {
			toSolver.append(str);
			toSolver.newLine();
			toSolver.flush();
		} catch (IOException e) {
			throw new JKindException("Unable to write to yices, "
					+ "probably due to internal JKind error", e);
		}
	}

	private Symbol type(Type type) {
		return new Symbol(Util.getName(type));
	}

	@Override
	public void define(VarDecl decl) {
		varTypes.put(decl.id, decl.type);
		send(new Cons("define", new Symbol(decl.id), new Symbol("::"), type(decl.type)));
	}

	@Override
	public void define(TransitionRelation lambda) {
		send(new Cons("define", TransitionRelation.T, new Symbol("::"), type(lambda),
				lambda(lambda)));
	}

	private Sexp type(TransitionRelation lambda) {
		List<Sexp> args = new ArrayList<>();
		for (VarDecl vd : lambda.getInputs()) {
			args.add(type(vd.type));
		}
		args.add(type(NamedType.BOOL));
		return new Cons("->", args);
	}

	private Sexp lambda(TransitionRelation lambda) {
		List<Sexp> args = new ArrayList<>();
		for (VarDecl vd : lambda.getInputs()) {
			args.add(new Symbol(vd.id));
			args.add(new Symbol("::"));
			args.add(type(vd.type));
		}
		return new Cons("lambda", new Cons(args), lambda.getBody());
	}

	private int labelCount = 1;

	@Override
	public Label labelledAssert(Sexp sexp) {
		debug("; id = " + labelCount);
		send(new Cons("assert+", sexp));
		return new Label(labelCount++);
	}

	@Override
	public void retract(Label label) {
		send(new Cons("retract", new Symbol(label.toString())));
	}

	@Override
	public Label weightedAssert(Sexp sexp, int weight) {
		debug("; id = " + labelCount);
		send(new Cons("assert+", sexp, Sexp.fromInt(weight)));
		return new Label(labelCount++);
	}

	@Override
	public Result query(Sexp sexp) {
		/**
		 * Using assert+ and retract seems to be much more efficient than push
		 * and pop for some reason.
		 */

		Label label = labelledAssert(new Cons("not", sexp));
		send("(check)");
		send("(echo \"" + DONE + "\\n\")");
		retract(label);

		Result result = readResult();
		if (result == null) {
			throw new JKindException("Unknown result from yices");
		}
		if (result instanceof UnsatResult) {
			List<Label> core = ((UnsatResult) result).getUnsatCore();
			if (core.contains(label)) {
				core.remove(label);
			} else {
				throw new JKindException("Unsat result did not depend on query");
			}
		}
		return result;
	}

	@Override
	public Result maxsatQuery(Sexp sexp) {
		Label label = labelledAssert(new Cons("not", sexp));
		send("(max-sat)");
		send("(echo \"" + DONE + "\\n\")");
		retract(label);

		Result result = readResult();
		if (result == null) {
			throw new JKindException("Unknown result from yices");
		}
		return result;
	}

	private Result readResult() {
		try {
			String line;
			StringBuilder content = new StringBuilder();
			boolean seenContextError = false;
			while (true) {
				line = fromSolver.readLine();
				debug("; YICES: " + line);
				if (line == null) {
					throw new JKindException("Yices terminated unexpectedly");
				} else if (line.contains("Error:")) {
					throw new JKindException("Yices error: " + line);
				} else if (line.startsWith("Logical context")) {
					/*
					 * One instance of a 'Logical context' message may come from
					 * the query we are asserting, but two instances indicates
					 * that some assumption about the system is false
					 */

					if (seenContextError) {
						throw new JKindException("Lustre program is inconsistent");
					}
					seenContextError = true;
					continue;
				} else if (line.equals(DONE)) {
					break;
				} else {
					content.append(line);
					content.append("\n");
				}
			}

			return parseYices(content.toString());
		} catch (RecognitionException e) {
			throw new JKindException("Error parsing Yices output", e);
		} catch (IOException e) {
			throw new JKindException("Unable to read from yices", e);
		}
	}

	private Result parseYices(String string) throws RecognitionException {
		CharStream stream = new ANTLRInputStream(string);
		YicesLexer lexer = new YicesLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		YicesParser parser = new YicesParser(tokens);
		ResultContext ctx;

		parser.removeErrorListeners();
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		parser.setErrorHandler(new BailErrorStrategy());
		try {
			ctx = parser.result();
		} catch (ParseCancellationException e) {
			tokens.reset();
			parser.addErrorListener(new StdoutErrorListener());
			parser.setErrorHandler(new DefaultErrorStrategy());
			parser.getInterpreter().setPredictionMode(PredictionMode.LL);
			ctx = parser.result();
		}

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing Yices output: " + string);
		}

		ParseTreeWalker walker = new ParseTreeWalker();
		ResultExtractorListener extractor = new ResultExtractorListener(varTypes);
		walker.walk(extractor, ctx);
		return extractor.getResult();
	}

	@Override
	public void push() {
		send("(push)");
	}

	@Override
	public void pop() {
		send("(pop)");
	}
}
