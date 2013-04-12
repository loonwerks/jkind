package jkind.solvers.cvc4;

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
import jkind.solvers.cvc4.Cvc4Parser.ModelContext;
import jkind.translation.Keywords;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Cvc4Solver extends Solver {
	final private static String DONE = "@DONE";

	public Cvc4Solver() {
		super(new ProcessBuilder("cvc4", "--lang", "smt"));
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
		send("(set-option :incremental true)");
		send("(set-logic AUFLIRA)");
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
			throw new JKindException("Unable to write to CVC4, "
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
	
	@Override
	public Label labelledAssert(Sexp sexp) {
		throw new UnsupportedOperationException();
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
				debug("; CVC4: " + line);
				if (line == null) {
					throw new JKindException("CVC4 terminated unexpectedly");
				} else if (line.contains("define-fun " + Keywords.T)) {
					// No need to parse the transition relation
				} else if (line.contains("error \"") || line.contains("Error:")) {
					// Flush the rest of the output since errors span multiple lines
					while ((line = fromSolver.readLine()) != null) {
						debug("; CVC4: " + line);
					}
					throw new JKindException("CVC4 error (see scratch file for details)");
				} else if (line.contains(DONE)) {
					break;
				} else {
					content.append(line);
					content.append("\n");
				}
			}

			return content.toString();
		} catch (RecognitionException e) {
			throw new JKindException("Error parsing CVC4 output", e);
		} catch (IOException e) {
			throw new JKindException("Unable to read from CVC4", e);
		}
	}

	private static Cvc4Model parseModel(String string) {
		CharStream stream = new ANTLRInputStream(string);
		Cvc4Lexer lexer = new Cvc4Lexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Cvc4Parser parser = new Cvc4Parser(tokens);
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
			throw new JKindException("Error parsing CVC4 output");
		}

		ParseTreeWalker walker = new ParseTreeWalker();
	    ModelExtractorListener extractor = new ModelExtractorListener();
		walker.walk(extractor, ctx);
		return extractor.getModel();
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
