package jkind.solvers.smtlib2;

import java.io.IOException;

import jkind.JKindException;
import jkind.lustre.Type;
import jkind.lustre.parsing.StdoutErrorListener;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Label;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.Solver;
import jkind.solvers.StreamDecl;
import jkind.solvers.StreamDef;
import jkind.solvers.UnsatResult;
import jkind.solvers.VarDecl;
import jkind.solvers.smtlib2.SmtLib2Parser.ModelContext;
import jkind.translation.Keywords;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public abstract class SmtLib2Solver extends Solver {
	final private static String DONE = "@DONE";
	final private String name;

	public SmtLib2Solver(ProcessBuilder pb, String name) {
		super(pb);
		this.name = name;
	}

	@Override
	public void send(Sexp sexp) {
		send(sexp.toString());
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

	@Override
	public void send(StreamDecl decl) {
		send(new Cons("declare-fun", decl.getId(), new Cons("Int"), type(decl.getType())));
	}

	private Symbol type(Type type) {
		return new Symbol(capitalize(type.name));
	}

	private String capitalize(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	@Override
	public void send(StreamDef def) {
		Sexp arg = new Cons(def.getArg(), new Symbol("Int"));
		send(new Cons("define-fun", def.getId(), new Cons(arg), type(def.getType()), def.getBody()));
	}

	@Override
	public void send(VarDecl decl) {
		send(new Cons("declare-fun", decl.id, new Symbol("()"), type(decl.type)));
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

	private boolean isSat(String output) {
		return !output.contains("unsat");
	}

	private String readFromSolver() {
		try {
			String line;
			StringBuilder content = new StringBuilder();
			while (true) {
				line = fromSolver.readLine();
				debug("; " + name + ": " + line);
				if (line == null) {
					throw new JKindException(name + " terminated unexpectedly");
				} else if (line.contains("define-fun " + Keywords.T + " ")) {
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

	private SmtLib2Model parseModel(String string) {
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
			System.out.println(string);
			throw new JKindException("Error parsing " + name + " output");
		}

		return ModelExtractor.getModel(ctx);
	}

	@Override
	public Result maxsatQuery(Sexp sexp) {
		throw new UnsupportedOperationException();
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
