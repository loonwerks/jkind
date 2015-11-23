package jkind.solvers.z3;

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
		super(scratchBase);
		this.linear = linear;
	}

	@Override
	protected String getSolverName() {
		return "Z3";
	}

	@Override
	protected String[] getSolverOptions() {
		return new String[] { "-smt2", "-in" };
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
			// Even for unknown we can sometimes get a partial model
			send("(get-model)");
			markDone();

			String content = readFromSolver();
			if (content == null) {
				return new UnknownResult();
			} else {
				result = new UnknownResult(parseModel(content));
			}
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
