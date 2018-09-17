package jkind.engines;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindSettings;
import jkind.engines.messages.StopMessage;
import jkind.lustre.Expr;
import jkind.lustre.LustreUtil;
import jkind.lustre.NamedType;
import jkind.lustre.VarDecl;
import jkind.lustre.values.BooleanValue;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.Solver;
import jkind.solvers.UnknownResult;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.util.StreamIndex;
import jkind.util.Util;

public abstract class SolverBasedEngine extends Engine {
	protected Solver solver;

	public SolverBasedEngine(String name, Specification spec, JKindSettings settings,
			Director director) {
		super(name, spec, settings, director);
	}

	@Override
	public final void run() {
		try {
			initializeSolver();
			super.run();
		} catch (StopException se) {
		} catch (NullPointerException n){ }
		  catch (Throwable t) {
			throwable = t;
		} finally {
			killEngine();
		}
	}

	protected void initializeSolver() {
		solver = getSolver();
		solver.initialize();
		solver.declare(spec.functions);
		solver.define(spec.getTransitionRelation());
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
	}

	public synchronized void killEngine() {
		if (solver != null) {
			solver.stop();
			solver = null;
		}
	}
	
	@Override
	public void stopEngine() {
		killEngine();
		receiveMessage(new StopMessage());
	}
	
	protected Solver getSolver() {
		return SolverUtil.getSolver(settings.solver, getScratchBase(), spec.node);
	}

	/** Utility */

	protected void comment(String str) {
		solver.comment(str);
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
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl vd : Util.getVarDecls(spec.node)) {
			StreamIndex si = new StreamIndex(vd.id, k);
			result.add(new VarDecl(si.getEncoded().str, vd.type));
		}
		return result;
	}

	protected static final Symbol INIT = Lustre2Sexp.INIT;

	protected void assertBaseTransition(int k) {
		solver.assertSexp(getBaseTransition(k));
	}

	protected void assertInductiveTransition(int k) {
		solver.assertSexp(getInductiveTransition(k));
	}

	protected Sexp getBaseTransition(int k) {
		return getTransition(k, k == 0);
	}

	protected Sexp getInductiveTransition(int k) {
		if (k == 0) {
			return getTransition(0, INIT);
		} else {
			return getTransition(k, false);
		}
	}

	protected Sexp getTransition(int k, boolean init) {
		return getTransition(k, Sexp.fromBoolean(init));
	}

	protected Sexp getTransition(int k, Sexp init) {
		List<Sexp> args = new ArrayList<>();
		args.add(init);
		args.addAll(getSymbols(getOffsetVarDecls(k - 1)));
		args.addAll(getSymbols(getOffsetVarDecls(k)));
		return new Cons(spec.getTransitionRelation().getName(), args);
	}

	protected List<Sexp> getSymbols(List<VarDecl> varDecls) {
		List<Sexp> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			result.add(new Symbol(vd.id));
		}
		return result;
	}

	protected Model getModel(Result result) {
		if (result instanceof SatResult) {
			return ((SatResult) result).getModel();
		} else if (result instanceof UnknownResult) {
			return ((UnknownResult) result).getModel();
		} else {
			throw new IllegalArgumentException();
		}
	}

	protected List<String> getFalseProperties(List<String> properties, int k, Model model) {
		List<String> falses = new ArrayList<>();
		for (String p : properties) {
			StreamIndex si = new StreamIndex(p, k);
			BooleanValue v = (BooleanValue) model.getValue(si);
			if (!v.value) {
				falses.add(p);
			}
		}
		return falses;
	}
}
