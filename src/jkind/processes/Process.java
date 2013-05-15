package jkind.processes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.JKindException;
import jkind.Settings;
import jkind.processes.messages.Message;
import jkind.solvers.Solver;
import jkind.solvers.cvc4.Cvc4Solver;
import jkind.solvers.yices.YicesSolver;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Specification;

public abstract class Process implements Runnable {
	protected Specification spec;
	protected Director director;
	protected List<String> properties;

	protected Solver solver;
	protected BlockingQueue<Message> incoming = new LinkedBlockingQueue<Message>();
	protected int kMax = Settings.n;

	private String name;
	private PrintWriter scratch;
	private Throwable throwable;

	public Process(String name, Specification spec, Director director) {
		this.name = name;
		this.spec = spec;
		this.director = director;
		this.properties = new ArrayList<String>(spec.node.properties);
		this.scratch = getScratch(spec.filename, name);
	}

	private static PrintWriter getScratch(String base, String proc) {
		String filename = base + "." + proc.toLowerCase() + "." + getSolverExtension();
		if (Settings.scratch) {
			try {
				return new PrintWriter(new FileOutputStream(filename), true);
			} catch (FileNotFoundException e) {
				throw new JKindException("Unable to open scratch file: " + filename, e);
			}
		} else {
			return null;
		}
	}

	private static String getSolverExtension() {
		switch (Settings.solver) {
		case YICES:
			return "yc";
		case CVC4:
		case Z3:
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
		switch (Settings.solver) {
		case YICES:
			solver = new YicesSolver();
			break;

		case CVC4:
			solver = new Cvc4Solver();
			break;

		case Z3:
			solver = new Z3Solver();
			break;
		}

		if (Settings.scratch) {
			solver.setDebug(scratch);
		}

		solver.initialize();
		solver.send(spec.translation.getDeclarations());
		solver.send(spec.translation.getTransition());
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
