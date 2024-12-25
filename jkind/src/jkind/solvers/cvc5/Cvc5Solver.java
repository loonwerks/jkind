package jkind.solvers.cvc5;   

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
import jkind.solvers.smtlib2.SolverOutOfMemoryException;

public class Cvc5Solver extends SmtLib2Solver implements MaxSatSolver {
	private final boolean linear;
	private int actCount = 1;

	public Cvc5Solver(String scratchBase, boolean linear) {
		super(scratchBase);
		this.linear = linear;
	}

	@Override
	protected String getSolverName() {
		return "Cvc5";
	}

	@Override
	protected String[] getSolverOptions() {
		return new String[] { "--lang", "smt2" };
	}
	
	@Override
	public void initialize() {
		setLogic("ALL"); // Set the logic to ALL or QF_NIRA it can be set with other options too.
		setOption("produce-models", true);
		setOption("produce-unsat-cores", true);
		setOption("minimize-unsat-cores", true);
		setOption("incremental", true); // Enable incremental solving

		// The following option can be added
		// when the reported bugs in Z3 resurfaces:
		// https://github.com/Z3Prover/z3/issues/158
		// setOption("smt.core.validate", true);
	}
	
	public void setLogic(String logic) {
		send("(set-logic " + logic + ")");
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
			send(new Cons("check-sat"));
		} else {
			push();
			send(new Cons("assert", new Cons("not", sexp)));
			send(new Cons("check-sat"));
		}

		try {
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
		} catch (SolverOutOfMemoryException e) {
			return new UnknownResult();
		}

		if (!linear) {
			pop();
		}

		return result;
	}
	
	/*@Override
	public Result query(Sexp sexp) {
		Result result;

	 	if (linear) {
        	// Activation literal for incremental solving
        		Symbol literal = createActivationLiteral("act", actCount++);
        		send(new Cons("declare-fun", literal, new Symbol("Bool"))); // Declare the activation literal
        		send(new Cons("assert", new Cons("=>", literal, new Cons("not", sexp)))); // Use the literal in assertion
        		send(new Cons("check-sat", literal)); // Use the literal in check-sat
    		} else {
        		// Standard solving process for non-linear mode
        		push();
        		send(new Cons("assert", new Cons("not", sexp)));
        		send(new Cons("check-sat"));
    		}

    		try {
        		String status = readFromSolver();

        		if (isSat(status)) {
				send("(get-model)");
				result = new SatResult(parseModel(readFromSolver()));
			} else if (isUnsat(status)) {
				result = new UnsatResult();
			} else {
				// Handle "unknown" results
				send("(get-model)");
			
				String content = readFromSolver();
				if (content == null) {
				return new UnknownResult();
				} else {
					result = new UnknownResult(parseModel(content));
				}
			}
		} catch (SolverOutOfMemoryException e) {
			        // Handle specific solver memory exceptions
			return new UnknownResult();
		}

		if (!linear) {
			pop(); // Pop the context for non-linear solving
		}

		return result;
	}*/

	@Override
	public Result quickCheckSat(List<Symbol> activationLiterals) {
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

	public Result checkMaximal() {
		send("(set-option :sat.phase always_true)");
		send("(check-sat-using sat)");
		String status = readFromSolver();

		if (isSat(status)) {
			send("(get-model)");
			return new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			return new UnsatResult();
		} else {
			return new UnknownResult();
		}
	}

	public Result checkMinimal() {
		send("(set-option :sat.phase always_false)");
		send("(check-sat-using sat)");
		String status = readFromSolver();

		if (isSat(status)) {
			send("(get-model)");
			return new SatResult(parseModel(readFromSolver()));
		} else if (isUnsat(status)) {
			return new UnsatResult();
		} else {
			return new UnknownResult();
		}
	}

	public Result checkValuation(List<Symbol> positiveLits, List<Symbol> negativeLits, boolean getModel) {
		String arg = "(check-sat ";
		for (Symbol s : positiveLits) {
			arg += s.toString() + " ";
		}
		for (Symbol s : negativeLits) {
			arg += "(not " + s.toString() + ") ";
		}
		arg += ")";
		send(arg);
		String status = readFromSolver();

		if (isSat(status)) {
			if (getModel) {
				send("(get-model)");
				return new SatResult(parseModel(readFromSolver()));
			} else {
				return new SatResult();
			}
		} else if (isUnsat(status)) {
			return new UnsatResult();

		} else {
			return new UnknownResult();
		}

	}

	/**
	 * similar to quickCheckSat, but focused on
	 *     1- either the SAT model or unsat-core
	 *     2- or just the return Type of Result
	 */
	public Result checkSat(List<Symbol> activationLiterals, boolean getModel, boolean getCore) {
		send(new Cons("check-sat", activationLiterals));
		String status = readFromSolver();

		if (isSat(status)) {
			if (getModel) {
				send("(get-model)");
				return new SatResult(parseModel(readFromSolver()));
			} else {
				return new SatResult();
			}
		} else if (isUnsat(status)) {
			if (getCore) {
				return new UnsatResult(getUnsatCore(activationLiterals));
			} else {
				return new UnsatResult();
			}
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
		throw new UnsupportedOperationException("Soft assertions are not supported in CVC5.");
	}
	
	@Override
	public Result maxsatQuery(Sexp query) {
		return query(query);
	}

}
