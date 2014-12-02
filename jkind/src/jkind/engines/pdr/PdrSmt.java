package jkind.engines.pdr;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import jkind.JKindSettings;
import jkind.analysis.YicesArithOnlyCheck;
import jkind.analysis.evaluation.Evaluator;
import jkind.engines.StopException;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.LustreUtil;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.Value;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Model;
import jkind.solvers.Result;
import jkind.solvers.SatResult;
import jkind.solvers.Solver;
import jkind.solvers.UnsatResult;
import jkind.solvers.cvc4.Cvc4Solver;
import jkind.solvers.mathsat.MathSatSolver;
import jkind.solvers.smtinterpol.ScriptUser;
import jkind.solvers.smtinterpol.SmtInterpolSolver;
import jkind.solvers.smtinterpol.SmtInterpolUtil;
import jkind.solvers.smtinterpol.Subst;
import jkind.solvers.yices.YicesSolver;
import jkind.solvers.yices2.Yices2Solver;
import jkind.solvers.z3.Z3Solver;
import jkind.translation.Lustre2Sexp;
import jkind.translation.TransitionRelation;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;
import jkind.util.Util;

public class PdrSmt {
	private final Solver solver;
	private final List<Frame> F;

	private final Predicate I;
	private final Predicate P;
	private final Set<Predicate> predicates = new HashSet<>();

	private final PdrPredicateRefiner refiner;

	private final List<VarDecl> varDecls;

	private static final String INIT = Lustre2Sexp.INIT.str;
	private static final int BASE = 0;
	private static final int BASE_ABSTRACT = 1;
	private static final int PRIME = 2;
	private static final int PRIME_ABSTRACT = 3;

	public PdrSmt(JKindSettings settings, Node node, List<Frame> F, String property,
			String scratchBase) {
		this.solver = initializeSolver(settings, node, scratchBase);
		this.F = F;

		varDecls = Util.getVarDecls(node);
		defineVariables(varDecls, BASE);
		defineVariables(varDecls, BASE_ABSTRACT);
		defineVariables(varDecls, PRIME);
		defineVariables(varDecls, PRIME_ABSTRACT);

		this.I = new Predicate(INIT);
		this.P = new Predicate(property);

		solver.assertSexp(T(BASE_ABSTRACT, PRIME_ABSTRACT));

		addPredicate(I);
		addPredicate(P);

		String refineScratchBase = scratchBase == null ? null : scratchBase + "-refine";
		this.refiner = new PdrPredicateRefiner(node, property, refineScratchBase);
	}

	private static Solver initializeSolver(JKindSettings settings, Node node, String scratchBase) {
		Solver solver = getSolver(settings, node, scratchBase);
		solver.initialize();
		solver.define(Lustre2Sexp.constructTransitionRelation(node));
		solver.define(new VarDecl(INIT, NamedType.BOOL));
		return solver;
	}

