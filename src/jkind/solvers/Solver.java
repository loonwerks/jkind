package jkind.solvers;

import java.io.IOException;
import java.util.List;

import jkind.sexp.Sexp;

public abstract class Solver {
	public abstract void send(Sexp sexp) throws IOException;
	public abstract void send(List<Sexp> sexps) throws IOException;
	
	public abstract SolverResult query(Sexp sexp) throws IOException;
	
	public abstract void push() throws IOException;
	public abstract void pop() throws IOException;
	public abstract void stop();
	
	public abstract void setDebug(boolean debug);
}
