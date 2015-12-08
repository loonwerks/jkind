package jkind.solvers.z3;

import java.util.ArrayList;
import java.util.Iterator;
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

	/**
	 * A query focused on the UNSAT result. Produces no model for SAT. Computes
	 * a minimal unsat-core of activation literals for UNSAT.
	 */
	public Result unsatQuery(List<Symbol> actLits, Sexp query) {
		Result result;
		push();

		send(new Cons("assert", new Cons("not", query)));
		send(new Cons("check-sat", new ArrayList<>(actLits)));

		markDone();
		String status = readFromSolver();
		if (isSat(status)) {
			result = new SatResult();
		} else if (isUnsat(status)) {
			result = new UnsatResult(minimizeUnsatCore(getUnsatCore()));
		} else {
			return new UnknownResult();
		}

		pop();
		return result;
	}

	private List<Symbol> getUnsatCore() {
		List<Symbol> unsatCore = new ArrayList<>();
		send("(get-unsat-core)");
		for (String s : readCore().split(" ")) {
			if (!s.isEmpty()) {
				unsatCore.add(new Symbol(s));
			}
		}
		return unsatCore;
	}

	/**
	 * Compute a minimal unsat-core for a check-sat under assumptions. This
	 * method assumes that check-sat under all the assumption returns UNSAT.
	 */
	private List<Symbol> minimizeUnsatCore(List<Symbol> unsatCore) {
		List<Symbol> result = new ArrayList<>(unsatCore);

		Iterator<Symbol> iterator = result.iterator();
		while (iterator.hasNext()) {
			Symbol curr = iterator.next();
			if (quickCheckSat(without(result, curr)) instanceof UnsatResult) {
				iterator.remove();
			}
		}

		comment("Minimal unsat core: " + result);
		return result;
	}

	private <T> List<T> without(List<T> list, T elem) {
		List<T> result = new ArrayList<>(list);
		result.remove(elem);
		return result;
	}

	/**
	 * Perform a check-sat under assumptions without any additional information
	 * (model or unsat-core)
	 */
	private Result quickCheckSat(List<Symbol> activationLiterals) {
		send(new Cons("check-sat", activationLiterals));
		markDone();
		String status = readFromSolver();
		if (isSat(status)) {
			return new SatResult();
		} else if (isUnsat(status)) {
			return new UnsatResult();
		} else {
			return new UnknownResult();
		}
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

	public Symbol createActivationLiteral(String prefix, int id) {
		String name = prefix + id;
		define(new VarDecl(name, NamedType.BOOL));
		return new Symbol(name);
	}
}
