package jkind.solvers.z3;

import jkind.lustre.NamedType;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.UnknownResult;
import jkind.solvers.UnsatResult;
import jkind.solvers.VarDecl;
import jkind.solvers.smtlib2.SmtLib2Solver;

public class Z3Solver extends SmtLib2Solver {
	public Z3Solver() {
		super(new ProcessBuilder("z3", "-smt2", "-in"), "Z3");
	}

	@Override
	public void initialize() {
		send("(set-option :produce-models true)");
	}
	
	private int assumCount = 1;
	
	@Override
	public Result query(Sexp sexp) {
		Result result;

		Symbol assum = new Symbol("assum" + assumCount++);
		send(new VarDecl(assum, NamedType.BOOL));
		send(new Cons("assert", new Cons("=>", assum, new Cons("not", sexp))));
		send(new Cons("check-sat", assum));
		send("(echo \"" + DONE + "\")");
		String status = readFromSolver();
		if (isSat(status)) {
			send("(get-model)");
			send("(echo \"" + DONE + "\")");
			result = new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			result = new UnsatResult();
		} else {
			result = new UnknownResult();
		}

		return result;
	}
}
