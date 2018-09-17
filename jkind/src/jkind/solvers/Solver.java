package jkind.solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.NamedType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.translation.Relation;

public abstract class Solver {
	public abstract void initialize();

	public abstract void assertSexp(Sexp sexp);

	public abstract void define(VarDecl decl);

	public abstract void declare(Function function);

	public abstract void define(Relation relation);

	/**
	 * A query focused on the SAT result. Produces a model for SAT. Does not
	 * return an unsat-core for UNSAT.
	 */
	public abstract Result query(Sexp sexp);

	public abstract void push();

	public abstract void pop();

	public abstract void comment(String str);

	public abstract void stop();

	protected final Map<String, Type> varTypes = new HashMap<>();
	protected final List<Function> functions = new ArrayList<>();

	/**
	 * Check if the solver supports all of the operators in the expression.
	 * Useful since PDR may generate invariants using operators not supported by
	 * the solver.
	 * 
	 * @param expr
	 */
	public boolean supports(Expr expr) {
		return true;
	}

	public void declare(List<Function> functions) {
		for (Function func : functions) {
			declare(func);
		}
	}

	public Symbol createActivationLiteral(String prefix, int i) {
		String name = prefix + i;
		define(new VarDecl(name, NamedType.BOOL));
		return new Symbol(name);
	}

	/**
	 * A query focused on the UNSAT result. Produces no model for SAT. Computes
	 * a minimal unsat-core of activation literals for UNSAT.
	 */
	public Result unsatQuery(List<Symbol> activationLiterals, Sexp query) {
		push();

		assertSexp(new Cons("not", query));
		Result result = quickCheckSat(activationLiterals);

		if (result instanceof UnsatResult) {
			UnsatResult unsat = (UnsatResult) result;
			result = new UnsatResult(minimizeUnsatCore(unsat.getUnsatCore()));
		}

		pop();
		return result;
	}

	/**
	 * Compute a minimal unsat-core for a check-sat under assumptions. This
	 * method assumes that check-sat under all the assumption returns UNSAT.
	 */
	protected List<Symbol> minimizeUnsatCore(List<Symbol> unsatCore) {
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

	/**
	 * Perform a check-sat under assumptions. Produces no model for SAT. Gets
	 * unsat-core of activation literals for UNSAT.
	 */
	protected abstract Result quickCheckSat(List<Symbol> activationLiterals);

	protected <T> List<T> without(List<T> list, T elem) {
		List<T> result = new ArrayList<>(list);
		result.remove(elem);
		return result;
	}
}
