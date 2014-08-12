package jkind.solvers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Sexp;
import jkind.translation.TransitionRelation;

public abstract class Solver {
	public abstract void initialize();

	public abstract void send(Sexp sexp);
	public abstract void define(VarDecl decl);
	public abstract void define(TransitionRelation lambda);
	
	public abstract Label weightedAssert(Sexp sexp, int weight);
	public abstract Label labelledAssert(Sexp sexp);
	public abstract void retract(Label label);
	
	public abstract Result query(Sexp sexp);
	public abstract Result maxsatQuery(Sexp sexp);
	
	public abstract void push();
	public abstract void pop();

	protected final Map<String, Type> varTypes = new HashMap<>();
	
	/** Backend */
	
	protected Process process;
	protected BufferedWriter toSolver;
	protected BufferedReader fromSolver;
	
	protected Solver(ProcessBuilder pb) {
		pb.redirectErrorStream(true);
		try {
			process = pb.start();
		} catch (IOException e) {
			throw new JKindException("Unable to start solver", e);
		}
		addShutdownHook();
		toSolver = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		fromSolver = new BufferedReader(new InputStreamReader(process.getInputStream()));
	}
	

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
			@Override
			public void run() {
				Solver.this.stop();
			}
		});
	}

	public synchronized void stop() {
		/**
		 * This must be synchronized since two threads (a Process or a shutdown
		 * hook) may try to stop the solver at the same time
		 */

		if (process != null) {
			process.destroy();
			process = null;
		}
		
		if (debug != null) {
			debug.close();
			debug = null;
		}
	}

	
	/** Debugging */
	
	protected PrintWriter debug;
	
	public void setDebug(PrintWriter debug) {
		this.debug = debug;
	}

	public void debug(String str) {
		if (debug != null) {
			debug.println(str);
		}
	}
}
