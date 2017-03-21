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
	}

	@Override
	public Result query(Sexp sexp) {
		Result result;

		push();
		send(new Cons("assert", new Cons("not", sexp)));
		send(new Cons("check-sat"));

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

		pop();

		return result;
	}


	@Override
	protected List<Symbol> getUnsatCore(List<Symbol> activationLiterals) {
		// dReal does not yet appear to support unsat-cores
		return activationLiterals;
	}

}
