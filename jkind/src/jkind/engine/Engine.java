package jkind.engine;

import java.util.ArrayList;
import java.util.List;

import jkind.JKindSettings;
import jkind.analysis.YicesArithOnlyCheck;
import jkind.engines.messages.MessageHandler;
import jkind.lustre.EnumType;
import jkind.lustre.NamedType;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Solver;
import jkind.solvers.cvc4.Cvc4Solver;
import jkind.solvers.mathsat.MathSatSolver;
import jkind.solvers.smtinterpol.SmtInterpolSolver;
import jkind.solvers.yices.YicesSolver;
import jkind.solvers.yices2.Yices2Solver;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.Specification;
import jkind.translation.TransitionRelation;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;
import jkind.util.Util;

public abstract class Engine extends MessageHandler implements Runnable {
	protected Specification spec;
	protected JKindSettings settings;
	protected Director director;
	protected List<String> properties;

	protected Solver solver;

	private String name;

	// The director process will read this from another thread, so we
	// make it volatile
	private volatile Throwable throwable;

	public Engine(String name, Specification spec, JKindSettings settings, Director director) {
		this.name = name;
		this.spec = spec;
		this.settings = settings;
		this.director = director;
		this.properties = new ArrayList<>(spec.node.properties);
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
			stopReceivingMessages();
		}
	}

	protected void initializeSolver() {
		solver = getSolver();
		solver.initialize();
		solver.define(spec.transitionRelation);
		solver.define(new VarDecl(INIT.str, NamedType.BOOL));
	}

	private Solver getSolver() {
		String scratchBase = getScratchBase();
		switch (settings.solver) {
		case YICES:
			return new YicesSolver(scratchBase, YicesArithOnlyCheck.check(spec.node));
		case CVC4:
			return new Cvc4Solver(scratchBase);
		case Z3:
			return new Z3Solver(scratchBase);
		case YICES2:
			return new Yices2Solver(scratchBase);
		case MATHSAT:
			return new MathSatSolver(scratchBase);
		case SMTINTERPOL:
			return new SmtInterpolSolver(scratchBase);
		}
		throw new IllegalArgumentException("Unknown solver: " + settings.solver);
	}

	private String getScratchBase() {
		if (settings.scratch) {
			return settings.filename + "." + name;
		} else {
			return null;
		}
	}

	public Throwable getThrowable() {
		return throwable;
	}

	/** Debug methods */

	protected void comment(String str) {
		solver.comment(str);
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
					solver.assertSexp(SexpUtil.subrangeConstraint(vd.id, subrangeType));
				} else if (vd.type instanceof EnumType) {
					EnumType enumType = (EnumType) vd.type;
					solver.assertSexp(SexpUtil.enumConstraint(vd.id, enumType));
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
		solver.assertSexp(getTransition(k, init));
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
