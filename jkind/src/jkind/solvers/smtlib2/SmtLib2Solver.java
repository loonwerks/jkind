package jkind.solvers.smtlib2;

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
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.Solver;
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

public abstract class SmtLib2Solver extends Solver {
	final protected static String DONE = "@DONE";
	final protected String name;

	public SmtLib2Solver(ProcessBuilder pb, String name) {
		super(pb);
		this.name = name;
	}

	@Override
	public void send(Sexp sexp) {
		send(Quoting.quoteSexp(sexp).toString());
	}

	protected void send(String str) {
		debug(str);
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

	private int labelCount = 1;

	@Override
	public Label labelledAssert(Sexp sexp) {
		String name = "a" + labelCount++;
		send(new Cons("assert", new Cons("!", sexp, new Symbol(":named"), new Symbol(name))));
		return new Label(name);
	}

	@Override
	public void retract(Label label) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Label weightedAssert(Sexp sexp, int weight) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result query(Sexp sexp) {
		Result result;
		push();

		send(new Cons("assert", new Cons("not", sexp)));
		send("(check-sat)");
		send("(echo \"" + DONE + "\")");
		if (isSat(readFromSolver())) {
			send("(get-model)");
			send("(echo \"" + DONE + "\")");
			result = new SatResult(parseModel(readFromSolver()));
		} else {
			result = new UnsatResult();
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
				debug("; " + name + ": " + line);
				if (line == null) {
					throw new JKindException(name + " terminated unexpectedly");
				} else if (line.contains("define-fun " + TransitionRelation.T + " ")) {
					// No need to parse the transition relation
				} else if (line.contains("error \"") || line.contains("Error:")) {
					// Flush the output since errors span multiple lines
					while ((line = fromSolver.readLine()) != null) {
						debug("; " + name + ": " + line);
						if (line.contains(DONE)) {
							break;
						}
					}
					throw new JKindException(name + " error (see scratch file for details)");
				} else if (line.contains(DONE)) {
					break;
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
	public Result maxsatQuery(Sexp sexp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void push() {
		send("(push 1)");
	}

	@Override
	public void pop() {
		send("(pop 1)");
	}
}
