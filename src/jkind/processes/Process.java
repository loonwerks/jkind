package jkind.processes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.processes.messages.Message;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.solvers.Solver;
import jkind.solvers.YicesSolver;
import jkind.translation.Lustre2Sexps;

public abstract class Process implements Runnable {
	final private Lustre2Sexps translation;
	protected List<String> properties;
	protected Director director;
	
	protected Solver solver;
	protected BlockingQueue<Message> incomming = new LinkedBlockingQueue<Message>();
	protected int kMax = 200;

	public Process(List<String> properties, Lustre2Sexps translation, Director director) {
		this.properties = new ArrayList<String>(properties);
		this.translation = translation;
		this.director = director;
	}

	protected void initializeSolver() throws IOException {
		solver = new YicesSolver();
		solver.send(translation.getDefinitions());
		solver.send(translation.getTransition());
	}
	
	
	/** Utility functions */
	
	public static Sexp conjoin(List<String> ids, Sexp i) {
		if (ids.isEmpty()) {
			throw new IllegalArgumentException("Cannot conjoin empty list");
		}
		
		List<Sexp> args = new ArrayList<Sexp>();
		for (String id : ids) {
			args.add(new Cons(id, i));
		}
		return new Cons("and", args);
	}
}
