package jkind.solvers;

import java.util.HashMap;
import java.util.Map;

import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;
import jkind.translation.TransitionRelation;

public abstract class Solver {
	public abstract void initialize();

	public abstract void assertSexp(Sexp sexp);
	public abstract void define(VarDecl decl);
	public abstract void define(TransitionRelation lambda);

	public abstract Result query(Sexp sexp);
	
	public abstract void push();
	public abstract void pop();

	public abstract void comment(String str);
	public abstract void stop();

	protected final Map<String, Type> varTypes = new HashMap<>();

	protected abstract String getSolverExtension();
}
