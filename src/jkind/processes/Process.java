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
import jkind.solvers.yices.YicesSolver;
import jkind.translation.Specification;

public abstract class Process implements Runnable {
	protected Specification spec;
	protected Director director;
	protected List<String> properties;
	
	protected Solver solver;
	protected BlockingQueue<Message> incoming = new LinkedBlockingQueue<Message>();
	protected int kMax = Settings.n;
	
	private PrintWriter scratch;
	private Throwable throwable;
	
	public Process(Specification spec, Director director) {
		this.spec = spec;
		this.director = director;
		this.properties = new ArrayList<String>(spec.node.properties);
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
		solver = new YicesSolver();
		if (Settings.scratch) {
			solver.setDebug(scratch);
		}
		solver.initialize();
		solver.send(spec.translation.getDefinitions());
		solver.send(spec.translation.getTransition());
	}
	
	public Throwable getThrowable() {
		return throwable;
	}

	/** Debug methods */
	
	protected void setScratch(String filename) {
		if (Settings.scratch) {
			try {
				scratch = new PrintWriter(new FileOutputStream(filename), true);
			} catch (FileNotFoundException e) {
				throw new JKindException("Unable to open scratch file: " + filename, e);
			}
		}
	}
	
	protected void debug(String str) {
		if (scratch != null) {
			scratch.print("; ");
			scratch.println(str);
		}
	}
}