	// TODO (stop solver at end - catch exceptions?)
	// TODO (duplication)
	private static Solver getSolver(JKindSettings settings, Node node, String scratchBase) {
		switch (settings.solver) {
		case YICES:
			return new YicesSolver(scratchBase, YicesArithOnlyCheck.check(node));
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

	private void addPredicates(Set<Predicate> otherPredicates) {
		otherPredicates.forEach(this::addPredicate);
	}

	private void addPredicate(Predicate p) {
		if (predicates.add(p)) {
			solver.assertSexp(new Cons("=", base(p), baseAbstract(p)));
			solver.assertSexp(new Cons("=", primeAbstract(p), prime(p)));
		}
	}

	public Cube getBadCube() {
		return extractCube(checkSat(and(R(depth()), not(P))));
	}

	private int depth() {
		return F.size() - 2;
	}

	private Sexp R(int k) {
		List<Sexp> conjuncts = new ArrayList<>();
		for (int i = k; i < F.size(); i++) {
			conjuncts.add(F.get(i).toSexp(BASE));
		}
		return SexpUtil.conjoin(conjuncts);
	}

	private Model checkSat(Sexp assertion) {
		Result result = solver.query(not(assertion));
		if (result instanceof SatResult) {
			SatResult sat = (SatResult) result;
			return sat.getModel();
		} else if (result instanceof UnsatResult) {
			return null;
		} else {
			comment("Solver returned unknown result");
			throw new StopException();
		}
	}

	private Cube extractCube(Model model) {
		if (model == null) {
			return null;
		}

		Evaluator eval = new Evaluator() {
			@Override
			public Value visit(IdExpr e) {
				return model.getValue(new StreamIndex(e.id, BASE));
			}
		};

		Cube result = new Cube();
		for (Predicate p : predicates) {
			Value value = p.getExpr().accept(eval);
			result.addPLiteral(new PLiteral(value == BooleanValue.TRUE, p));
		}
		return result;
	}

	public boolean isInitial(Cube cube) {
		// TODO: Given our Lustre translation, a frame is initial if it does not
		// contain ~init
		return !cube.getPLiterals().contains(not(I));
	}

	public enum Option {
		DEFAULT, EXTRACT_MODEL, NO_IND
	};

	public TCube solveRelative(TCube s, Option option) {
		int frame = s.getFrame();
		Cube cube = s.getCube();

		solver.push();

		if (option != Option.NO_IND) {
			solver.assertSexp(not(base(cube)));
		}

		for (int i = frame - 1; i < F.size() - 1; i++) {
			solver.assertSexp(base(F.get(i)), "F" + i);
		}
		script.assertTerm(F.get(F.size() - 1).toTerm(script));

		List<PLiteral> pLiterals = s.getCube().getPLiterals();
		for (int i = 0; i < pLiterals.size(); i++) {
			script.assertTerm(name(prime(pLiterals.get(i).toTerm(script)), "P" + i));
		}

		switch (script.checkSat()) {
		case UNSAT:
			Term[] unsatCore = script.getUnsatCore();
			script.pop(1);
			int minFrame = getMinimumF(unsatCore);
			Cube minCube = getMinimalNonInitialCube(pLiterals, unsatCore);

			if (minFrame == F.size() - 2 || minFrame == TCube.FRAME_INF) {
				return new TCube(minCube, minFrame);
			} else {
				return new TCube(minCube, minFrame + 1);
			}

		case SAT:
			Cube c = null;
			if (option == Option.EXTRACT_MODEL) {
				c = extractCube(script.getModel());
			}
			script.pop(1);
			return new TCube(c, TCube.FRAME_NULL);

		default:
			commentUnknownReason();
			throw new StopException();
		}
	}

	private int getMinimumF(Term[] unsatCore) {
		int min = TCube.FRAME_INF;
		for (Term t : unsatCore) {
			String name = t.toString();
			if (name.startsWith("F")) {
				int frame = Integer.parseInt(name.substring(1));
				if (frame < min) {
					min = frame;
				}
			}
		}
		return min;
	}

	private Cube getMinimalNonInitialCube(List<PLiteral> pLiterals, Term[] unsatCore) {
		Cube result = new Cube();
		for (Term t : unsatCore) {
			String name = t.toString();
			if (name.startsWith("P")) {
				int pIndex = Integer.parseInt(name.substring(1));
				result.addPLiteral(pLiterals.get(pIndex));
			}
		}

		if (isInitial(result)) {
			result.addPLiteral(not(I));
		}

		return result;
	}

	public TCube solveRelative(TCube s) {
		return solveRelative(s, Option.DEFAULT);
	}

	public boolean isBlocked(TCube s) {
		int frame = s.getFrame();
		Cube cube = s.getCube();
		Term query = and(cube, R(frame));
		return checkSat(query) == null;
	}

	public Frame createInitialFrame() {
		return new Frame(I.toTerm(script));
	}

	public void refine(List<Cube> cubes) {
		addPredicates(refiner.refine(cubes));
	}

	public void comment(String comment) {
		script.echo(new QuotedObject(comment));
	}

	public Expr getInvariant(Cube cube) {
		List<Expr> disjuncts = new ArrayList<>();

		for (PLiteral literal : cube.getPLiterals()) {
			if (!literal.equals(not(I))) {
				disjuncts.add(literal.negate().toExpr());
			}
		}

		return LustreUtil.or(disjuncts);
	}

	private Sexp T(int index1, int index2) {
		return new Cons(TransitionRelation.T, concat(getSymbols(index1), getSymbols(index2)));
	}

	private static <T> List<T> concat(List<T> terms1, List<T> terms2) {
		return Stream.concat(terms1.stream(), terms2.stream()).collect(toList());
	}

	private List<Symbol> getSymbols(int index) {
		List<Symbol> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			result.add(new StreamIndex(vd.id, index).getEncoded());
		}
		return result;
	}

	private void defineVariables(List<VarDecl> varDecls, int index) {
		for (VarDecl vd : varDecls) {
			StreamIndex si = new StreamIndex(vd.id, index);
			solver.define(new VarDecl(si.getEncoded().str, vd.type));
		}
	}

	private PLiteral not(Predicate p) {
		return new PLiteral(false, p);
	}

	private Sexp base(Predicate p) {
		return p.toSexp(BASE);
	}

	private Sexp baseAbstract(Predicate p) {
		return p.toSexp(BASE_ABSTRACT);
	}

	private Sexp prime(Predicate p) {
		return p.toSexp(PRIME);
	}

	private Sexp primeAbstract(Predicate p) {
		return p.toSexp(PRIME_ABSTRACT);
	}

	private Sexp and(Sexp sexp, PLiteral plit) {
		return new Cons("and", sexp, plit.toSexp(BASE));
	}

	private Sexp not(Sexp sexp) {
		return SexpUtil.not(sexp);
	}

	private Sexp base(Cube cube) {
		return cube.toSexp(BASE);
	}

	private Sexp base(Frame frame) {
		return frame.toSexp(BASE);
	}

}
