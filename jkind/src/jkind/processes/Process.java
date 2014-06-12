package jkind.processes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.lustre.NamedType;
import jkind.processes.messages.Message;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Solver;
import jkind.solvers.VarDecl;
import jkind.solvers.cvc4.Cvc4Solver;
import jkind.solvers.yices.YicesSolver;
import jkind.solvers.yices2.Yices2Solver;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Keywords;
import jkind.translation.Specification;

public abstract class Process implements Runnable {
	protected Specification spec;
	protected JKindSettings settings;
	protected Director director;
	protected List<String> properties;

	protected Solver solver;
	protected BlockingQueue<Message> incoming = new LinkedBlockingQueue<>();

	private String name;
	private PrintWriter scratch;

	// The director process will read this from another thread, so we
	// make it volatile
	private volatile Throwable throwable;

	public Process(String name, Specification spec, JKindSettings settings, Director director) {
		this.name = name;
		this.spec = spec;
		this.settings = settings;
		this.director = director;
		this.properties = new ArrayList<>(spec.node.properties);
		this.scratch = getScratch(spec.filename, name);
	}

	private PrintWriter getScratch(String base, String proc) {
		String filename = base + "." + proc.toLowerCase() + "." + getSolverExtension();
		if (settings.scratch) {
			try {
				return new PrintWriter(new FileOutputStream(filename), true);
			} catch (FileNotFoundException e) {
				throw new JKindException("Unable to open scratch file: " + filename, e);
			}
		} else {
			return null;
		}
	}

	private String getSolverExtension() {
		switch (settings.solver) {
		case YICES:
			return "yc";
		case CVC4:
		case Z3:
		case YICES2:
			return "smt2";
		}
		return null;
	}

	protected abstract void main();

	@Override
	final public void run() {
		try {
			initializeSolver();
			main();
		} catch (Throwable t) {
			throwable = t;
		} finally {
			if (solver != null) {
				solver.stop();
				solver = null;
			}
			if (scratch != null) {
				scratch.close();
			}
		}
	}

	protected void initializeSolver() {
		switch (settings.solver) {
		case YICES:
			solver = new YicesSolver();
			break;

		case CVC4:
			solver = new Cvc4Solver();
			break;

		case Z3:
			solver = new Z3Solver();
			break;
			
		case YICES2:
			solver = new Yices2Solver();
			break;
		}

		if (settings.scratch) {
			solver.setDebug(scratch);
		}

		solver.initialize();
		solver.send(spec.translation.getDeclarations());
		solver.send(spec.translation.getTransition());
	}

	protected void declareN() {
		solver.send(new VarDecl(Keywords.N, NamedType.INT));
		solver.send(new Cons("assert", new Cons(">=", Keywords.N, Sexp.fromInt(0))));
	}

	public Throwable getThrowable() {
		return throwable;
	}

	/** Debug methods */

	protected void debug(String str) {
		if (scratch != null) {
			scratch.print("; ");
			scratch.println(str);
		}
	}

	public String getName() {
		return name;
	}
}
