package jkind.solvers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import jkind.sexp.Sexp;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class YicesSolver extends Solver {
	private Process process;
	private BufferedWriter toYices;
	private BufferedReader fromYices;
	private boolean debug = true;

	final private static String DONE = "@DONE";
	
	public YicesSolver() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("yices");
		pb.redirectErrorStream(true);
		process = pb.start();
		toYices = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		fromYices = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		send("(set-evidence! true)");
	}

	public void send(Sexp sexp) throws IOException {
		send(sexp.toString());
	}
	
	public void send(List<Sexp> sexps) throws IOException {
		for (Sexp sexp : sexps) {
			send(sexp);
		}
	}
	
	private void send(String str) throws IOException {
		if (debug) {
			System.out.println("Sending: " + str);
		}
		toYices.append(str);
		toYices.newLine();
		toYices.flush();
	}

	public SolverResult query(Sexp sexp) throws IOException {
		return query(sexp.toString());
	}
	
	private SolverResult query(String str) throws IOException {
		push();
		send("(assert (not " + str + "))");
		send("(check)");
		send("(echo \"" + DONE + "\\n\")");
		pop();

		return readResult();
	}

	private SolverResult readResult() throws IOException {
		try {
			String line;
			StringBuilder content = new StringBuilder();
			while (true) {
				line = fromYices.readLine();
				if (debug) {
					System.out.println("Read: " + line);
				}
				if (line == null) {
					throw new IllegalArgumentException("Yices terminated unexpectedly");
				} else if (line.contains("Error:")) {
					throw new IllegalArgumentException("Yices error: " + line);
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
			throw new IllegalArgumentException("Unexpected output format from Yices", e);
		}
	}
	
	private static SolverResult parseYices(String string) throws IOException, RecognitionException {
		CharStream stream = new ANTLRStringStream(string);
		YicesLexer lexer = new YicesLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		YicesParser parser = new YicesParser(tokens);
		SolverResult result = parser.solverResult();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			throw new IllegalArgumentException("Error parsing Yices output");
		}
		return result;
	}

	@Override
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public void push() throws IOException {
		send("(push)");
	}

	@Override
	public void pop() throws IOException {
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
