package jkind.solvers.dreal;

import java.util.List;

import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.smtlib2.SmtLib2Solver;

public class DRealSolver extends SmtLib2Solver {

	public DRealSolver(String scratchBase) {
		super(scratchBase);
	}

	@Override
	protected String getSolverName() {
		return "dfake";
	}

	@Override
	protected String[] getSolverOptions() {
		return new String[] { "-in" };
	}

	@Override
	public void initialize() {
		send("(set-logic QF_NRA)");
	}

	@Override
	public Result query(Sexp sexp) {
		Result result;

		push();
		send(new Cons("assert", new Cons("not", sexp)));
		send(new Cons("check-sat"));

		String status = readFromSolver();
		System.out.println("Status: " + status);
		if (isSat(status)) {
			System.out.println("SAT result: sending (get-model)");
			send("(get-model)");
			result = new SatResult(parseModel(readFromSolver()));
			System.out.println("Model parsed");
		} else if (isUnsat(status)) {
			System.out.println("UNSAT result...");
			result = new UnsatResult();
		} else {
			// Even for unknown we can sometimes get a partial model
			System.out.println("UNKNOWN result...");
			send("(get-model)");

			String content = readFromSolver();
			if (content == null) {
				return new UnknownResult();
			} else {
				result = new UnknownResult(parseModel(content));
			}
		}

		pop();

		return result;
	}


	@Override
	protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
		// dReal does not yet appear to support unsat-cores
		return activationLiterals;
	}

}
