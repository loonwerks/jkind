package jkind.solvers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import jkind.misc.JKindException;
import jkind.sexp.Sexp;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class YicesSolver extends Solver {
	private Process process;
	private BufferedWriter toYices;
	private BufferedReader fromYices;
	private boolean debug = false;

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
		
		send("(set-evidence! true)");
	}

	public void send(Sexp sexp) {
		send(sexp.toString());
	}
	
	public void send(List<Sexp> sexps) {
		for (Sexp sexp : sexps) {
			send(sexp);
		}
	}
	
	private void send(String str) {
		if (debug) {
			System.out.println("Sending: " + str);
		}
		try {
			toYices.append(str);
			toYices.newLine();
			toYices.flush();
		} catch (IOException e) {
			throw new JKindException("Unable to write to yices", e);
		}
	}

	public SolverResult query(Sexp sexp) {
		return query(sexp.toString());
	}
	
	private SolverResult query(String str) {
		push();
		send("(assert (not " + str + "))");
		send("(check)");
		send("(echo \"" + DONE + "\\n\")");
		pop();

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
			while (true) {
				line = fromYices.readLine();
				if (debug) {
					System.out.println("Read: " + line);
				}
				if (line == null) {
					throw new JKindException("Yices terminated unexpectedly");
				} else if (line.contains("Error:")) {
					throw new JKindException("Yices error: " + line);
				} else if (line.startsWith("Logical context")) {
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
	
	private static SolverResult parseYices(String string) throws IOException, RecognitionException {
		CharStream stream = new ANTLRStringStream(string);
		YicesLexer lexer = new YicesLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		YicesParser parser = new YicesParser(tokens);
		SolverResult result = parser.solverResult();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new JKindException("Error parsing Yices output");
		}
		return result;
	}

	@Override
	public void setDebug(boolean debug) {
		this.debug = debug;
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
