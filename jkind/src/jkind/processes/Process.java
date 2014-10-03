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
import jkind.analysis.YicesArithOnlyCheck;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.VarDecl;
import jkind.processes.messages.Message;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Solver;
import jkind.solvers.cvc4.Cvc4Solver;
import jkind.solvers.yices.YicesSolver;
import jkind.solvers.yices2.Yices2Solver;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.translation.TransitionRelation;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;
import jkind.util.Util;

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
			solver = new YicesSolver(YicesArithOnlyCheck.check(spec.node));
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
		solver.define(spec.transitionRelation);
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
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

	/** Utility */

	protected void createVariables(int k) {
		for (VarDecl vd : getOffsetVarDecls(k)) {
			solver.define(vd);
		}

		// Constrain input by type
		if (k >= 0) {
			for (VarDecl vd : getOffsetInputVarDecls(k)) {
				if (vd.type instanceof SubrangeIntType) {
					SubrangeIntType subrangeType = (SubrangeIntType) vd.type;
					solver.send(new Cons("assert", SexpUtil.subrangeConstraint(vd.id, subrangeType)));
				} else if (vd.type instanceof EnumType) {
					EnumType enumType = (EnumType) vd.type;
					solver.send(new Cons("assert", SexpUtil.enumConstraint(vd.id, enumType)));
				}
			}
		}
	}

	protected List<VarDecl> getOffsetVarDecls(int k) {
		return getOffsetVarDecls(k, Util.getVarDecls(spec.node));
	}

	protected List<VarDecl> getOffsetInputVarDecls(int k) {
		return getOffsetVarDecls(k, spec.node.inputs);
	}

	protected List<VarDecl> getOffsetVarDecls(int k, List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			StreamIndex si = new StreamIndex(vd.id, k);
			result.add(new VarDecl(si.getEncoded().str, vd.type));
		}
		return result;
	}

	protected void assertBaseTransition(int k) {
		assertTransition(k, k == 0);
	}

	protected static final Symbol INIT = Lustre2Sexp.INIT;

	protected void defineInductiveInit() {
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
	}

	protected void assertInductiveTransition(int k) {
		if (k == 0) {
			assertTransition(0, INIT);
		} else {
			assertTransition(k, false);
		}
	}

	protected void assertTransition(int k, boolean init) {
		assertTransition(k, Sexp.fromBoolean(init));
	}

	protected void assertTransition(int k, Sexp init) {
		solver.send(new Cons("assert", getTransition(k, init)));
	}

	protected Sexp getTransition(int k, Sexp init) {
		List<Sexp> args = new ArrayList<>();
		args.add(init);
		args.addAll(getSymbols(getOffsetVarDecls(k - 1)));
		args.addAll(getSymbols(getOffsetVarDecls(k)));
		return new Cons(TransitionRelation.T, args);
	}

	protected List<Sexp> getSymbols(List<VarDecl> varDecls) {
		List<Sexp> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			result.add(new Symbol(vd.id));
		}
		return result;
	}
}
