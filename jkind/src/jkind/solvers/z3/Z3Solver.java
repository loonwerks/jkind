package jkind.solvers.z3;

import java.io.File;

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
	private final boolean linear;

	public Z3Solver(String scratchBase, boolean linear) {
		super(scratchBase, new ProcessBuilder(getZ3(), "-smt2", "-in"), "Z3");
		this.linear = linear;
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
	}

	private int assumCount = 1;

	@Override
	public Result query(Sexp sexp) {
		Result result;

		if (linear) {
			Symbol assum = new Symbol("assum" + assumCount++);
			define(new VarDecl(assum.str, NamedType.BOOL));
			send(new Cons("assert", new Cons("=>", assum, new Cons("not", sexp))));
			send(new Cons("check-sat", assum));
		} else {
			push();
			send(new Cons("assert", new Cons("not", sexp)));
			send(new Cons("check-sat"));
		}
		
		markDone();
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			markDone();
			result = new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			result = new UnsatResult();
		} else {
			// Even for unknown we can get a partial model
			send("(get-model)");
			markDone();
			result = new UnknownResult(parseModel(readFromSolver()));
		}
		
		if (!linear) {
			pop();
		}

		return result;
	}

	private void markDone() {
		send("(echo \"" + DONE + "\")");
	}

	public Result realizabilityQuery(Sexp outputs, Sexp transition, Sexp properties, int timeoutMs) {
		push();
		if (timeoutMs > 0) {
			send(new Cons("set-option", new Symbol(":timeout"), Sexp.fromInt(timeoutMs)));
		}
		Sexp query = new Cons("not", new Cons("and", transition, properties));
		if (outputs != null) {
			query = new Cons("forall", outputs, query);
		}
		assertSexp(query);
		send(new Cons("check-sat"));
		markDone();
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			markDone();
			pop();
			return new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			pop();
			return new UnsatResult();
		} else {
			pop();
			return new UnknownResult();
		}
	}

	public Result realizabilityQuery(Sexp outputs, Sexp transition, Sexp properties) {
		return realizabilityQuery(outputs, transition, properties, 0);
	}
}
