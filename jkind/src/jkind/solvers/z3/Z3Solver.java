package jkind.solvers.z3;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jkind.lustre.NamedType;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.smtlib2.SmtLib2Solver;

public class Z3Solver extends SmtLib2Solver {
	public Z3Solver(String scratchBase) {
		super(scratchBase, new ProcessBuilder(getZ3(), "-smt2", "-in"), "Z3");
	}

	private static String getZ3() {
		String home = System.getenv("Z3_HOME");
		if (home != null) {
			return new File(new File(home, "bin"), "z3").toString();
		}
		return "z3";
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
		send("(set-option :produce-unsat-cores true)");
		send("(set-option :global-decls false)");
	}

	private int assumCount = 1;

	public Result checkSat(List<String> names) {
		Result result = checkSat(names, true);
		if (result instanceof SatResult) {
			return result;
		} else if (result instanceof UnsatResult) {
			UnsatResult unsat = (UnsatResult) result;
			names.retainAll(unsat.getUnsatCore());
		}

		List<String> reduced = new ArrayList<>(names);
		for (String name : names) {
			if (reduced.remove(name)) {
				result = checkSat(reduced, false);
				if (result instanceof SatResult) {
					reduced.add(name);
				} else if (result instanceof UnsatResult) {
					UnsatResult unsat = (UnsatResult) result;
					reduced.retainAll(unsat.getUnsatCore());
				}
			}
		}
		return new UnsatResult(reduced);
	}

	public Result checkSat(List<String> assums, boolean extract) {
		Result result;

		List<Symbol> symbols = assums.stream().map(Symbol::new).collect(toList());
		send(new Cons("check-sat", symbols));
		send("(echo \"" + DONE + "\")");
		String status = readFromSolver();
		if (isSat(status)) {
			if (extract) {
				send("(get-model)");
				send("(echo \"" + DONE + "\")");
				result = new SatResult(parseModel(readFromSolver()));
			} else {
				result = new SatResult(null);
			}
		} else if (isUnsat(status)) {
			send("(get-unsat-core)");
			send("(echo \"" + DONE + "\")");
			result = new UnsatResult(parseUnsatCore(readFromSolver()));
		} else {
			result = new UnknownResult(null);
		}

		return result;
	}

	@Override
	public Result query(Sexp sexp) {
		Result result;

		Symbol assum = new Symbol("assum" + assumCount++);
		define(new VarDecl(assum.str, NamedType.BOOL));
		send(new Cons("assert", new Cons("=>", assum, new Cons("not", sexp))));
		send(new Cons("check-sat", assum));
		send("(echo \"" + DONE + "\")");
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			send("(echo \"" + DONE + "\")");
			result = new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			send("(get-unsat-core)");
			send("(echo \"" + DONE + "\")");
			result = new UnsatResult(parseUnsatCore(readFromSolver()));
		} else {
			// Even for unknown we can get a partial model
			send("(get-model)");
			send("(echo \"" + DONE + "\")");
			result = new UnknownResult(parseModel(readFromSolver()));
		}

		return result;
	}

	private List<String> parseUnsatCore(String text) {
		int start = text.indexOf("(");
		int stop = text.indexOf(")");
		String[] pieces = text.substring(start + 1, stop).split("\\s+");
		return Arrays.asList(pieces);
	}
}
