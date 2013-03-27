package jkind.solvers.yices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import jkind.JKindException;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Label;
import jkind.solvers.Result;
import jkind.solvers.Solver;
import jkind.solvers.UnsatResult;
import jkind.solvers.yices.YicesParser.ResultContext;

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
	private Process process;
	private BufferedWriter toYices;
	private BufferedReader fromYices;

	final private static String DONE = "@DONE";

	public YicesSolver() {
		ProcessBuilder pb = new ProcessBuilder("yices");
		pb.redirectErrorStream(true);
		try {
			process = pb.start();
		} catch (IOException e) {
			throw new JKindException("Unable to start yices", e);
		}
		addShutdownHook();
		toYices = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		fromYices = new BufferedReader(new InputStreamReader(process.getInputStream()));
	}

	@Override
	public void initialize() {
		send("(set-evidence! true)");
	}

	@Override
	public void send(Sexp sexp) {
		send(sexp.toString());
	}

	private void send(String str) {
		debug(str);
		try {
			toYices.append(str);
			toYices.newLine();
			toYices.flush();
		} catch (IOException e) {
			throw new JKindException("Unable to write to yices, "
					+ "probably due to internal JKind error", e);
		}
	}

	private int labelCount = 1;

	@Override
	public IntLabel labelledAssert(Sexp sexp) {
		debug("; id = " + labelCount);
		send(new Cons("assert+", sexp));
		return new IntLabel(labelCount++);
	}

	@Override
	public void retract(Label label) {
		if (label instanceof IntLabel) {
			IntLabel il = (IntLabel) label;
			send(new Cons("retract", Sexp.fromInt(il.i)));
		} else {
			throw new IllegalArgumentException("Invalid label to retract in YicesSolver: "
					+ label.getClass().getCanonicalName());
		}
	}

	@Override
	public Label weightedAssert(Sexp sexp, int weight) {
		debug("; id = " + labelCount);
		send(new Cons("assert+", sexp, Sexp.fromInt(weight)));
		return new IntLabel(labelCount++);
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
				line = fromYices.readLine();
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

	private static Result parseYices(String string) throws IOException, RecognitionException {
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
			System.out.println(string);
			throw new JKindException("Error parsing Yices output");
		}

		ParseTreeWalker walker = new ParseTreeWalker();
		ResultExtractorListener extractor = new ResultExtractorListener();
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

	@Override
	public synchronized void stop() {
		/**
		 * This must be synchronized since two threads (a Process or a shutdown
		 * hook) may try to stop the solver at the same time
		 */

		if (process != null) {
			process.destroy();
			process = null;
		}
	}

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
			@Override
			public void run() {
				YicesSolver.this.stop();
			}
		});
	}
}
