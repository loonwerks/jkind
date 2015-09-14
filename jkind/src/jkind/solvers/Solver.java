package jkind.solvers;

import java.util.HashMap;
import java.util.Map;

import jkind.lustre.Expr;
import jkind.lustre.InductType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;
import jkind.translation.Relation;
import jkind.translation.Specification;

public abstract class Solver {
	public abstract void initialize(Specification spec);

	public abstract void assertSexp(Sexp sexp);
	public abstract void define(VarDecl decl);
	public abstract void define(Relation relation);
	public abstract void define(InductType type);

	public abstract Result query(Sexp sexp);
	
	public abstract void push();
	public abstract void pop();

	public abstract void comment(String str);
	public abstract void stop();
	
	protected final Map<String, Type> varTypes = new HashMap<>();

	/**
	 * Check if the solver supports all of the operators in the expression.
	 * Useful since PDR may generate invariants using operators not supported by
	 * the solver.
	 * @param expr
	 */
	public boolean supports(Expr expr) {
		return true;
	}
}
