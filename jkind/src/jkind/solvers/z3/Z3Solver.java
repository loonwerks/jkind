package jkind.solvers.z3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.MaxSatSolver;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.smtlib2.SmtLib2Solver;

public class Z3Solver extends SmtLib2Solver implements MaxSatSolver {
	private final boolean linear;
	private int actCount = 1;

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
		setOption("produce-models", true);
		setOption("produce-unsat-cores", true);
	}

	public void setOption(String option, boolean value) {
		send("(set-option :" + option + " " + value + ")");
	}

	@Override
	public Result query(Sexp sexp) {
		Result result;

		if (linear) {
			Symbol literal = createActivationLiteral("act", actCount++);
			send(new Cons("assert", new Cons("=>", literal, new Cons("not", sexp))));
			send(new Cons("check-sat", literal));
		} else {
			push();
			send(new Cons("assert", new Cons("not", sexp)));
			send(new Cons("check-sat"));
		}

		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			result = new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			result = new UnsatResult();
		} else {
			// Even for unknown we can sometimes get a partial model
			send("(get-model)");

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

	@Override
	protected Result quickCheckSat(List<Symbol> activationLiterals) {
		send(new Cons("check-sat", activationLiterals));
		String status = readFromSolver();
		if (isSat(status)) {
			return new SatResult();
		} else if (isUnsat(status)) {
			return new UnsatResult(getUnsatCore(activationLiterals));
		} else {
			return new UnknownResult();
		}
	}

	@Override
	protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
		List<Symbol> unsatCore = new ArrayList<>();
		send("(get-unsat-core)");
		for (String s : readCore().split(" ")) {
			if (!s.isEmpty()) {
				unsatCore.add(new Symbol(s));
			}
		}
		return unsatCore;
	}

	private String readCore() {
		String line = "";
		try {
			line = fromSolver.readLine();
			comment(getSolverName() + ": " + line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line.substring(1, line.length() - 1);
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
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
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

	@Override
	public void assertSoft(Sexp sexp) {
		send(new Cons("assert-soft", sexp));
	}

	@Override
	public Result maxsatQuery(Sexp query) {
		return query(query);
	}
}
