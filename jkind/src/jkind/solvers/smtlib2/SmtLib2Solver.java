package jkind.solvers.smtlib2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.ProcessBasedSolver;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.smtlib2.SmtLib2Parser.ModelContext;
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

public abstract class SmtLib2Solver extends ProcessBasedSolver {
	final protected String name;

	public SmtLib2Solver(JKindSettings settings, String engineName, ProcessBuilder pb, String name) {
		super(settings, engineName, pb);
		this.name = name;
	}

	@Override
	public void assertSexp(Sexp sexp) {
		send(new Cons("assert", sexp));
	}

	protected void send(Sexp sexp) {
		send(Quoting.quoteSexp(sexp).toString());
	}

	protected void send(String str) {
		scratch(str);
		try {
			toSolver.append(str);
			toSolver.newLine();
			toSolver.flush();
		} catch (IOException e) {
			throw new JKindException("Unable to write to " + name + ", "
					+ "probably due to internal JKind error", e);
		}
	}

	private Symbol type(Type type) {
		return new Symbol(capitalize(Util.getName(type)));
	}

	private String capitalize(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	@Override
	public void define(VarDecl decl) {
		varTypes.put(decl.id, decl.type);
		send(new Cons("declare-fun", new Symbol(decl.id), new Symbol("()"), type(decl.type)));
	}

	@Override
	public void define(TransitionRelation lambda) {
		send(new Cons("define-fun", TransitionRelation.T, inputs(lambda.getInputs()),
				type(NamedType.BOOL), lambda.getBody()));
	}

	private Sexp inputs(List<VarDecl> inputs) {
		List<Sexp> args = new ArrayList<>();
		for (VarDecl vd : inputs) {
			args.add(new Cons(vd.id, type(vd.type)));
		}
		return new Cons(args);
	}

	@Override
	public Result query(Sexp sexp) {
		Result result = null;
		push();

		assertSexp(new Cons("not", sexp));
		send("(check-sat)");
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

		pop();
		return result;
	}

	protected boolean isSat(String output) {
		return output.trim().equals("sat");
	}

	protected boolean isUnsat(String output) {
		return output.trim().equals("unsat");
	}

	protected String readFromSolver() {
		try {
			String line;
			StringBuilder content = new StringBuilder();
			while (true) {
				line = fromSolver.readLine();
				comment(name + ": " + line);
				if (line == null) {
					throw new JKindException(name + " terminated unexpectedly");
				} else if (line.contains("define-fun " + TransitionRelation.T + " ")) {
					// No need to parse the transition relation
				} else if (isDone(line)) {
					break;
				} else if (line.contains("error \"") || line.contains("Error:")) {
					// Flush the output since errors span multiple lines
					while ((line = fromSolver.readLine()) != null) {
						comment(name + ": " + line);
						if (isDone(line)) {
							break;
						}
					}
					throw new JKindException(name + " error (see scratch file for details)");
				} else {
					content.append(line);
					content.append("\n");
				}
			}

			return content.toString();
		} catch (RecognitionException e) {
			throw new JKindException("Error parsing " + name + " output", e);
		} catch (IOException e) {
			throw new JKindException("Unable to read from " + name, e);
		}
	}

	protected boolean isDone(String line) {
		return line.contains(DONE);
	}

	protected Model parseModel(String string) {
		CharStream stream = new ANTLRInputStream(string);
		SmtLib2Lexer lexer = new SmtLib2Lexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SmtLib2Parser parser = new SmtLib2Parser(tokens);
		ModelContext ctx;

		parser.removeErrorListeners();
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		parser.setErrorHandler(new BailErrorStrategy());
		try {
			ctx = parser.model();
		} catch (ParseCancellationException e) {
			tokens.reset();
			parser.addErrorListener(new StdoutErrorListener());
			parser.setErrorHandler(new DefaultErrorStrategy());
			parser.getInterpreter().setPredictionMode(PredictionMode.LL);
			ctx = parser.model();
		}

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing " + name + " output: " + string);
		}

		return ModelExtractor.getModel(ctx, varTypes);
	}

	@Override
	public void push() {
		send("(push 1)");
	}

	@Override
	public void pop() {
		send("(pop 1)");
	}
	
	@Override
	public String getSolverExtension() {
		return "smt2";
	}
}
