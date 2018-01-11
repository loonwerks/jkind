package jkind.solvers.smtlib2;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import jkind.JKindException;
import jkind.lustre.Function;
import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.ProcessBasedSolver;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.SolverParserErrorListener;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.smtlib2.SmtLib2Parser.ModelContext;
import jkind.translation.Relation;
import jkind.util.SexpUtil;
import jkind.util.Util;

public abstract class SmtLib2Solver extends ProcessBasedSolver {
	public SmtLib2Solver(String scratchBase) {
		super(scratchBase);
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
			throw new JKindException(
					"Unable to write to " + getSolverName() + ", " + "probably due to internal JKind error", e);
		}
	}

	public Symbol type(Type type) {
		return new Symbol(Util.capitalize(Util.getName(type)));
	}

	@Override
	public void define(VarDecl decl) {
		varTypes.put(decl.id, decl.type);
		send(new Cons("declare-fun", new Symbol(decl.id), new Symbol("()"), type(decl.type)));
	}

	@Override
	public void declare(Function function) {
		functions.add(function);
		Symbol name = new Symbol(SexpUtil.encodeFunction(function.id));
		List<Sexp> inputTypes = function.inputs.stream().map(vd -> type(vd.type)).collect(toList());
		Sexp inputTypesDecl = inputTypes.isEmpty() ? new Symbol("()") : new Cons(inputTypes);
		Symbol outputType = type(function.outputs.get(0).type);
		send(new Cons("declare-fun", name, inputTypesDecl, outputType));
	}

	@Override
	public void define(Relation relation) {
		send(new Cons("define-fun", new Symbol(relation.getName()), inputs(relation.getInputs()), type(NamedType.BOOL),
				relation.getBody()));
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
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			result = new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			result = new UnsatResult();
		} else {
			throw new IllegalArgumentException("Unknown result: " + result);
		}

		pop();
		return result;
	}

	@Override
	protected Result quickCheckSat(List<Symbol> activationLiterals) {
		push();
		for (Symbol actLit : activationLiterals) {
			String name = "_" + actLit.str;
			assertSexp(new Cons("!", actLit, new Symbol(":named"), new Symbol(name)));
		}

		send("(check-sat)");
		String status = readFromSolver();
		Result result;
		if (isSat(status)) {
			result = new SatResult();
		} else if (isUnsat(status)) {
			result = new UnsatResult(getUnsatCore(activationLiterals));
		} else {
			result = new UnknownResult();
		}

		pop();
		return result;
	}

	protected abstract List<Symbol> getUnsatCore(List<Symbol> activationLiterals);

	protected boolean isSat(String output) {
		// Only check prefix, Z3 outputs content after 'sat' for max-sat style
		// queries
		return output.trim().startsWith("sat");
	}

	protected boolean isUnsat(String output) {
		return output.trim().equals("unsat");
	}

	protected String readFromSolver() {
		send("(echo \"" + DONE + "\")");

		try {
			String line;
			StringBuilder content = new StringBuilder();
			while (true) {
				line = fromSolver.readLine();
				comment(getSolverName() + ": " + line);
				if (line == null) {
					throw new JKindException(getSolverName() + " terminated unexpectedly");
				} else if (line.contains("define-fun " + Relation.T + " ")) {
					// No need to parse the transition relation
				} else if (isDone(line)) {
					break;
				} else if (line.contains("model is not available")) {
					return null;
				} else if (line.contains(" |-> ")) {
					// Ignore Z3 optimization information
				} else if (line.contains("error \"") || line.contains("Error:")) {
					// Flush the output since errors span multiple lines
					while ((line = fromSolver.readLine()) != null) {
						comment(getSolverName() + ": " + line);
						if (isDone(line)) {
							break;
						}
					}
					throw new JKindException(getSolverName() + " error (see scratch file for details)");
				} else {
					content.append(line);
					content.append("\n");
				}
			}

			return content.toString();
		} catch (RecognitionException e) {
			throw new JKindException("Error parsing " + getSolverName() + " output", e);
		} catch (IOException e) {
			throw new JKindException("Unable to read from " + getSolverName(), e);
		}
	}

	protected boolean isDone(String line) {
		return line.contains(DONE);
	}
	
	protected Model parseModel(String modelStr) {
		return parseSmtLib2Model(modelStr, varTypes, functions);
	}

	public static SmtLib2Model parseSmtLib2Model(String modelStr, Map<String, Type> varTypes, List<Function> functions) {
		CharStream stream = new ANTLRInputStream(modelStr);
		SmtLib2Lexer lexer = new SmtLib2Lexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SmtLib2Parser parser = new SmtLib2Parser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new SolverParserErrorListener());
		ModelContext ctx = parser.model();

		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing solver output: " + modelStr);
		}

		return ModelExtractor.getModel(ctx, varTypes, functions);
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
