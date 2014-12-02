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
import jkind.engines.StopException;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.LustreUtil;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.solvers.Solver;
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
import jkind.util.StreamIndex;
import jkind.util.Util;

public class PdrSmt {
	private final Solver solver;
	private final List<Frame> F;

	private static final String INIT = Lustre2Sexp.INIT.str;

	private final List<Symbol> base;
	private final List<Symbol> prime;

	private final Predicate I;
	private final Predicate P;

	private final Set<Predicate> predicates = new HashSet<>();
	private final List<Symbol> baseAbstract;
	private final List<Symbol> primeAbstract;

	private final PdrPredicateRefiner refiner;

	public PdrSmt(JKindSettings settings, Node node, List<Frame> F, String property,
			String scratchBase) {
		this.solver = initializeSolver(settings, node, scratchBase);
		this.F = F;

		List<VarDecl> varDecls = Util.getVarDecls(node);
		this.base = defineVariables(varDecls, "");
		this.prime = defineVariables(varDecls, "'");
		this.baseAbstract = defineVariables(varDecls, "-");
		this.primeAbstract = defineVariables(varDecls, "-'");

		this.I = new Predicate(INIT);
		this.P = new Predicate(StreamIndex.getPrefix(property));
		
		solver.assertSexp(T(baseAbstract, primeAbstract));

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
			solver.assertSexp(new Cons("=", apply(p, base), apply(p, baseAbstract)));
			solver.assertSexp(new Cons("=", apply(p, primeAbstract), apply(p, prime)));
		}
	}

	public Cube getBadCube() {
		return extractCube(checkSat(and(R(depth()), not(P))));
	}

	private int depth() {
		return F.size() - 2;
	}

	private Term R(int k) {
		List<Term> conjuncts = new ArrayList<>();
		for (int i = k; i < F.size(); i++) {
			conjuncts.add(F.get(i).toTerm(script));
		}
		return and(conjuncts);
	}

	private Model checkSat(Term assertion) {
		script.push(1);
		script.assertTerm(assertion);
		switch (script.checkSat()) {
		case UNSAT:
			script.pop(1);
			return null;

		case SAT:
			Model model = script.getModel();
			script.pop(1);
			return model;

		default:
			commentUnknownReason();
			throw new StopException();
		}
	}

	private Cube extractCube(Model model) {
		if (model == null) {
			return null;
		}

		Cube result = new Cube();
		for (Predicate p : predicates) {
			Term t = p.toTerm(script);
			result.addPLiteral(new PLiteral(isTrue(model.evaluate(t)), p));
		}
		return result;
	}

	private boolean isTrue(Term term) {
		if (term instanceof ApplicationTerm) {
			ApplicationTerm at = (ApplicationTerm) term;
			return at.getFunction().getName().equals("true");
		}
		return false;
	}

	public boolean isInitial(Cube cube) {
		// Given our Lustre translation, a frame is initial if it does not
		// contain ~init
		return !cube.getPLiterals().contains(not(I));
	}

	public enum Option {
		DEFAULT, EXTRACT_MODEL, NO_IND
	};

	public TCube solveRelative(TCube s, Option option) {
		int frame = s.getFrame();
		Cube cube = s.getCube();

		script.push(1);

		if (option != Option.NO_IND) {
			script.assertTerm(not(cube));
		}

		for (int i = frame - 1; i < F.size() - 1; i++) {
			script.assertTerm(name(F.get(i).toTerm(script), "F" + i));
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

	private void commentUnknownReason() {
		comment("SMT solver returned 'unknown' due to " + script.getInfo(":reason-unknown"));
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

	private static Sexp T(List<Symbol> variables1, List<Symbol> variables2) {
		return new Cons(TransitionRelation.T, concat(variables1, variables2));
	}

	private static List<Symbol> toSymbols(List<String> strings) {
		return strings.stream().map(Symbol::new).collect(toList());
	}

	private static <T> List<T> concat(List<T> terms1, List<T> terms2) {
		return Stream.concat(terms1.stream(), terms2.stream()).collect(toList());
	}

	private List<Symbol> defineVariables(List<VarDecl> varDecls, String suffix) {
		List<Symbol> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			String id = vd.id + suffix;
			solver.define(new VarDecl(id, vd.type));
			result.add(new Symbol(id));
		}
		return result;
	}

	private Term declareVar(String name, Type type) {
		Sort sort = getSort(type);
		script.declareFun(name, new Sort[0], sort);
		return script.term(name);
	}

	private Sort getSort(Type type) {
		return SmtInterpolUtil.getSort(script, type);
	}

	private Term not(Cube cube) {
		return not(cube.toTerm(script));
	}

	private Term prime(Term term) {
		return subst(term, base, prime);
	}

	private Term apply(Term term, Term[] arguments) {
		return subst(term, base, arguments);
	}

	private Sexp apply(Predicate p, List<String> arguments) {
		return apply(p.toTerm(script), arguments);
	}

	private Term subst(Term term, Term[] variables, Term[] arguments) {
		return Subst.apply(script, term, variables, arguments);
	}

	private Term and(Cube cube, Term... terms) {
		return and(cube.toTerm(script), and(terms));
	}

	private Term name(Term term, String name) {
		return script.annotate(term, new Annotation(":named", name));
	}

	private PLiteral not(Predicate p) {
		return new PLiteral(false, p);
	}

	private List<String> append(List<String> ids, String suffix) {
		return ids.stream().map(s -> s + suffix).collect(toList());
	}
}
