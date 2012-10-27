package jkind.processes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.misc.JKindException;
import jkind.processes.messages.Message;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
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
		if (properties != null) {
			this.properties = new ArrayList<String>(properties);
		}
		this.translation = translation;
		this.director = director;
	}

	protected void initializeSolver() {
		solver = new YicesSolver();
		solver.send(translation.getDefinitions());
		solver.send(translation.getTransition());
	}
	
	
	/** Utility functions */
	
	public static Sexp conjoin(List<Sexp> fns, Sexp i) {
		if (fns.isEmpty()) {
			throw new JKindException("Cannot conjoin empty list");
		}
		
		List<Sexp> args = new ArrayList<Sexp>();
		for (Sexp fn : fns) {
			args.add(new Cons(fn, i));
		}
		return new Cons("and", args);
	}
	
	public static Sexp conjoinIds(List<String> ids, Sexp i) {
		List<Sexp> symbols = new ArrayList<Sexp>();
		for (String id : ids) {
			symbols.add(new Symbol(id));
		}
		return conjoin(symbols, i);
	}
	
	public static Sexp conjoinStreams(List<String> ids, Sexp i) {
		List<Sexp> symbols = new ArrayList<Sexp>();
		for (String id : ids) {
			symbols.add(new Symbol("$" + id));
		}
		return conjoin(symbols, i);
	}
}
