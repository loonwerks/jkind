package jkind.solvers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jkind.sexp.Sexp;
import jkind.solvers.SolverResult.Result;

public class YicesSolver extends Solver {
	private Process process;
	private BufferedWriter toYices;
	private BufferedReader fromYices;
	private boolean debug = false;

	final private static String DONE = "__DONE__";

	public YicesSolver() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("yices");
		pb.redirectErrorStream(true);
		process = pb.start();
		toYices = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		fromYices = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		send("(set-evidence! true)");
	}

	public void stop() {
		try {
			toYices.close();
			fromYices.close();
		} catch (IOException e) {
		}
		process.destroy();
		process = null;
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
		send("(push)");
		send("(assert " + str + ")");
		send("(check)");
		send("(echo \"" + DONE + "\\n\")");
		send("(pop)");

		return readResult();
	}

	final private static Pattern valuePattern = Pattern.compile("\\(= (\\S+) (\\S+) \\)");
	final private static Pattern functionPattern = Pattern
			.compile("\\(= \\((\\S+) (\\S+)\\) (\\S+)\\)");

	private SolverResult readResult() throws IOException {
		Result result = null;
		Model model = new Model();
		while (true) {
			String line = fromYices.readLine();
			if (debug ) {
				System.out.println("Read: " + line);
			}
			if (line == null) {
				throw new IllegalArgumentException("Yices terminated unexpectedly");
			} else if (line.equals(DONE)) {
				break;
			} else if (line.equals("unsat")) {
				result = Result.UNSAT;
			} else if (line.equals("sat")) {
				result = Result.SAT;
			} else if (line.startsWith("Error:")) {
				throw new IllegalArgumentException("Yices error: " + line);
			} else {
				Matcher m = valuePattern.matcher(line);
				if (m.matches()) {
					model.addValue(m.group(1), parseValue(m.group(2)));
				} else {
					m = functionPattern.matcher(line);
					if (m.matches()) {
						model.addFunctionValue(m.group(1), Integer.parseInt(m.group(2)),
								parseValue(m.group(3)));
					}
				}
			}
		}
		return new SolverResult(result, model);
	}

	private Value parseValue(String str) {
		if (str.equals("true")) {
			return BoolValue.TRUE;
		} else if (str.equals("false")) {
			return BoolValue.FALSE;
		} else {
			return new NumericValue(str);
		}
	}
}
