package jkind.solvers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import jkind.JKindException;
import jkind.sexp.Sexp;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

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

	@Override
	public void send(List<Sexp> sexps) {
		for (Sexp sexp : sexps) {
			send(sexp);
		}
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

	@Override
	public SolverResult query(Sexp sexp) {
		return query(sexp.toString());
	}

	private int queryCount = 1;

	private SolverResult query(String str) {
		/**
		 * Using assert+ and retract seems to be much more efficient than push
		 * and pop for some reason.
		 */

		send("(assert+ (not " + str + "))");
		send("(check)");
		send("(echo \"" + DONE + "\\n\")");
		send("(retract " + queryCount + ")");
		queryCount++;

		SolverResult result = readResult();
		if (result.getResult() == null) {
			throw new JKindException("Unknown result from yices");
		}
		return result;
	}

	private SolverResult readResult() {
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
				} else if (line.startsWith("unsat core ids")) {
					continue;
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

	private static SolverResult parseYices(String string) throws IOException, RecognitionException {
		CharStream stream = new ANTLRStringStream(string);
		YicesLexer lexer = new YicesLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		YicesParser parser = new YicesParser(tokens);
		SolverResult result = parser.solverResult();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			System.out.println(string);
			throw new JKindException("Error parsing Yices output");
		}
		return result;
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
	public void stop() {
		try {
			toYices.close();
			fromYices.close();
		} catch (IOException e) {
		}
		process.destroy();
		process = null;
	}
}
