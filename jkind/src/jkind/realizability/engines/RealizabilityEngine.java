package jkind.realizability.engines;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.JRealizabilitySettings;
import jkind.analysis.LinearChecker;
import jkind.engines.StopException;
import jkind.lustre.Expr;
import jkind.lustre.LustreUtil;
import jkind.lustre.VarDecl;
import jkind.realizability.engines.messages.Message;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.StreamIndex;
import jkind.util.Util;

public abstract class RealizabilityEngine implements Runnable {
	protected final String name;
	protected final Specification spec;
	protected final JRealizabilitySettings settings;
	protected final RealizabilityDirector director;

	protected Z3Solver solver;
	protected final BlockingQueue<Message> incoming = new LinkedBlockingQueue<>();

	// The director process will read this from another thread, so we
	// make it volatile
	protected volatile Throwable throwable;

	public RealizabilityEngine(String name, Specification spec,
			JRealizabilitySettings settings, RealizabilityDirector director) {
		this.name = name;
		this.spec = spec;
		this.settings = settings;
		this.director = director;
	}

	protected abstract void main();

	@Override
	public final void run() {
		try {
			initializeSolver();
			main();
		} catch (StopException se) {
		} catch (Throwable t) {
			throwable = t;
		} finally {
			if (solver != null) {
				solver.stop();
				solver = null;
			}
		}
	}

	protected void initializeSolver() {
		solver = new Z3Solver(getScratchBase(), LinearChecker.isLinear(spec.node));
		solver.initialize();
		solver.declare(spec.functions);
		solver.define(spec.getTransitionRelation());
	}

	protected String getScratchBase() {
		if (settings.scratch) {
			return settings.filename + "." + name;
		} else {
			return null;
		}
	}

	public Throwable getThrowable() {
		return throwable;
	}

	/** Utility */

	protected void comment(String str) {
		solver.comment(str);
	}

	public String getName() {
		return name;
	}

	protected void createVariables(int k) {
		for (VarDecl vd : getOffsetVarDecls(k)) {
			solver.define(vd);
		}

		for (VarDecl vd : Util.getVarDecls(spec.node)) {
			Expr constraint = LustreUtil.typeConstraint(vd.id, vd.type);
			if (constraint != null) {
				solver.assertSexp(constraint.accept(new Lustre2Sexp(k)));
			}
		}
	}

	protected List<VarDecl> getOffsetVarDecls(int k) {
		return getOffsetVarDecls(k, Util.getVarDecls(spec.node));
	}

	protected List<VarDecl> getOffsetVarDecls(int k, List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			StreamIndex si = new StreamIndex(vd.id, k);
			result.add(new VarDecl(si.getEncoded().str, vd.type));
		}
		return result;
	}

	protected Sexp getTransition(int k, Sexp init) {
		List<Sexp> args = new ArrayList<>();
		args.add(init);
		args.addAll(getSymbols(getOffsetVarDecls(k - 1)));
		args.addAll(getSymbols(getOffsetVarDecls(k)));
		return new Cons(spec.getTransitionRelation().getName(), args);
	}

	protected Sexp getTransition(int k, boolean init) {
		return getTransition(k, Sexp.fromBoolean(init));
	}

	protected Sexp getRealizabilityOutputs(int k) {
		List<VarDecl> realizabilityOutputVarDecls = getRealizabilityOutputVarDecls();
		if (realizabilityOutputVarDecls.isEmpty()) {
			return null;
		} else {
			return varDeclsToQuantifierArguments(realizabilityOutputVarDecls, k);
		}
	}

	protected Sexp getInductiveTransition(int k) {
		if (k == 0) {
			return getTransition(0, INIT);
		} else {
			return getTransition(k, false);
		}
	}

	protected static final Symbol INIT = Lustre2Sexp.INIT;

	private List<VarDecl> getRealizabilityOutputVarDecls() {
		List<String> realizabilityInputs = spec.node.realizabilityInputs;
		List<VarDecl> all = Util.getVarDecls(spec.node);
		all.removeIf(vd -> realizabilityInputs.contains(vd.id));
		return all;
	}

	protected Sexp varDeclsToQuantifierArguments(List<VarDecl> varDecls, int k) {
		List<Sexp> args = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			Symbol name = new StreamIndex(vd.id, k).getEncoded();
			Symbol type = solver.type(vd.type);
			args.add(new Cons(name, type));
		}
		return new Cons(args);
	}

	protected List<Sexp> getSymbols(List<VarDecl> varDecls) {
		List<Sexp> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			result.add(new Symbol(vd.id));
		}
		return result;
	}
}