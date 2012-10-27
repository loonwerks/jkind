package jkind.solvers;

import java.util.List;

import jkind.sexp.Sexp;

public abstract class Solver {
	public abstract void send(Sexp sexp);
	public abstract void send(List<Sexp> sexps);
	
	public abstract SolverResult query(Sexp sexp);
	
	public abstract void push();
	public abstract void pop();
	public abstract void stop();
	
	public abstract void setDebug(boolean debug);
}
